import assert from 'node:assert/strict';
import test from 'node:test';
import fs from 'node:fs/promises';
import path from 'node:path';
import ts from 'typescript';

async function loadSongDetailMockModule() {
    const filePath = path.resolve('app/_data/song-detail-mock.ts');
    const source = await fs.readFile(filePath, 'utf8');
    const transpiled = ts.transpileModule(source, {
        compilerOptions: {
            module: ts.ModuleKind.ES2022,
            target: ts.ScriptTarget.ES2022,
        },
        fileName: filePath,
    });

    const moduleUrl = `data:text/javascript;base64,${Buffer.from(transpiled.outputText).toString('base64')}`;
    return import(moduleUrl);
}

test('楽曲詳細モックは楽曲情報とコール情報を同じ楽曲IDで返す', async () => {
    const { getMockSongCalls, getMockSongDetail } = await loadSongDetailMockModule();

    const song = getMockSongDetail(1);
    const calls = getMockSongCalls(1);

    assert.equal(song?.songId, 1);
    assert.equal(calls?.songId, 1);
    assert.equal(song?.title, '=LOVE');
    assert.ok(Array.isArray(calls?.blocks));
    assert.ok(calls.blocks.length > 0);
});

test('楽曲詳細モックには掛け声とクラップの両方が含まれる', async () => {
    const { getMockSongCalls } = await loadSongDetailMockModule();
    const calls = getMockSongCalls(1);

    const callTypeCodes = new Set(
        calls.blocks.flatMap((block) =>
            block.lines.flatMap((line) => line.calls.map((call) => call.callTypeCode)),
        ),
    );

    assert.ok(callTypeCodes.has('CHANT'));
    assert.ok(callTypeCodes.has('CLAP'));
});
