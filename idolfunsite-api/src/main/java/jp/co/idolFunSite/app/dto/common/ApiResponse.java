package jp.co.idolFunSite.app.dto.common;

import java.util.List;

/**
 * API共通レスポンスです。
 *
 * @param <T> データ部の型
 */
public record ApiResponse<T>(
        T data,
        ApiMeta meta,
        List<ApiError> errors) {

    private static final String VERSION = "v1";

    /**
     * 正常系レスポンスを生成します。
     *
     * @param data データ
     * @param requestId リクエストID
     * @param <T> データ型
     * @return 共通レスポンス
     */
    public static <T> ApiResponse<T> success(T data, String requestId) {
        return new ApiResponse<>(data, new ApiMeta(requestId, VERSION), List.of());
    }

    /**
     * 異常系レスポンスを生成します。
     *
     * @param requestId リクエストID
     * @param errors エラー一覧
     * @param <T> データ型
     * @return 共通レスポンス
     */
    public static <T> ApiResponse<T> failure(String requestId, List<ApiError> errors) {
        return new ApiResponse<>(null, new ApiMeta(requestId, VERSION), errors);
    }
}
