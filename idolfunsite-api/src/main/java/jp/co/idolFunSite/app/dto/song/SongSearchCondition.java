package jp.co.idolFunSite.app.dto.song;

/**
 * 楽曲検索時の条件を指定するDTOです。
 */
public record SongSearchCondition(
        Long siteId,
        String keyword) {
}
