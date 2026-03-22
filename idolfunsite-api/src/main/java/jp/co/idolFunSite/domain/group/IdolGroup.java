package jp.co.idolFunSite.domain.group;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.site.Site;
import jakarta.persistence.*;

/**
 * アイドルグループ情報を管理するエンティティ
 */
@Entity
@Table(name = "idol_group")
public class IdolGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @Column(name = "group_name", nullable = false, length = 200)
    private String groupName;

    @Column(name = "group_kana", length = 200)
    private String groupKana;

    @Column(name = "official_name", length = 200)
    private String officialName;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Site getSite() { return site; }
    public void setSite(Site site) { this.site = site; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public String getGroupKana() { return groupKana; }
    public void setGroupKana(String groupKana) { this.groupKana = groupKana; }
    public String getOfficialName() { return officialName; }
    public void setOfficialName(String officialName) { this.officialName = officialName; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
