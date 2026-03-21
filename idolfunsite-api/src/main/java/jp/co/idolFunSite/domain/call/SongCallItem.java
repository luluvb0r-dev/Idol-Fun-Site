package jp.co.idolFunSite.domain.call;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jakarta.persistence.*;

/**
 * 歌詞の特定行に関連づく、掛け声やクラップなどの「コール要素」を管理するエンティティ。
 */
@Entity
@Table(name = "song_call_item")
public class SongCallItem extends BaseEntity {

    /** コール要素ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 対象となる歌詞行 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "call_line_id", nullable = false)
    private SongCallLine callLine;

    /** コールの種別（文字列、手拍子、ジャンプなど） */
    @Enumerated(EnumType.STRING)
    @Column(name = "call_type", nullable = false, length = 30)
    private CallType callType = CallType.CHANT;

    /** 実際のコール内容テキスト（例：「おー！」や「超絶かわいい〇〇！」等） */
    @Column(name = "call_text", nullable = false, length = 500)
    private String callText;

    /** コール文字列を表示する際の色（例：メンバーカラーのHEXコード） */
    @Column(name = "style_color_hex", length = 20)
    private String styleColorHex;

    /** 同一行内に複数コールが存在する場合の横・縦への配置順 */
    @Column(name = "order_no", nullable = false)
    private Integer orderNo = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public SongCallLine getCallLine() { return callLine; }
    public void setCallLine(SongCallLine callLine) { this.callLine = callLine; }
    public CallType getCallType() { return callType; }
    public void setCallType(CallType callType) { this.callType = callType; }
    public String getCallText() { return callText; }
    public void setCallText(String callText) { this.callText = callText; }
    public String getStyleColorHex() { return styleColorHex; }
    public void setStyleColorHex(String styleColorHex) { this.styleColorHex = styleColorHex; }
    public Integer getOrderNo() { return orderNo; }
    public void setOrderNo(Integer orderNo) { this.orderNo = orderNo; }
}
