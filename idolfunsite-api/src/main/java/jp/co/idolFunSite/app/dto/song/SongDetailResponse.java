package jp.co.idolFunSite.app.dto.song;

import java.time.LocalDate;
import java.util.List;

/**
 * 楽曲詳細画面向けのレスポンスDTOです。
 */
public record SongDetailResponse(
        Long songId,
        String title,
        String titleKana,
        String description,
        Boolean hasCallData,
        PrimaryReleaseResponse primaryRelease,
        List<ReleaseResponse> releases,
        List<OriginalMemberResponse> originalMembers) {

    /**
     * 代表作品情報です。
     */
    public record PrimaryReleaseResponse(
            Long releaseId,
            String title,
            LocalDate releaseDate,
            Boolean isTitleTrack) {
    }

    /**
     * 収録作品一覧の1件分です。
     */
    public record ReleaseResponse(
            Long releaseId,
            String title,
            LocalDate releaseDate,
            Integer trackNumber,
            Boolean isPrimary,
            Boolean isTitleTrack) {
    }

    /**
     * オリジナル歌唱メンバー情報です。
     */
    public record OriginalMemberResponse(
            Long memberId,
            String name,
            String memberColorHex) {
    }
}
