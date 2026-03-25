package jp.co.idolFunSite.app.dto.song;

import java.util.List;

/**
 * 楽曲コール表示向けのレスポンスDTOです。
 */
public record SongCallResponse(
        Long songId,
        String title,
        List<BlockResponse> blocks) {

    /**
     * 歌詞ブロック情報です。
     */
    public record BlockResponse(
            Long blockId,
            String blockType,
            String blockLabel,
            Integer orderNo,
            List<LineResponse> lines) {
    }

    /**
     * 歌詞1行分の情報です。
     */
    public record LineResponse(
            Long lineId,
            Integer lineNo,
            String lyrics,
            List<CallItemResponse> calls) {
    }

    /**
     * 行の下に表示するコール情報です。
     */
    public record CallItemResponse(
            Long callId,
            String callTypeCode,
            String callTypeLabel,
            String callText,
            StyleResponse style) {
    }

    /**
     * コール表示用のスタイル情報です。
     */
    public record StyleResponse(
            String colorHex,
            String iconKey) {
    }
}
