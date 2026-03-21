package jp.co.idolFunSite.domain.member;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.common.Status;
import jp.co.idolFunSite.domain.group.IdolGroup;
import jp.co.idolFunSite.domain.site.Site;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * アイドルグループに所属するメンバーの詳細プロフィール情報を管理するエンティティ。
 */
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    /** メンバーID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** データが帰属するサイト */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    /** 所属するアイドルグループ */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private IdolGroup group;

    /** メンバー名 */
    @Column(name = "member_name", nullable = false, length = 200)
    private String memberName;

    /** メンバー名の読み仮名 */
    @Column(name = "member_name_kana", length = 200)
    private String memberNameKana;

    /** 生年月日 */
    @Column(name = "birthday")
    private LocalDate birthday;

    /** 言語化されたメンバーカラー表現（例："赤"、"パステルピンク"） */
    @Column(name = "member_color", length = 50)
    private String memberColor;

    /** カラー表示用のHEXコード */
    @Column(name = "member_color_hex", length = 20)
    private String memberColorHex;

    /** 出身地（都道府県など） */
    @Column(name = "birthplace", length = 100)
    private String birthplace;

    /** グループ加入年月日 */
    @Column(name = "joined_on")
    private LocalDate joinedOn;

    /** 加入期やジェネレーション（例："第1期生"） */
    @Column(name = "generation_label", length = 50)
    private String generationLabel;

    /** トップページ等で表示するキャッチコピーや短い紹介文 */
    @Column(name = "short_bio", length = 500)
    private String shortBio;

    /** プロフィール用画像リソースのURLパス */
    @Column(name = "profile_image_url", length = 1000)
    private String profileImageUrl;

    /** 一覧・リスト出力時などの優先表示順 */
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    /** 現在の在籍状況などのステータス */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status = Status.ACTIVE;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Site getSite() { return site; }
    public void setSite(Site site) { this.site = site; }
    public IdolGroup getGroup() { return group; }
    public void setGroup(IdolGroup group) { this.group = group; }
    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }
    public String getMemberNameKana() { return memberNameKana; }
    public void setMemberNameKana(String memberNameKana) { this.memberNameKana = memberNameKana; }
    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
    public String getMemberColor() { return memberColor; }
    public void setMemberColor(String memberColor) { this.memberColor = memberColor; }
    public String getMemberColorHex() { return memberColorHex; }
    public void setMemberColorHex(String memberColorHex) { this.memberColorHex = memberColorHex; }
    public String getBirthplace() { return birthplace; }
    public void setBirthplace(String birthplace) { this.birthplace = birthplace; }
    public LocalDate getJoinedOn() { return joinedOn; }
    public void setJoinedOn(LocalDate joinedOn) { this.joinedOn = joinedOn; }
    public String getGenerationLabel() { return generationLabel; }
    public void setGenerationLabel(String generationLabel) { this.generationLabel = generationLabel; }
    public String getShortBio() { return shortBio; }
    public void setShortBio(String shortBio) { this.shortBio = shortBio; }
    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
