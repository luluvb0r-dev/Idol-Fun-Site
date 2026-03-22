package jp.co.idolFunSite.domain.alias;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.member.Member;
import jp.co.idolFunSite.domain.site.Site;
import jakarta.persistence.*;

@Entity
@Table(name = "member_alias")
public class MemberAlias extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "alias_type", nullable = false, length = 30)
    private AliasType aliasType = AliasType.SEARCH;

    @Column(name = "alias_text", nullable = false, length = 255)
    private String aliasText;

    @Column(name = "normalized_text", nullable = false, length = 255)
    private String normalizedText;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Site getSite() { return site; }
    public void setSite(Site site) { this.site = site; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public AliasType getAliasType() { return aliasType; }
    public void setAliasType(AliasType aliasType) { this.aliasType = aliasType; }
    public String getAliasText() { return aliasText; }
    public void setAliasText(String aliasText) { this.aliasText = aliasText; }
    public String getNormalizedText() { return normalizedText; }
    public void setNormalizedText(String normalizedText) { this.normalizedText = normalizedText; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
