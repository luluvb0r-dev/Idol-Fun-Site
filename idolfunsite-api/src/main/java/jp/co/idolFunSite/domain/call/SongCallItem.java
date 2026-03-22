package jp.co.idolFunSite.domain.call;

import jp.co.idolFunSite.domain.common.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "song_call_item")
public class SongCallItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "call_line_id", nullable = false)
    private SongCallLine callLine;

    @Column(name = "call_type_code", nullable = false, length = 30)
    private String callTypeCode;

    @Column(name = "call_text", nullable = false, length = 500)
    private String callText;

    @Column(name = "style_color_hex", length = 20)
    private String styleColorHex;

    @Column(name = "order_no", nullable = false)
    private Integer orderNo = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public SongCallLine getCallLine() { return callLine; }
    public void setCallLine(SongCallLine callLine) { this.callLine = callLine; }
    public String getCallTypeCode() { return callTypeCode; }
    public void setCallTypeCode(String callTypeCode) { this.callTypeCode = callTypeCode; }
    public String getCallText() { return callText; }
    public void setCallText(String callText) { this.callText = callText; }
    public String getStyleColorHex() { return styleColorHex; }
    public void setStyleColorHex(String styleColorHex) { this.styleColorHex = styleColorHex; }
    public Integer getOrderNo() { return orderNo; }
    public void setOrderNo(Integer orderNo) { this.orderNo = orderNo; }
}
