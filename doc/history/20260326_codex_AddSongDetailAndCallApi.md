# 2026-03-26 Codex 作業履歴

## Song 詳細API・コールAPI

- `GET /api/v1/sites/{siteKey}/songs/{songId}` を追加し、楽曲基本情報、主リリース情報、収録一覧、オリジナルメンバーを返すようにした。
- `GET /api/v1/sites/{siteKey}/songs/{songId}/calls` を追加し、ブロック、歌詞行、コール項目をネストしたレスポンスを返すようにした。
- `call_type_master` を参照し、コール表示用のラベルやスタイル情報を組み立てるようにした。
- 楽曲詳細API・楽曲コールAPIの service/controller 統合テストを追加した。

## Step 5: メンバー一覧API・メンバー詳細API

- `GET /api/v1/sites/{siteKey}/members` を追加し、公開サイト配下の有効メンバー一覧を返すようにした。
- `GET /api/v1/sites/{siteKey}/members/{memberId}` を追加し、対象メンバーの最小プロフィール情報を返すようにした。
- response DTO は情報量を絞り、`名前` `誕生日` `メンバーカラー` `メンバーカラーHEX` `簡単な紹介文` のみを返す構成にした。
- `MemberService` を追加し、サイト公開状態の確認とメンバー取得処理を集約した。
- `MemberRepository` に一覧取得用・詳細取得用の最低限のクエリメソッドを追加した。
- メンバー一覧API・メンバー詳細APIの service/controller 統合テストを追加し、アクティブメンバーのみ取得されることを確認した。
