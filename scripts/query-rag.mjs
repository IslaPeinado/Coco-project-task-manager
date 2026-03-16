import fs from 'node:fs';
import path from 'node:path';
import { STOPWORDS } from './lib/project-analysis.mjs';

const query = process.argv.slice(2).join(' ').trim();

if (!query) {
  console.error('Usage: node scripts/query-rag.mjs "your question"');
  process.exit(1);
}

const indexPath = path.join(process.cwd(), 'ai-context', 'rag-index.json');

if (!fs.existsSync(indexPath)) {
  console.error('RAG index not found. Run `npm run rag:index` first.');
  process.exit(1);
}

const index = JSON.parse(fs.readFileSync(indexPath, 'utf8'));
const queryTokens = query
  .toLowerCase()
  .replace(/[^a-z0-9_\-/\s]/g, ' ')
  .split(/\s+/)
  .filter((token) => token.length > 2 && !STOPWORDS.has(token));

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
  return vector.map((value) => value / magnitude);
}

function cosineSimilarity(left, right) {
  return left.reduce((sum, value, index) => sum + value * right[index], 0);
}

const queryEmbedding = buildEmbedding(queryTokens, index.embeddingDimension || 32);

const scored = index.documents
  .map((document) => {
    const overlap = queryTokens.filter((token) => document.tokens.includes(token));
    const similarity = cosineSimilarity(queryEmbedding, document.embedding || []);
    return {
      ...document,
      score: Number((overlap.length + similarity * 0.2).toFixed(6)),
      similarity: Number(similarity.toFixed(6)),
      overlap,
    };
  })
  .sort((a, b) => b.score - a.score)
  .slice(0, 5);

console.log(JSON.stringify({ query, results: scored }, null, 2));
