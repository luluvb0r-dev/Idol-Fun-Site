# API一覧と画面対応表

## 改訂履歴

| Version | Date       | Author | Changes |
|---------|------------|--------|---------|
| 0.1.0   | 2026-03-26 | Codex  | 初版作成 |

---

## 1. 目的
本書は、フロントエンド実装時に「どの画面でどの API を使うか」を素早く確認するための対応表である。  
[`doc/02_フロントエンド実装ステップ.md`](c:/Users/luluv/IdolFunSite/doc/02_フロントエンド実装ステップ.md) と
[`doc/05_API仕様書.md`](c:/Users/luluv/IdolFunSite/doc/05_API仕様書.md) の橋渡しとして利用する。

---

## 2. 前提

- API の Base URL は `/api/v1`
- 参照系 API はすべて `siteKey` を前提にする
- 共通レスポンス形式は `data` / `meta` / `errors`
- フロントエンドでは API 生レスポンス型と画面描画用 ViewModel を分離する

---

## 3. API一覧

| API | Method | 主用途 | 主な利用画面 |
|-----|--------|--------|--------------|
| `/sites/{siteKey}` | GET | サイト基本情報、テーマ取得 | `/`、共通レイアウト |
| `/sites/{siteKey}/songs` | GET | 楽曲一覧、検索、絞り込み | `/songs` |
| `/sites/{siteKey}/songs/{songId}` | GET | 楽曲基本情報、作品、歌唱メンバー取得 | `/songs/[id]` |
| `/sites/{siteKey}/songs/{songId}/calls` | GET | コール表示データ取得 | `/songs/[id]` |
| `/sites/{siteKey}/members` | GET | メンバー一覧取得 | `/members` |
| `/sites/{siteKey}/members/{memberId}` | GET | メンバー詳細、所属履歴取得 | `/members/[id]` |
| `/sites/{siteKey}/releases` | GET | 作品フィルタ候補取得 | `/songs` |
| `/sites/{siteKey}/masters/call-types` | GET | コール種別メタ情報取得 | `/songs/[id]` |

---

## 4. 画面対応表

## 4.1 トップページ `/`

### 使用 API
- `GET /sites/{siteKey}`
- 任意: `GET /sites/{siteKey}/songs`

### 主な用途
- サイトタイトル表示
- テーマカラー反映
- 楽曲一覧・メンバー一覧への導線表示
- ピックアップ楽曲表示

### 画面で使う主な項目
- `siteName`
- `idolName`
- `theme.primaryColorHex`
- `theme.secondaryColorHex`
- `theme.accentColorHex`
- 任意で `songs.items[].songId`
- 任意で `songs.items[].title`

### フロント実装メモ
- サイト情報はレイアウト初期表示にも使うため、共通取得関数化してよい
- ピックアップ楽曲を出す場合、現状 API に専用項目はないため、一覧の先頭数件を流用する

---

## 4.2 楽曲一覧ページ `/songs`

### 使用 API
- `GET /sites/{siteKey}/songs`
- `GET /sites/{siteKey}/releases`

### 主な用途
- 楽曲カード一覧表示
- キーワード検索
- 作品絞り込み
- 表題曲フィルタ
- ページング

### 主なクエリ
- `keyword`
- `releaseId`
- `isTitleTrack`
- `memberId`
- `page`
- `size`
- `sort`

### 画面で使う主な項目
- `items[].songId`
- `items[].title`
- `items[].titleKana`
- `items[].primaryRelease.title`
- `items[].primaryRelease.releaseDate`
- `items[].primaryRelease.isTitleTrack`
- `items[].originalMembers[].name`
- `items[].hasCallData`
- `pagination.page`
- `pagination.totalPages`
- `releases.items[].releaseId`
- `releases.items[].title`

### フロント実装メモ
- 検索フォームは URL クエリと同期させると再訪しやすい
- `primaryRelease` は null の可能性を考慮する
- `hasCallData` が true の楽曲だけ「コールあり」バッジを出せる
- 絞り込み候補の作品は `releases` API を事前取得して select に流し込む

---

## 4.3 楽曲詳細ページ `/songs/[id]`

### 使用 API
- `GET /sites/{siteKey}/songs/{songId}`
- `GET /sites/{siteKey}/songs/{songId}/calls`
- `GET /sites/{siteKey}/masters/call-types`

### 主な用途
- 楽曲ヘッダー情報表示
- 複数作品表示
- オリジナル歌唱メンバー表示
- コール表示
- コール凡例表示

### 画面で使う主な項目
- `songId`
- `title`
- `titleKana`
- `description`
- `hasCallData`
- `primaryRelease.title`
- `releases[].title`
- `releases[].trackNumber`
- `releases[].isPrimary`
- `releases[].isTitleTrack`
- `originalMembers[].name`
- `originalMembers[].memberColorHex`
- `calls.blocks[].blockType`
- `calls.blocks[].blockLabel`
- `calls.blocks[].lines[].lyrics`
- `calls.blocks[].lines[].calls[].callTypeCode`
- `calls.blocks[].lines[].calls[].callTypeLabel`
- `calls.blocks[].lines[].calls[].callText`
- `calls.blocks[].lines[].calls[].style.colorHex`
- `calls.blocks[].lines[].calls[].style.iconKey`
- `callTypes.items[].callTypeCode`
- `callTypes.items[].callTypeLabel`
- `callTypes.items[].colorHex`
- `callTypes.items[].iconKey`

### フロント実装メモ
- `songs/{id}` と `songs/{id}/calls` は並列取得でよい
- `masters/call-types` は凡例表示や種別辞書の補完に使える
- 描画時は `calls[].style` を最優先し、足りない場合だけマスタで補う
- `hasCallData=false` の場合はコール領域を Empty State にする

---

## 4.4 メンバー一覧ページ `/members`

### 使用 API
- `GET /sites/{siteKey}/members`

### 主な用途
- メンバーカード一覧表示
- 在籍ステータス別表示

### 主なクエリ
- `status`
- `sort`

### 画面で使う主な項目
- `items[].memberId`
- `items[].name`
- `items[].birthday`
- `items[].memberColor`
- `items[].memberColorHex`
- `items[].currentGroup.groupName`
- `items[].profileImageUrl`

### フロント実装メモ
- `profileImageUrl` が null の場合はプレースホルダ表示にする
- `memberColorHex` は装飾用、`memberColor` はラベル用として併用すると分かりやすい
- 将来 `status=INACTIVE` を使って卒業メンバー表示へ拡張しやすい

---

## 4.5 メンバー詳細ページ `/members/[id]`

### 使用 API
- `GET /sites/{siteKey}/members/{memberId}`

### 主な用途
- 基本プロフィール表示
- 現在所属表示
- 所属履歴表示

### 画面で使う主な項目
- `memberId`
- `name`
- `birthday`
- `memberColor`
- `memberColorHex`
- `birthplace`
- `shortBio`
- `profileImageUrl`
- `currentGroups[].groupName`
- `currentGroups[].generationLabel`
- `currentGroups[].joinedOn`
- `groupHistory[].groupName`
- `groupHistory[].generationLabel`
- `groupHistory[].joinedOn`
- `groupHistory[].leftOn`
- `groupHistory[].isPrimary`

### フロント実装メモ
- `currentGroups` は複数所属を前提に配列で扱う
- `groupHistory` は時系列 UI にすると拡張しやすい
- `leftOn=null` は「在籍中」表示に変換すると分かりやすい

---

## 5. 共通レイアウト対応

## 5.1 共通取得候補
- `GET /sites/{siteKey}`

### 用途
- ヘッダーのサイト名
- OGP / title 生成の元データ
- テーマカラー CSS Variables 初期化

### 実装メモ
- App Router の `layout.tsx` または共通 provider で取得候補
- 将来 `siteKey` を URL に含める場合でも再利用しやすい

---

## 6. フロント側の推奨 ViewModel

## 6.1 SiteViewModel
- `siteName`
- `idolName`
- `theme`

## 6.2 SongCardViewModel
- `id`
- `title`
- `titleKana`
- `primaryReleaseTitle`
- `primaryReleaseDate`
- `isTitleTrack`
- `originalMemberNames`
- `hasCallData`

## 6.3 SongDetailViewModel
- `id`
- `title`
- `description`
- `primaryRelease`
- `releaseSummaries`
- `originalMembers`
- `hasCallData`

## 6.4 SongCallViewModel
- `title`
- `blocks`
- `legendItems`

## 6.5 MemberCardViewModel
- `id`
- `name`
- `birthday`
- `memberColorLabel`
- `memberColorHex`
- `currentGroupName`
- `profileImageUrl`

## 6.6 MemberDetailViewModel
- `id`
- `name`
- `profile`
- `currentGroups`
- `historyItems`

---

## 7. 実装優先順

フロント着手時は以下の順を推奨する。

1. `GET /sites/{siteKey}` を使った共通レイアウト・テーマ反映
2. `GET /sites/{siteKey}/songs` と `GET /sites/{siteKey}/releases` を使った楽曲一覧
3. `GET /sites/{siteKey}/songs/{songId}` と `GET /sites/{siteKey}/songs/{songId}/calls` を使った楽曲詳細
4. `GET /sites/{siteKey}/members` を使ったメンバー一覧
5. `GET /sites/{siteKey}/members/{memberId}` を使ったメンバー詳細
6. `GET /sites/{siteKey}/masters/call-types` を使ったコール凡例の強化

---

## 8. 注意点

- すべての API は共通レスポンスで包まれているため、フロント側ではまず `response.data` を取り出す
- `meta.requestId` はエラー画面やログ出力に使える
- null の可能性がある項目:
  - `primaryRelease`
  - `profileImageUrl`
  - `birthplace`
  - `description`
  - `style.colorHex`
  - `style.iconKey`
- `siteKey` を固定文字列で持つ場合でも、取得関数は引数で受ける形にしておくと将来拡張しやすい
