package jp.co.idolFunSite.domain.master;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.site.Site;
import jakarta.persistence.*;

@Entity
@Table(name = "call_type_master")
public class CallTypeMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @Column(name = "call_type_code", nullable = false, length = 30)
    private String callTypeCode;

    @Column(name = "call_type_label", nullable = false, length = 100)
    private String callTypeLabel;

    @Column(name = "color_hex", length = 20)
    private String colorHex;

    @Column(name = "icon_key", length = 50)
    private String iconKey;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Site getSite() { return site; }
    public void setSite(Site site) { this.site = site; }
    public String getCallTypeCode() { return callTypeCode; }
    public void setCallTypeCode(String callTypeCode) { this.callTypeCode = callTypeCode; }
    public String getCallTypeLabel() { return callTypeLabel; }
    public void setCallTypeLabel(String callTypeLabel) { this.callTypeLabel = callTypeLabel; }
    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }
    public String getIconKey() { return iconKey; }
    public void setIconKey(String iconKey) { this.iconKey = iconKey; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
