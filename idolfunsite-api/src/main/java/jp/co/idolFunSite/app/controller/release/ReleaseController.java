package jp.co.idolFunSite.app.controller.release;

import jp.co.idolFunSite.app.dto.common.ItemListResponse;
import jp.co.idolFunSite.app.dto.release.ReleaseListResponse;
import jp.co.idolFunSite.app.service.release.ReleaseService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作品APIです。
 */
@RestController
@RequestMapping("/api/v1/sites/{siteKey}/releases")
public class ReleaseController {

    private final ReleaseService releaseService;

    public ReleaseController(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    /**
     * 作品一覧を取得します。
     *
     * @param siteKey サイト識別子
     * @param sort ソート条件
     * @return 作品一覧
     */
    @GetMapping
    public ItemListResponse<ReleaseListResponse> getReleases(
            @PathVariable("siteKey") String siteKey,
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "releaseDate", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "displayOrder", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            }) Sort sort) {
        return new ItemListResponse<>(releaseService.getReleases(siteKey, sort));
    }
}
