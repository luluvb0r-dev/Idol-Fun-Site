package jp.co.idolFunSite.domain.member;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.group.IdolGroup;
import jp.co.idolFunSite.domain.site.Site;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "member_group_history")
public class MemberGroupHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private IdolGroup group;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false, length = 30)
    private MemberRoleType roleType = MemberRoleType.MEMBER;

    @Column(name = "generation_label", length = 50)
    private String generationLabel;

    @Column(name = "joined_on")
    private LocalDate joinedOn;

    @Column(name = "left_on")
    private LocalDate leftOn;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = true;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Site getSite() { return site; }
    public void setSite(Site site) { this.site = site; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public IdolGroup getGroup() { return group; }
    public void setGroup(IdolGroup group) { this.group = group; }
    public MemberRoleType getRoleType() { return roleType; }
    public void setRoleType(MemberRoleType roleType) { this.roleType = roleType; }
    public String getGenerationLabel() { return generationLabel; }
    public void setGenerationLabel(String generationLabel) { this.generationLabel = generationLabel; }
    public LocalDate getJoinedOn() { return joinedOn; }
    public void setJoinedOn(LocalDate joinedOn) { this.joinedOn = joinedOn; }
    public LocalDate getLeftOn() { return leftOn; }
    public void setLeftOn(LocalDate leftOn) { this.leftOn = leftOn; }
    public Boolean getIsPrimary() { return isPrimary; }
    public void setIsPrimary(Boolean isPrimary) { this.isPrimary = isPrimary; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
