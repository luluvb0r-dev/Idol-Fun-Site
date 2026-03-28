package jp.co.idolFunSite.app.dto.master;

/**
 * コール種別マスタレスポンスです。
 */
public record CallTypeMasterResponse(
        String callTypeCode,
        String callTypeLabel,
        String colorHex,
        String iconKey) {
}
