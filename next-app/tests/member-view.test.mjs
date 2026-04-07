import assert from 'node:assert/strict';
import test from 'node:test';
import fs from 'node:fs/promises';
import path from 'node:path';
import ts from 'typescript';

async function loadMemberViewModule() {
    const targetFiles = ['app/_lib/api-client.ts', 'app/_lib/member-view.ts'];
    const compiledModules = new Map();

    for (const targetFile of targetFiles) {
        const filePath = path.resolve(targetFile);
        const source = await fs.readFile(filePath, 'utf8');
        const transpiled = ts.transpileModule(source, {
            compilerOptions: {
                module: ts.ModuleKind.ES2022,
                target: ts.ScriptTarget.ES2022,
            },
            fileName: filePath,
        });

        compiledModules.set(filePath, transpiled.outputText);
    }

    const memberViewPath = path.resolve('app/_lib/member-view.ts');
    const apiClientPath = path.resolve('app/_lib/api-client.ts');
    const memberViewCode = compiledModules.get(memberViewPath).replace(
        "./api-client",
        `data:text/javascript;base64,${Buffer.from(compiledModules.get(apiClientPath)).toString('base64')}`,
    );

    const moduleUrl = `data:text/javascript;base64,${Buffer.from(memberViewCode).toString('base64')}`;
    return import(moduleUrl);
}

test('現在所属の要約を複数グループで組み立てられる', async () => {
    const { getCurrentGroupSummary } = await loadMemberViewModule();

    const result = getCurrentGroupSummary({
        currentGroups: [
            { groupName: '=LOVE', generationLabel: '1期生' },
            { groupName: 'ユニットA', generationLabel: null },
        ],
    });

    assert.equal(result, '=LOVE 1期生 / ユニットA');
});

test('履歴ステータスは leftOn の有無で表示を切り替える', async () => {
    const { getHistoryStatusLabel } = await loadMemberViewModule();

    assert.equal(getHistoryStatusLabel(null), '在籍中');
    assert.match(getHistoryStatusLabel('2024-05-01'), /2024年5月1日/);
});

test('ピックアップ楽曲は指定件数まで切り出す', async () => {
    const { pickFeaturedSongs } = await loadMemberViewModule();

    const result = pickFeaturedSongs(
        [{ songId: 1 }, { songId: 2 }, { songId: 3 }, { songId: 4 }],
        2,
    );

    assert.deepEqual(result, [{ songId: 1 }, { songId: 2 }]);
});
