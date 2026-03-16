import fs from 'node:fs';
import path from 'node:path';

const requiredFiles = [
  'docs/architecture.md',
  'docs/api.md',
  'docs/workflows.md',
  'docs/development.md',
  'docs/testing.md',
  'docs/ai-agents.md',
  'docs/troubleshooting.md',
  'ai-context/system-overview.md',
  'ai-context/domain-knowledge.md',
  'ai-context/api-reference.md',
  'ai-context/common-errors.md',
  'ai-context/rag-index.json',
  'adr/0001-docs-automation-foundation.md',
  'prompts/feature-delivery.md',
  'prompts/test-generation.md',
  'prompts/refactor.md',
  'prompts/architecture-explainer.md',
  'prompts/bug-detection.md',
];

const missing = requiredFiles.filter(
  (filePath) => !fs.existsSync(path.join(process.cwd(), filePath)),
);

if (missing.length > 0) {
  console.error(`Missing automation artifacts:\n${missing.map((item) => `- ${item}`).join('\n')}`);
  process.exit(1);
}

console.log('Automation artifacts validated.');
