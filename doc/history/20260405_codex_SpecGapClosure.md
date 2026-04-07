# 2026-04-05 Codex 仕様差分対応

## 対応内容
- Next.js フロントエンドで `site` / `member` / `call-types` API の取得層を追加
- トップページを仕様準拠の導線に差し替え、サイト情報とテーマカラーを共通レイアウトへ反映
- メンバー一覧ページ `/members` とメンバー詳細ページ `/members/[memberId]` を新規実装
- 楽曲詳細ページでコール種別マスタ API を利用し、凡例表示を強化
- メンバー表示用の純粋関数テストを追加

## 変更ファイル
- `next-app/app/_lib/api-client.ts`
- `next-app/app/_lib/site-api.ts`
- `next-app/app/_lib/member-api.ts`
- `next-app/app/_lib/member-view.ts`
- `next-app/app/_lib/song-api.ts`
- `next-app/app/_components/site-shell.tsx`
- `next-app/app/_components/song-detail.tsx`
- `next-app/app/_components/member-list.tsx`
- `next-app/app/_components/member-detail.tsx`
- `next-app/app/layout.tsx`
- `next-app/app/page.tsx`
- `next-app/app/members/page.tsx`
- `next-app/app/members/[memberId]/page.tsx`
- `next-app/app/songs/[songId]/page.tsx`
- `next-app/app/globals.css`
- `next-app/tests/member-view.test.mjs`

## 補足
- 既存のモック記事中心トップページと旧 `members/[slug]` ルートは仕様外だったため、API ベースの構成へ置き換え
- API 取得失敗時は 404 にせず、画面上でエラー状態を表示する方針に統一
