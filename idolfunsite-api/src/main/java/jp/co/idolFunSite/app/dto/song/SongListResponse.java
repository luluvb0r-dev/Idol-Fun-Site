package jp.co.idolFunSite.app.dto.song;

import java.time.LocalDate;
import java.util.List;

/**
 * 楽曲の一覧用に返却するレスポンスDTOです。
 */
public record SongListResponse(
        Long songId,
        String title,
        String titleKana,
        PrimaryReleaseResponse primaryRelease,
        List<OriginalMemberResponse> originalMembers,
        Boolean hasCallData) {

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
     * 一覧用オリジナル歌唱メンバーです。
     */
    public record OriginalMemberResponse(
            Long memberId,
            String name) {
    }
}
