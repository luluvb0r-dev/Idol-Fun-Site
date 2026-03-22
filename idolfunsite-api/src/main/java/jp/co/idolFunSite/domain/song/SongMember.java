package jp.co.idolFunSite.domain.song;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.member.Member;
import jakarta.persistence.*;

@Entity
@Table(name = "song_member")
public class SongMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false, length = 30)
    private SongRoleType roleType = SongRoleType.ORIGINAL_VOCAL;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Song getSong() { return song; }
    public void setSong(Song song) { this.song = song; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public SongRoleType getRoleType() { return roleType; }
    public void setRoleType(SongRoleType roleType) { this.roleType = roleType; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
