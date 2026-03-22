package jp.co.idolFunSite.domain.common;

/**
 * サイトやデータの公開ステータスを表す列挙型
 */
public enum Status {
    /** 下書き・作成中 */
    DRAFT,
    /** 公開済み */
    PUBLISHED,
    /** アーカイブ済み・過去データ */
    ARCHIVED
}
