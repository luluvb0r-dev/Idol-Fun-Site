package jp.co.idolFunSite.app.dto.common;

import java.util.List;

/**
 * items 配列を返す一覧レスポンスです。
 *
 * @param <T> 要素型
 */
public record ItemListResponse<T>(
        List<T> items) {
}
