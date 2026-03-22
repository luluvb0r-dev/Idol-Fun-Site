package jp.co.idolFunSite.domain.site;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.common.Status;
import jp.co.idolFunSite.domain.common.Visibility;
import jakarta.persistence.*;

/**
 * ファンサイト基本情報を管理するエンティティ
 */
@Entity
@Table(name = "site")
public class Site extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "site_key", nullable = false, unique = true, length = 100)
    private String siteKey;

    @Column(name = "site_name", nullable = false, length = 200)
    private String siteName;

    @Column(name = "idol_name", nullable = false, length = 200)
    private String idolName;

    /** サイトを作成した認証ユーザーのID（将来拡張用） */
    @Column(name = "owner_user_id")
    private Long ownerUserId;

    /** 公開範囲（PUBLIC/UNLISTED/PRIVATE） */
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false, length = 20)
    private Visibility visibility = Visibility.PUBLIC;

    /** サイトの状態（DRAFT/PUBLISHED/ARCHIVED） */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status = Status.PUBLISHED;

    /** システム管理者が初期提供した公式データサイトかどうかのフラグ */
    @Column(name = "is_seeded_site", nullable = false)
    private Boolean isSeededSite = false;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSiteKey() { return siteKey; }
    public void setSiteKey(String siteKey) { this.siteKey = siteKey; }
    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }
    public String getIdolName() { return idolName; }
    public void setIdolName(String idolName) { this.idolName = idolName; }
    public Long getOwnerUserId() { return ownerUserId; }
    public void setOwnerUserId(Long ownerUserId) { this.ownerUserId = ownerUserId; }
    public Visibility getVisibility() { return visibility; }
    public void setVisibility(Visibility visibility) { this.visibility = visibility; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Boolean getIsSeededSite() { return isSeededSite; }
    public void setIsSeededSite(Boolean isSeededSite) { this.isSeededSite = isSeededSite; }
}
