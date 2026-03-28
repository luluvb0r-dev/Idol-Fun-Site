package jp.co.idolFunSite.app.dto.site;

/**
 * サイト情報レスポンスです。
 */
public record SiteResponse(
        String siteKey,
        String siteName,
        String idolName,
        ThemeResponse theme) {

    /**
     * テーマ情報です。
     */
    public record ThemeResponse(
            String primaryColorHex,
            String secondaryColorHex,
            String accentColorHex) {
    }
}
