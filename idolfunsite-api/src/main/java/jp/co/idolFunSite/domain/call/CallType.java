package jp.co.idolFunSite.domain.call;

/**
 * 行に対応するコールの種類を表す列挙型
 */
public enum CallType {
    /** 掛け声（「はい！」「おー！」など） */
    CHANT,
    /** クラップ・手拍子 */
    CLAP,
    /** MIX（「タイガー、ファイヤー...」など） */
    MIX,
    /** ジャンプ（振り付けに合わせたジャンプ指示） */
    JUMP,
    /** 合いの手やその他のコール */
    OTHER
}
