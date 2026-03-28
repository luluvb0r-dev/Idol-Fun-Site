package jp.co.idolFunSite.app.dto.common;

/**
 * APIレスポンス共通メタ情報です。
 */
public record ApiMeta(
        String requestId,
        String version) {
}
