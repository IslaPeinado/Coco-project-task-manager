# Bug Detection Prompt

Analyze a failing test or runtime error in COCO.

Workflow:

1. Reproduce the issue from logs or tests.
2. Identify the smallest failing scope.
3. Propose a regression test.
4. Implement the minimal patch.
5. Re-run validation.
6. Regenerate docs and AI context if behavior changed.
