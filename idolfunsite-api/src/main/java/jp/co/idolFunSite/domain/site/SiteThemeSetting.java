package jp.co.idolFunSite.domain.site;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jakarta.persistence.*;

/**
 * サイトのテーマカラー情報を保持するエンティティ
 */
@Entity
@Table(name = "site_theme_setting")
public class SiteThemeSetting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false, unique = true)
    private Site site;

    @Column(name = "primary_color_hex", length = 20)
    private String primaryColorHex;

    @Column(name = "secondary_color_hex", length = 20)
    private String secondaryColorHex;

    @Column(name = "accent_color_hex", length = 20)
    private String accentColorHex;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Site getSite() { return site; }
    public void setSite(Site site) { this.site = site; }
    public String getPrimaryColorHex() { return primaryColorHex; }
    public void setPrimaryColorHex(String primaryColorHex) { this.primaryColorHex = primaryColorHex; }
    public String getSecondaryColorHex() { return secondaryColorHex; }
    public void setSecondaryColorHex(String secondaryColorHex) { this.secondaryColorHex = secondaryColorHex; }
    public String getAccentColorHex() { return accentColorHex; }
    public void setAccentColorHex(String accentColorHex) { this.accentColorHex = accentColorHex; }
}
