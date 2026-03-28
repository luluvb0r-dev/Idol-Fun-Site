package jp.co.idolFunSite.app.dto.common;

import java.util.List;

/**
 * items と pagination を返す一覧レスポンスです。
 *
 * @param <T> 要素型
 */
public record PagedItemListResponse<T>(
        List<T> items,
        PaginationResponse pagination) {
}
