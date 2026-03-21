package jp.co.idolFunSite.domain.site;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.common.Status;
import jakarta.persistence.*;

/**
 * ファンサイト自体を表現するエンティティ。
 * 将来的なマルチサイト（複数アイドルポータル）展開の基点となります。
 */
@Entity
@Table(name = "site")
public class Site extends BaseEntity {

    /** サイトID（システム内部キー） */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** サイト識別キー（URLパスやAPIリクエスト判定等に使用）例: "equal-love" */
    @Column(name = "site_key", nullable = false, unique = true, length = 100)
    private String siteKey;

    /** サイトの表示名 */
    @Column(name = "site_name", nullable = false, length = 200)
    private String siteName;

    /** 対象とするアイドルのメイン名称 */
    @Column(name = "idol_name", nullable = false, length = 200)
    private String idolName;

    /** サイトの運用ステータス */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status = Status.ACTIVE;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSiteKey() { return siteKey; }
    public void setSiteKey(String siteKey) { this.siteKey = siteKey; }
    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }
    public String getIdolName() { return idolName; }
    public void setIdolName(String idolName) { this.idolName = idolName; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
