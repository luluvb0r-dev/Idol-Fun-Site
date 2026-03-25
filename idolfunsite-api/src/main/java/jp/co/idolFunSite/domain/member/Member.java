package jp.co.idolFunSite.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.site.Site;

/**
 * メンバー基本情報を管理するエンティティです。
 */
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @Column(name = "member_name", nullable = false, length = 200)
    private String memberName;

    @Column(name = "member_name_kana", length = 200)
    private String memberNameKana;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "member_color", length = 50)
    private String memberColor;

    @Column(name = "member_color_hex", length = 20)
    private String memberColorHex;

    @Column(name = "birthplace", length = 100)
    private String birthplace;

    @Column(name = "short_bio", length = 500)
    private String shortBio;

    @Column(name = "profile_image_url", length = 1000)
    private String profileImageUrl;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "ACTIVE";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberNameKana() {
        return memberNameKana;
    }

    public void setMemberNameKana(String memberNameKana) {
        this.memberNameKana = memberNameKana;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getMemberColor() {
        return memberColor;
    }

    public void setMemberColor(String memberColor) {
        this.memberColor = memberColor;
    }

    public String getMemberColorHex() {
        return memberColorHex;
    }

    public void setMemberColorHex(String memberColorHex) {
        this.memberColorHex = memberColorHex;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getShortBio() {
        return shortBio;
    }

    public void setShortBio(String shortBio) {
        this.shortBio = shortBio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
