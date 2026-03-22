package jp.co.idolFunSite.domain.common;

/**
 * サイトの公開状態を表す列挙型
 */
public enum Visibility {
    /** 全体公開 */
    PUBLIC,
    /** リンクを知っている人のみ */
    UNLISTED,
    /** 非公開・自分のみ */
    PRIVATE
}
