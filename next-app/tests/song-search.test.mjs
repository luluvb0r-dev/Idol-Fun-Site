import assert from 'node:assert/strict';
import test from 'node:test';
import fs from 'node:fs/promises';
import path from 'node:path';
import ts from 'typescript';

async function loadSongSearchModule() {
    const filePath = path.resolve('app/_lib/song-search.ts');
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

test('検索パラメータを画面用の状態へ正規化できる', async () => {
    const { normalizeSongSearchParams } = await loadSongSearchModule();

    const filters = normalizeSongSearchParams({
        keyword: '  青春  ',
        releaseId: '12',
        isTitleTrack: 'true',
        page: '3',
    });

    assert.deepEqual(filters, {
        keyword: '青春',
        releaseId: '12',
        isTitleTrack: true,
        page: 3,
    });
});

test('空や不正な値は安全な初期値へ寄せる', async () => {
    const { normalizeSongSearchParams } = await loadSongSearchModule();

    const filters = normalizeSongSearchParams({
        keyword: ['  '],
        releaseId: 'abc',
        isTitleTrack: 'no',
        page: '-2',
    });

    assert.deepEqual(filters, {
        keyword: '',
        releaseId: '',
        isTitleTrack: false,
        page: 0,
    });
});

test('API 用のクエリ文字列を必要な条件だけで組み立てる', async () => {
    const { buildSongSearchQuery } = await loadSongSearchModule();

    const query = buildSongSearchQuery({
        keyword: '青春',
        releaseId: '5',
        isTitleTrack: true,
        page: 1,
    });

    assert.equal(query.get('keyword'), '青春');
    assert.equal(query.get('releaseId'), '5');
    assert.equal(query.get('isTitleTrack'), 'true');
    assert.equal(query.get('page'), '1');
    assert.equal(query.get('size'), '20');
    assert.equal(query.get('sort'), 'releaseDate,desc');
});
