# 楽曲一覧APIの実装

- 楽曲情報を一覧で取得するAPIを実装しました。
- APIパスを `GET /api/v1/sites/{siteKey}/songs` に修正し、公開済みサイトの `siteKey` 解決に対応しました。
- 検索キーワードによる楽曲名（title, titleKana, titleRoman）と `song_alias.normalized_text` の部分一致検索に対応しました。
- `releaseId`、`isTitleTrack`、`memberId` を用いた絞り込み条件を追加しました。
- ページネーションを実装し、1ページあたりのデフォルト取得件数を20件、デフォルトソートを `displayOrder`, `id` に設定しました。
- 1+N問題を抑えるため、`IN` 句と `@EntityGraph` を使用してオリジナル歌唱メンバーおよび掲載シングルをまとめて取得するよう修正しました。
- 一覧DTOは「楽曲名」「オリジナル歌唱メンバー」「掲載シングル」「表題曲フラグ」を返す構成に整理しました。
- 空ファイル化していた `Member.java` をDB定義に合わせて復旧し、関連リポジトリも整合するよう修正しました。
- 未使用の重複 `domain/single` パッケージを削除し、アプリ起動時の `SingleReleaseRepository` 重複登録エラーを解消しました。
- H2 のインメモリDBがテスト中に破棄されないよう、接続URLに `DB_CLOSE_DELAY=-1` を追加しました。
- Spring Boot 4 で Flyway 自動設定が別モジュール化されていたため、`spring-boot-flyway` を追加して起動時マイグレーションを有効化しました。
- `contextLoads` を安定して実行できるよう、テスト専用の `application.properties` を追加し、H2 の `create-drop` でコンテキスト起動確認ができるようにしました。
