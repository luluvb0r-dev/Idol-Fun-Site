package jp.co.idolFunSite.domain.alias;

/**
 * 別名の種類を表す列挙型
 */
public enum AliasType {
    /** 読み仮名 */
    KANA,
    /** ローマ字 */
    ROMAN,
    /** 汎用検索補助キーワード */
    SEARCH,
    /** ニックネーム・愛称 */
    NICKNAME,
    /** その他 */
    OTHER
}
