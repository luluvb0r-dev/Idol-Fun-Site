package jp.co.idolFunSite.app.controller.site;

import jp.co.idolFunSite.app.dto.site.SiteResponse;
import jp.co.idolFunSite.app.service.site.SiteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * サイト情報APIです。
 */
@RestController
@RequestMapping("/api/v1/sites")
public class SiteController {

    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    /**
     * サイト情報を取得します。
     *
     * @param siteKey サイト識別子
     * @return サイト情報
     */
    @GetMapping("/{siteKey}")
    public SiteResponse getSite(@PathVariable("siteKey") String siteKey) {
        return siteService.getSite(siteKey);
    }
}
