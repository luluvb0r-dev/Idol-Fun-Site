package jp.co.idolFunSite.domain.release;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.site.Site;
import jp.co.idolFunSite.domain.song.Song;
import jakarta.persistence.*;

@Entity
@Table(name = "release_song")
public class ReleaseSong extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "release_id", nullable = false)
    private SingleRelease release;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @Column(name = "track_number")
    private Integer trackNumber;

    @Column(name = "disc_number")
    private Integer discNumber;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @Column(name = "is_title_track", nullable = false)
    private Boolean isTitleTrack = false;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Site getSite() { return site; }
    public void setSite(Site site) { this.site = site; }
    public SingleRelease getRelease() { return release; }
    public void setRelease(SingleRelease release) { this.release = release; }
    public Song getSong() { return song; }
    public void setSong(Song song) { this.song = song; }
    public Integer getTrackNumber() { return trackNumber; }
    public void setTrackNumber(Integer trackNumber) { this.trackNumber = trackNumber; }
    public Integer getDiscNumber() { return discNumber; }
    public void setDiscNumber(Integer discNumber) { this.discNumber = discNumber; }
    public Boolean getIsPrimary() { return isPrimary; }
    public void setIsPrimary(Boolean isPrimary) { this.isPrimary = isPrimary; }
    public Boolean getIsTitleTrack() { return isTitleTrack; }
    public void setIsTitleTrack(Boolean isTitleTrack) { this.isTitleTrack = isTitleTrack; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
