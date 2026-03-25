package jp.co.idolFunSite.app.controller.song;

import jp.co.idolFunSite.app.dto.song.SongListResponse;
import jp.co.idolFunSite.app.dto.song.SongSearchCondition;
import jp.co.idolFunSite.app.service.song.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 楽曲に関するエンドポイントを提供するコントローラーです。
 */
@RestController
@RequestMapping("/api/sites/{siteId}/songs")
public class SongController {

    private static final Logger log = LoggerFactory.getLogger(SongController.class);

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    /**
     * 楽曲一覧を取得します。
     *
     * @param siteId   サイトID
     * @param keyword  検索キーワード（任意）
     * @param pageable ページング情報
     * @return 楽曲一覧レスポンス
     */
    @GetMapping
    public Page<SongListResponse> getSongs(
            @PathVariable("siteId") Long siteId,
            @RequestParam(name = "keyword", required = false) String keyword,
            @PageableDefault(size = 20) Pageable pageable) {

        log.info("getSongs - start. siteId: {}, keyword: {}, pageable: {}", siteId, keyword, pageable);
        try {
            SongSearchCondition condition = new SongSearchCondition(siteId, keyword);
            Page<SongListResponse> response = songService.searchSongs(condition, pageable);
            log.info("getSongs - end. status: success, element count: {}", response.getNumberOfElements());
            return response;
        } catch (Exception e) {
            log.error("getSongs - Error occurred while processing request.", e);
            throw e;
        }
    }
}
