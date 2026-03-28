package jp.co.idolFunSite.app.service.site;

import jp.co.idolFunSite.app.dto.site.SiteResponse;
import jp.co.idolFunSite.domain.common.Status;
import jp.co.idolFunSite.domain.site.Site;
import jp.co.idolFunSite.domain.site.SiteRepository;
import jp.co.idolFunSite.domain.site.SiteThemeSetting;
import jp.co.idolFunSite.domain.site.SiteThemeSettingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * サイト情報の参照サービスです。
 */
@Service
@Transactional(readOnly = true)
public class SiteService {

    private final SiteRepository siteRepository;
    private final SiteThemeSettingRepository siteThemeSettingRepository;

    public SiteService(SiteRepository siteRepository, SiteThemeSettingRepository siteThemeSettingRepository) {
        this.siteRepository = siteRepository;
        this.siteThemeSettingRepository = siteThemeSettingRepository;
    }

    /**
     * 公開中サイト情報を取得します。
     *
     * @param siteKey サイト識別子
     * @return サイト情報
     */
    public SiteResponse getSite(String siteKey) {
        Site site = siteRepository.findBySiteKeyAndStatus(siteKey, Status.PUBLISHED)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Site not found."));

        SiteThemeSetting themeSetting = siteThemeSettingRepository.findBySiteId(site.getId()).orElse(null);

        return new SiteResponse(
                site.getSiteKey(),
                site.getSiteName(),
                site.getIdolName(),
                new SiteResponse.ThemeResponse(
                        themeSetting != null ? themeSetting.getPrimaryColorHex() : null,
                        themeSetting != null ? themeSetting.getSecondaryColorHex() : null,
                        themeSetting != null ? themeSetting.getAccentColorHex() : null));
    }
}
