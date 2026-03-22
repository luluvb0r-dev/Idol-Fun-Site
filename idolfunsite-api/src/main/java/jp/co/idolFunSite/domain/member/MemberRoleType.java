package jp.co.idolFunSite.domain.member;

/**
 * メンバーのグループにおける役職・役割を表す列挙型
 */
public enum MemberRoleType {
    /** 一般メンバー */
    MEMBER,
    /** キャプテン・リーダー */
    CAPTAIN,
    /** 副キャプテン */
    VICE_CAPTAIN,
    /** その他マネージャー等 */
    OTHER
}
