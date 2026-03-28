package jp.co.idolFunSite.app.dto.common;

/**
 * APIエラー情報です。
 */
public record ApiError(
        String code,
        String message) {
}
