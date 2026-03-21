package jp.co.idolFunSite.domain.group;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.site.Site;
import jakarta.persistence.*;

/**
 * アイドルグループの基本情報を管理するエンティティ。
 */
@Entity
@Table(name = "idol_group")
public class IdolGroup extends BaseEntity {

    /** グループID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 所属するサイト */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    /** 人間が読みやすい形式のグループ表示名 */
    @Column(name = "group_name", nullable = false, length = 200)
    private String groupName;

    /** グループ名の読み仮名（ソートや検索用） */
    @Column(name = "group_kana", length = 200)
    private String groupKana;

    /** 登記や公式発表などで用いられる正式名称 */
    @Column(name = "official_name", length = 200)
    private String officialName;

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
}
