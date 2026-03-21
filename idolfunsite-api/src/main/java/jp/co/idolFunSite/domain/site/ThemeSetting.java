package jp.co.idolFunSite.domain.site;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jakarta.persistence.*;

/**
 * 各サイトに適用されるデザインやテーマカラーの設定を管理するエンティティ。
 */
@Entity
@Table(name = "theme_setting")
public class ThemeSetting extends BaseEntity {

    /** テーマ設定ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** このテーマ設定が紐づくサイト */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false, unique = true)
    private Site site;

    /** ブランド等のメインカラー HEXコード */
    @Column(name = "primary_color_hex", length = 20)
    private String primaryColorHex;

    /** メインカラーを補完するサブカラー HEXコード */
    @Column(name = "secondary_color_hex", length = 20)
    private String secondaryColorHex;

    /** 目立たせたい部分などに使用するアクセントカラー HEXコード */
    @Column(name = "accent_color_hex", length = 20)
    private String accentColorHex;

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
