import fs from 'node:fs';
import path from 'node:path';

const ROOT = process.cwd();

const EXCLUDED_DIRS = new Set([
  '.git',
  'node_modules',
  'dist',
  'coverage',
  '.angular',
]);

const STOPWORDS = new Set([
  'the',
  'and',
  'for',
  'with',
  'that',
  'this',
  'from',
  'into',
  'when',
  'how',
  'what',
  'where',
  'your',
  'are',
  'was',
  'were',
  'has',
  'have',
  'had',
  'but',
  'not',
  'can',
  'use',
  'using',
  'handled',
  'project',
]);

function walk(dir) {
  const results = [];

  if (!fs.existsSync(dir)) {
    return results;
  }

  for (const entry of fs.readdirSync(dir, { withFileTypes: true })) {
    if (EXCLUDED_DIRS.has(entry.name)) {
      continue;
    }

    const fullPath = path.join(dir, entry.name);

    if (entry.isDirectory()) {
      results.push(...walk(fullPath));
    } else {
      results.push(fullPath);
    }
  }

  return results;
}

function safeRead(relativePath) {
  const fullPath = path.join(ROOT, relativePath);
  return fs.existsSync(fullPath) ? fs.readFileSync(fullPath, 'utf8') : '';
}

function extractBulletsFromSection(content, heading) {
  if (!content) {
    return [];
  }

  const escapedHeading = heading.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
  const match = content.match(
    new RegExp(`^### ${escapedHeading}\\r?\\n\\r?\\n([\\s\\S]*?)(?:\\r?\\n## |\\r?\\n### |$)`, 'm'),
  );

  if (!match) {
    return [];
  }

  return match[1]
    .split(/\r?\n/)
    .map((line) => line.trim())
    .filter((line) => line.startsWith('- `') && line.endsWith('`'))
    .map((line) => line.slice(3, -1));
}

function findFiles(startDir, predicate) {
  return walk(path.join(ROOT, startDir))
    .filter((file) => predicate(path.relative(ROOT, file).replace(/\\/g, '/')))
    .map((file) => path.relative(ROOT, file).replace(/\\/g, '/'))
    .sort();
}

function parseRoutes(content) {
  const routeMatches = [...content.matchAll(/path:\s*['"`]([^'"`]+)['"`]/g)];
  return routeMatches.map((match) => match[1]);
}

function parseJavaPublicTypes(content) {
  const matches = [
    ...content.matchAll(
      /\b(public\s+(?:class|interface|record|enum))\s+([A-Za-z0-9_]+)/g,
    ),
  ];
  return matches.map((match) => ({
    kind: match[1].replace('public ', ''),
    name: match[2],
  }));
}

function detectStack() {
  const rootPackage = safeRead('package.json');
  const frontendPackage = safeRead('frontend/package.json');
  const angularJson = safeRead('frontend/angular.json');
  const javaFiles = findFiles('backend/src/main/java', (file) => file.endsWith('.java'));

  return {
    hasRootAutomation: Boolean(rootPackage),
    hasAngular: frontendPackage.includes('"@angular/core"') || angularJson.includes('@angular'),
    hasSpringCode: javaFiles.length > 0,
    backendBuildPresent:
      fs.existsSync(path.join(ROOT, 'backend', 'pom.xml')) ||
      fs.existsSync(path.join(ROOT, 'backend', 'build.gradle')) ||
      fs.existsSync(path.join(ROOT, 'backend', 'mvnw')) ||
      fs.existsSync(path.join(ROOT, 'backend', 'gradlew')),
  };
}

function collectProjectFacts() {
  const existingTestingDoc = safeRead('docs/testing.md');
  const frontendSpecs = findFiles('frontend/src', (file) => file.endsWith('.spec.ts'));
  const frontendSource = findFiles(
    'frontend/src',
    (file) => file.endsWith('.ts') && !file.endsWith('.spec.ts'),
  );
  const frontendRoutes = parseRoutes(safeRead('frontend/src/app/app.routes.ts'));

  const backendJava = findFiles('backend/src/main/java', (file) => file.endsWith('.java'));
  const backendTestsDetected = findFiles('backend/src/test/java', (file) => file.endsWith('.java'));
  const backendTests =
    backendTestsDetected.length > 0
      ? backendTestsDetected
      : extractBulletsFromSection(existingTestingDoc, 'Backend');
  const backendTypes = backendJava.flatMap((file) =>
    parseJavaPublicTypes(safeRead(file)).map((type) => ({
      file,
      ...type,
    })),
  );
  const migrations = findFiles(
    'backend/src/main/resources/db/migration',
    (file) => file.endsWith('.sql'),
  );

  const docs = findFiles('docs', (file) => file.endsWith('.md') || file.endsWith('.puml'));
  const prompts = findFiles('prompts', (file) => file.endsWith('.md'));

  return {
    root: ROOT,
    generatedAt: new Date().toISOString(),
    stack: detectStack(),
    frontend: {
      specs: frontendSpecs,
      source: frontendSource,
      routes: frontendRoutes,
    },
    backend: {
      java: backendJava,
      tests: backendTests,
      types: backendTypes,
      migrations,
    },
    docs,
    prompts,
  };
}

function ensureDir(relativeDir) {
  fs.mkdirSync(path.join(ROOT, relativeDir), { recursive: true });
}

function writeFile(relativePath, content) {
  const fullPath = path.join(ROOT, relativePath);
  fs.mkdirSync(path.dirname(fullPath), { recursive: true });
  fs.writeFileSync(fullPath, content, 'utf8');
}

function listToBullet(items) {
  if (items.length === 0) {
    return '- None detected';
  }

  return items.map((item) => `- \`${item}\``).join('\n');
}

function truncate(text, maxLength) {
  if (text.length <= maxLength) {
    return text;
  }

  return `${text.slice(0, maxLength - 3)}...`;
}

export {
  ROOT,
  STOPWORDS,
  collectProjectFacts,
  ensureDir,
  findFiles,
  listToBullet,
  extractBulletsFromSection,
  safeRead,
  truncate,
  walk,
  writeFile,
};
