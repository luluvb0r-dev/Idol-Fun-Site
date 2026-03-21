package jp.co.idolFunSite.domain.song;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.member.Member;
import jakarta.persistence.*;

/**
 * 楽曲と歌唱メンバーの関連情報（多対多）を管理する中間エンティティ。
 */
@Entity
@Table(name = "song_member")
public class SongMember extends BaseEntity {

    /** 関連ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 対象楽曲 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    /** 参加メンバー */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /** メンバーの役割（オリジナル歌唱メンバー、センター等） */
    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false, length = 30)
    private RoleType roleType = RoleType.ORIGINAL_VOCAL;

    /** 楽曲詳細等でのメンバー表示順序 */
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Song getSong() { return song; }
    public void setSong(Song song) { this.song = song; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public RoleType getRoleType() { return roleType; }
    public void setRoleType(RoleType roleType) { this.roleType = roleType; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
