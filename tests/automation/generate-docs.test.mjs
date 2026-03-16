import test from 'node:test';
import assert from 'node:assert/strict';
import fs from 'node:fs';
import path from 'node:path';
import { execFileSync } from 'node:child_process';

test('generated docs exist', () => {
  const files = [
    'docs/architecture.md',
    'docs/api.md',
    'docs/workflows.md',
    'ai-context/system-overview.md',
    'ai-context/rag-index.json',
  ];

  for (const file of files) {
    assert.equal(fs.existsSync(path.join(process.cwd(), file)), true, `${file} should exist`);
  }
});

test('rag index contains documents', () => {
  const index = JSON.parse(
    fs.readFileSync(path.join(process.cwd(), 'ai-context', 'rag-index.json'), 'utf8'),
  );

  assert.equal(Array.isArray(index.documents), true);
  assert.ok(index.documents.length > 0);
});

test('rag query returns JWT context', () => {
  const output = execFileSync('node', ['scripts/query-rag.mjs', 'How is JWT handled?'], {
    cwd: process.cwd(),
    encoding: 'utf8',
  });
  const result = JSON.parse(output);

  assert.ok(result.results.length > 0);
  assert.equal(
    result.results.some((item) => item.path.includes('JwtService')),
    true,
    'JWT query should return JwtService context',
  );
});
