package jp.co.idolFunSite.domain.call;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jp.co.idolFunSite.domain.song.Song;
import jakarta.persistence.*;

/**
 * 楽曲歌詞のブロック単位（Aメロ、Bメロ、サビなど）の構造を管理するエンティティ。
 */
@Entity
@Table(name = "song_call_block")
public class SongCallBlock extends BaseEntity {

    /** ブロックID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 対象となる楽曲 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    /** ブロックの種別（VERSE=平歌, CHORUS=サビ 等） */
    @Enumerated(EnumType.STRING)
    @Column(name = "block_type", nullable = false, length = 30)
    private BlockType blockType = BlockType.VERSE;

    /** 画面表示用のラベル名（例："1番 Aメロ"） */
    @Column(name = "block_label", length = 50)
    private String blockLabel;

    /** 楽曲内でのブロック表示順（1, 2, 3...） */
    @Column(name = "order_no", nullable = false)
    private Integer orderNo = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Song getSong() { return song; }
    public void setSong(Song song) { this.song = song; }
    public BlockType getBlockType() { return blockType; }
    public void setBlockType(BlockType blockType) { this.blockType = blockType; }
    public String getBlockLabel() { return blockLabel; }
    public void setBlockLabel(String blockLabel) { this.blockLabel = blockLabel; }
    public Integer getOrderNo() { return orderNo; }
    public void setOrderNo(Integer orderNo) { this.orderNo = orderNo; }
}
