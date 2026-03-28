package jp.co.idolFunSite.app.dto.common;

/**
 * 一覧系ページング情報です。
 */
public record PaginationResponse(
        int page,
        int size,
        long totalElements,
        int totalPages) {
}
