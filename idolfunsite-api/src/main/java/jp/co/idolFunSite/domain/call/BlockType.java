package jp.co.idolFunSite.domain.call;

/**
 * 楽曲歌詞のブロック（Aメロ、Bメロ、サビなど）の種別を表す列挙型
 */
public enum BlockType {
    /** Aメロなどの一般的な平歌部分 */
    VERSE,
    /** サビ */
    CHORUS,
    /** 大サビ・Cメロなど */
    BRIDGE,
    /** Bメロ・サビ前 */
    PRE_CHORUS,
    /** アウトロ（後奏） */
    OUTRO,
    /** イントロや間奏などその他のブロック */
    OTHER
}
