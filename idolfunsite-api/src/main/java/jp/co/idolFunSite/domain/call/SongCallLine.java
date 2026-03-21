package jp.co.idolFunSite.domain.call;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jakarta.persistence.*;

/**
 * ブロック内の1行ずつの歌詞テキストを管理するエンティティ。
 * コール要素は、この「行」に対して紐づけられます。
 */
@Entity
@Table(name = "song_call_line")
public class SongCallLine extends BaseEntity {

    /** 行ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 所属するブロック */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id", nullable = false)
    private SongCallBlock block;

    /** ブロック内での行番号（表示順） */
    @Column(name = "line_no", nullable = false)
    private Integer lineNo = 0;

    /** この行の歌詞テキスト */
    @Column(name = "lyrics", nullable = false, columnDefinition = "TEXT")
    private String lyrics;

    /** 行に対する補足説明や備考（あれば） */
    @Column(name = "note", length = 500)
    private String note;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public SongCallBlock getBlock() { return block; }
    public void setBlock(SongCallBlock block) { this.block = block; }
    public Integer getLineNo() { return lineNo; }
    public void setLineNo(Integer lineNo) { this.lineNo = lineNo; }
    public String getLyrics() { return lyrics; }
    public void setLyrics(String lyrics) { this.lyrics = lyrics; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
