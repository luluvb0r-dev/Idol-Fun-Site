package jp.co.idolFunSite.domain.common;

/**
 * レコードの有効・無効など、汎用的な運用ステータスを表す列挙型
 */
public enum Status {
    /** 有効状態 */
    ACTIVE,
    /** 無効または論理削除状態 */
    INACTIVE
}
