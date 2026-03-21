package jp.co.idolFunSite.domain.song;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.group.IdolGroup;
import jp.co.idolFunSite.domain.single.SingleRelease;
import jp.co.idolFunSite.domain.site.Site;
import jakarta.persistence.*;

/**
 * 個別の楽曲情報を管理するエンティティ。
 */
@Entity
@Table(name = "song")
public class Song extends BaseEntity {

    /** 楽曲ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 対象サイト */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    /** 対象アイドルグループ */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private IdolGroup group;

    /** 収録されている主なリリース作品（シングルやアルバム） */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "single_release_id")
    private SingleRelease singleRelease;

    /** 楽曲名 */
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    /** 楽曲名の読み仮名（検索用） */
    @Column(name = "title_kana", length = 200)
    private String titleKana;

    /** 楽曲名のローマ字表記（任意・検索用） */
    @Column(name = "title_roman", length = 200)
    private String titleRoman;

    /** シングル内のトラック番号（例：1=表題曲） */
    @Column(name = "track_number")
    private Integer trackNumber;

    /** 表題曲であるかどうかを示すフラグ */
    @Column(name = "is_title_track", nullable = false)
    private Boolean isTitleTrack = false;

    /** フル歌詞のテキスト（著作権への配慮で実データを持たない運用も可能） */
    @Column(name = "lyrics_text", columnDefinition = "TEXT")
    private String lyricsText;

    /** 楽曲に関する補足説明など */
    @Column(name = "description", length = 1000)
    private String description;

    /** この楽曲に対してコールデータ（掛け声・クラップなど）が登録されているか */
    @Column(name = "has_call_data", nullable = false)
    private Boolean hasCallData = false;

    /** 一覧での優先表示順 */
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Site getSite() { return site; }
    public void setSite(Site site) { this.site = site; }
    public IdolGroup getGroup() { return group; }
    public void setGroup(IdolGroup group) { this.group = group; }
    public SingleRelease getSingleRelease() { return singleRelease; }
    public void setSingleRelease(SingleRelease singleRelease) { this.singleRelease = singleRelease; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getTitleKana() { return titleKana; }
    public void setTitleKana(String titleKana) { this.titleKana = titleKana; }
    public String getTitleRoman() { return titleRoman; }
    public void setTitleRoman(String titleRoman) { this.titleRoman = titleRoman; }
    public Integer getTrackNumber() { return trackNumber; }
    public void setTrackNumber(Integer trackNumber) { this.trackNumber = trackNumber; }
    public Boolean getIsTitleTrack() { return isTitleTrack; }
    public void setIsTitleTrack(Boolean isTitleTrack) { this.isTitleTrack = isTitleTrack; }
    public String getLyricsText() { return lyricsText; }
    public void setLyricsText(String lyricsText) { this.lyricsText = lyricsText; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getHasCallData() { return hasCallData; }
    public void setHasCallData(Boolean hasCallData) { this.hasCallData = hasCallData; }
    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
