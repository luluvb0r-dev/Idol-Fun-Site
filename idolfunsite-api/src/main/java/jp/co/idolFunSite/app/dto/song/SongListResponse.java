package jp.co.idolFunSite.app.dto.song;

import java.util.List;

/**
 * 楽曲の一覧用に返却するレスポンスDTOです。
 */
public record SongListResponse(
        Long songId,
        String title,
        List<String> originalMembers,
        String singleTitle,
        boolean isTitleTrack) {
}
