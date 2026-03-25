# 楽曲詳細API・楽曲コールAPIの実装

- `GET /api/v1/sites/{siteKey}/songs/{songId}` を追加し、楽曲基本情報、代表作品、収録作品一覧、オリジナル歌唱メンバー、説明文、コール有無を返すようにしました。
- `GET /api/v1/sites/{siteKey}/songs/{songId}/calls` を追加し、ブロック単位・歌詞行単位でコール情報を返すDTOを実装しました。
- コール情報は「歌詞行の下にコールを表示する」前提で、各行配下に `calls` を持つレスポンス構造に整理しました。
- `call type` は `CallType` enum として整理し、最低限 `CHANT` と `CLAP` を区別できるようにしました。
- コール表示メタデータは `call_type_master` を参照し、`callTypeCode`、`callTypeLabel`、`style.colorHex`、`style.iconKey` を返すようにしました。
- 初期データのコール種別ラベルを API仕様書に合わせて `掛け声`、`クラップ` に修正しました。
- 楽曲詳細・楽曲コール取得の結合テストを追加しました。
- 楽曲一覧・楽曲詳細・楽曲コールの各 API を `MockMvc` で確認する統合テストを追加しました。
