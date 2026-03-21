package jp.co.idolFunSite.domain.single;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.site.Site;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * シングルCDやアルバムなどのリリース作品情報を管理するエンティティ。
 */
@Entity
@Table(name = "single_release")
public class SingleRelease extends BaseEntity {

    /** 作品ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 対象サイト */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    /** 作品の種別（SINGLE, ALBUM等） */
    @Enumerated(EnumType.STRING)
    @Column(name = "release_type", nullable = false, length = 30)
    private ReleaseType releaseType = ReleaseType.SINGLE;

    /** 作品のタイトル（例：あの子コンプレックス） */
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /** タイトルの読み仮名 */
    @Column(name = "title_kana", length = 200)
    private String titleKana;

    /** 発売日・リリース日 */
    @Column(name = "release_date")
    private LocalDate releaseDate;

    /** 規格品番やカタログ番号（任意） */
    @Column(name = "catalog_number", length = 100)
    private String catalogNumber;

    /** ジャケット画像のURLパス */
    @Column(name = "jacket_image_url", length = 1000)
    private String jacketImageUrl;

    /** 一覧・リスト出力時などの優先表示順 */
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Site getSite() { return site; }
    public void setSite(Site site) { this.site = site; }
    public ReleaseType getReleaseType() { return releaseType; }
    public void setReleaseType(ReleaseType releaseType) { this.releaseType = releaseType; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getTitleKana() { return titleKana; }
    public void setTitleKana(String titleKana) { this.titleKana = titleKana; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
    public String getCatalogNumber() { return catalogNumber; }
    public void setCatalogNumber(String catalogNumber) { this.catalogNumber = catalogNumber; }
    public String getJacketImageUrl() { return jacketImageUrl; }
    public void setJacketImageUrl(String jacketImageUrl) { this.jacketImageUrl = jacketImageUrl; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
