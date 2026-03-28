import assert from 'node:assert/strict';
import test from 'node:test';
import fs from 'node:fs/promises';
import path from 'node:path';
import ts from 'typescript';

async function loadMockModule() {
    const filePath = path.resolve('app/_data/mock.ts');
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

test('仮データの一覧とメンバーが最低1件ずつ存在する', async () => {
    const { articles, members } = await loadMockModule();

    assert.ok(Array.isArray(articles));
    assert.ok(Array.isArray(members));
    assert.ok(articles.length > 0);
    assert.ok(members.length > 0);
});

test('記事の memberSlug は必ず存在するメンバーを参照している', async () => {
    const { articles, members } = await loadMockModule();
    const memberSlugs = new Set(members.map((member) => member.slug));

    for (const article of articles) {
        assert.ok(memberSlugs.has(article.memberSlug), `未定義メンバーを参照: ${article.id}`);
    }
});

test('検索関数で期待する仮データを取得できる', async () => {
    const { getArticleById, getMemberBySlug } = await loadMockModule();

    assert.equal(getArticleById('spring-tour-preview')?.title, '=LOVE Spring Tour Preview');
    assert.equal(getMemberBySlug('hana')?.name, '花音');
    assert.equal(getArticleById('unknown'), undefined);
    assert.equal(getMemberBySlug('unknown'), undefined);
});
