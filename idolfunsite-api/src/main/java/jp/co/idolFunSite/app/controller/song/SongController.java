package jp.co.idolFunSite.app.controller.song;

import jp.co.idolFunSite.app.dto.song.SongCallResponse;
import jp.co.idolFunSite.app.dto.song.SongDetailResponse;
import jp.co.idolFunSite.app.dto.song.SongListResponse;
import jp.co.idolFunSite.app.dto.song.SongSearchCondition;
import jp.co.idolFunSite.app.service.song.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 楽曲に関するエンドポイントを提供するコントローラーです。
 */
@RestController
@RequestMapping("/api/v1/sites/{siteKey}/songs")
public class SongController {

    private static final Logger log = LoggerFactory.getLogger(SongController.class);

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    /**
     * 楽曲一覧を取得します。
     *
     * @param siteKey      サイト識別子
     * @param keyword      検索キーワード（任意）
     * @param releaseId    シングル絞り込み条件（任意）
     * @param isTitleTrack 表題曲絞り込み条件（任意）
     * @param memberId     メンバー絞り込み条件（任意）
     * @param pageable     ページング情報
     * @return 楽曲一覧レスポンス
     */
    @GetMapping
    public Page<SongListResponse> getSongs(
            @PathVariable("siteKey") String siteKey,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "releaseId", required = false) Long releaseId,
            @RequestParam(name = "isTitleTrack", required = false) Boolean isTitleTrack,
            @RequestParam(name = "memberId", required = false) Long memberId,
            @PageableDefault(size = 20)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "displayOrder"),
                    @SortDefault(sort = "id")
            }) Pageable pageable) {

        log.info(
                "getSongs - start. siteKey: {}, keyword: {}, releaseId: {}, isTitleTrack: {}, memberId: {}, pageable: {}",
                siteKey,
                keyword,
                releaseId,
                isTitleTrack,
                memberId,
                pageable);
        try {
            SongSearchCondition condition = new SongSearchCondition(siteKey, keyword, releaseId, isTitleTrack, memberId);
            Page<SongListResponse> response = songService.searchSongs(condition, pageable);
            log.info("getSongs - end. status: success, element count: {}", response.getNumberOfElements());
            return response;
        } catch (Exception e) {
            log.error("getSongs - Error occurred while processing request.", e);
            throw e;
        }
    }

    /**
     * 楽曲詳細を取得します。
     *
     * @param siteKey サイト識別子
     * @param songId  楽曲ID
     * @return 楽曲詳細レスポンス
     */
    @GetMapping("/{songId}")
    public SongDetailResponse getSongDetail(
            @PathVariable("siteKey") String siteKey,
            @PathVariable("songId") Long songId) {

        log.info("getSongDetail - start. siteKey: {}, songId: {}", siteKey, songId);
        try {
            SongDetailResponse response = songService.getSongDetail(siteKey, songId);
            log.info("getSongDetail - end. status: success, songId: {}", response.songId());
            return response;
        } catch (Exception e) {
            log.error("getSongDetail - Error occurred while processing request.", e);
            throw e;
        }
    }

    /**
     * 楽曲コール情報を取得します。
     *
     * @param siteKey サイト識別子
     * @param songId  楽曲ID
     * @return 楽曲コールレスポンス
     */
    @GetMapping("/{songId}/calls")
    public SongCallResponse getSongCalls(
            @PathVariable("siteKey") String siteKey,
            @PathVariable("songId") Long songId) {

        log.info("getSongCalls - start. siteKey: {}, songId: {}", siteKey, songId);
        try {
            SongCallResponse response = songService.getSongCalls(siteKey, songId);
            log.info("getSongCalls - end. status: success, songId: {}", response.songId());
            return response;
        } catch (Exception e) {
            log.error("getSongCalls - Error occurred while processing request.", e);
            throw e;
        }
    }
}
