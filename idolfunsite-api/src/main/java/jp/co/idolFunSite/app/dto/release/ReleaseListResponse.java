package jp.co.idolFunSite.app.dto.release;

import java.time.LocalDate;

/**
 * 作品一覧レスポンスです。
 */
public record ReleaseListResponse(
        Long releaseId,
        String title,
        LocalDate releaseDate,
        String releaseType,
        String jacketImageUrl) {
}
