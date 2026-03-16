import fs from 'node:fs';
import path from 'node:path';
import {
  STOPWORDS,
  collectProjectFacts,
  safeRead,
  truncate,
  writeFile,
} from './lib/project-analysis.mjs';

const facts = collectProjectFacts();

const sources = [
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
  ...facts.backend.java,
  ...facts.backend.tests,
  ...facts.frontend.source.slice(0, 40),
  ...facts.frontend.specs.slice(0, 40),
];

function chunkText(filePath, content) {
  const normalized = content.replace(/\r\n/g, '\n');
  const paragraphs = normalized
    .split(/\n{2,}/)
    .map((part) => part.trim())
    .filter(Boolean);

  return paragraphs.map((paragraph, index) => ({
    id: `${filePath}#${index + 1}`,
    path: filePath,
    chunkIndex: index + 1,
    text: truncate(paragraph, 800),
    tokens: paragraph
      .toLowerCase()
      .replace(/[^a-z0-9_\-/\s]/g, ' ')
      .split(/\s+/)
      .filter((token) => token.length > 2 && !STOPWORDS.has(token)),
  }));
}

function buildEmbedding(tokens, dimension = 32) {
  const vector = Array.from({ length: dimension }, () => 0);

  for (const token of tokens) {
    let hash = 0;

    for (const char of token) {
      hash = (hash * 31 + char.charCodeAt(0)) >>> 0;
    }

    vector[hash % dimension] += 1;
  }

  const magnitude = Math.sqrt(vector.reduce((sum, value) => sum + value ** 2, 0)) || 1;
  return vector.map((value) => Number((value / magnitude).toFixed(6)));
}

const documents = sources
  .filter((filePath, index) => sources.indexOf(filePath) === index)
  .filter((filePath) => fs.existsSync(path.join(process.cwd(), filePath)))
  .flatMap((filePath) => chunkText(filePath, safeRead(filePath)))
  .map((document) => ({
    ...document,
    embedding: buildEmbedding(document.tokens),
  }));

writeFile(
  'ai-context/rag-index.json',
  JSON.stringify(
    {
      generatedAt: facts.generatedAt,
      strategy: 'hashed-token-embedding',
      embeddingDimension: 32,
      documents,
    },
    null,
    2,
  ),
);

console.log(`Built RAG index with ${documents.length} chunks.`);
