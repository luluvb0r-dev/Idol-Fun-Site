package jp.co.idolFunSite.domain.song;

/**
 * 楽曲における歌唱メンバーの役割を表す列挙型
 */
public enum SongRoleType {
    /** オリジナル歌唱メンバー */
    ORIGINAL_VOCAL,
    /** 単独センター */
    CENTER,
    /** ダブルセンター等 */
    W_CENTER,
    /** その他 */
    OTHER
}
