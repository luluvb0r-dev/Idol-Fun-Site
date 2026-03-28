# 20260325_codex_Step2_DBReviewFix.md

## 1. 今回対応した内容:
- `V1__init_schema.sql` の `call_type_master` / `song_call_item` に参照整合性を補う制約を追加
- `V2__insert_initial_data.sql` を全面的に見直し、文字化けと SQL 構文崩れを解消
- seed データの固定 ID 依存をなくし、`site_key` や名称を使った参照に置き換え

## 2. 変更したファイル一覧:
- `idolfunsite-api/src/main/resources/db/migration/V1__init_schema.sql`
- `idolfunsite-api/src/main/resources/db/migration/V2__insert_initial_data.sql`
- `doc/history/20260325_codex_Step2_DBReviewFix.md`

## 3. 実装上の補足:
- `call_type_code` の参照整合性を担保するため、`call_type_master.call_type_code` に一意制約を追加した
- seed は PostgreSQL の IDENTITY シーケンスずれを避けるため、明示的な ID 挿入をやめて副問い合わせで関連付けた
- =LOVE 用の最低限 seed として、サイト、テーマ、グループ、コール種別、メンバー、所属履歴、作品、楽曲、作品楽曲関連、歌唱メンバー、コール雛形を投入する形に整理した

## 4. 未対応事項:
- 既存の Java コードに `Status.ACTIVE` 参照などのコンパイルエラーがあり、Gradle テストでのアプリ起動確認までは未完了
- 将来的にサイトごとに `call_type_code` を別定義したい場合は、`song_call_item` 側のキー設計を再検討する必要がある

## 5. 私が次に指示すべき推奨ステップ:
- Step 2 の残確認として、既存エンティティのコンパイルエラー解消と migration 実行確認を行う

## 6. コミットメッセージ案:
[fix] Step2 の migration と seed データ不整合を修正
- `call_type_master` と `song_call_item` の参照整合性を補う制約を追加
- 文字化けしていた初期 seed SQL を全面修正
- SQL のクォート崩れを解消して Flyway で読める形に修正
- 固定 ID 依存の seed を廃止し、自然キー参照で関連データを投入する形に変更
