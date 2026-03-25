package jp.co.idolFunSite.app.dto.song;

/**
 * 楽曲検索時の条件を指定するDTOです。
 */
public record SongSearchCondition(
        String siteKey,
        String keyword,
        Long releaseId,
        Boolean isTitleTrack,
        Long memberId) {

    /**
     * レコード生成時に検索条件を正規化します。
     *
     * @param siteKey      サイト識別子
     * @param keyword      検索キーワード
     * @param releaseId    シングル絞り込み条件
     * @param isTitleTrack 表題曲絞り込み条件
     * @param memberId     メンバー絞り込み条件
     */
    public SongSearchCondition {
        if (keyword != null) {
            keyword = keyword.trim();
            if (keyword.isEmpty()) {
                keyword = null;
            }
        }
    }
}
