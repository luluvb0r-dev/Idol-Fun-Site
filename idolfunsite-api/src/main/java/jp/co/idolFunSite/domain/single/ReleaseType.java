package jp.co.idolFunSite.domain.single;

/**
 * リリース作品の種別（シングル、アルバムなど）を表す列挙型
 */
public enum ReleaseType {
    /** シングルCD */
    SINGLE,
    /** アルバムCD */
    ALBUM,
    /** デジタル配信シングル・アルバム */
    DIGITAL,
    /** その他（DVD付録など） */
    OTHER
}
