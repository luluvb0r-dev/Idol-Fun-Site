package jp.co.idolFunSite.app.service.release;

import java.util.List;

import jp.co.idolFunSite.app.dto.release.ReleaseListResponse;
import jp.co.idolFunSite.domain.common.Status;
import jp.co.idolFunSite.domain.release.SingleReleaseRepository;
import jp.co.idolFunSite.domain.site.Site;
import jp.co.idolFunSite.domain.site.SiteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * 作品一覧の参照サービスです。
 */
@Service
@Transactional(readOnly = true)
public class ReleaseService {

    private final SiteRepository siteRepository;
    private final SingleReleaseRepository singleReleaseRepository;

    public ReleaseService(SiteRepository siteRepository, SingleReleaseRepository singleReleaseRepository) {
        this.siteRepository = siteRepository;
        this.singleReleaseRepository = singleReleaseRepository;
    }

    /**
     * サイト配下の作品一覧を取得します。
     *
     * @param siteKey サイト識別子
     * @param sort ソート条件
     * @return 作品一覧
     */
    public List<ReleaseListResponse> getReleases(String siteKey, Sort sort) {
        Site site = siteRepository.findBySiteKeyAndStatus(siteKey, Status.PUBLISHED)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Site not found."));

        return singleReleaseRepository.findBySiteId(site.getId(), sort).stream()
                .map(release -> new ReleaseListResponse(
                        release.getId(),
                        release.getTitle(),
                        release.getReleaseDate(),
                        release.getReleaseType().name(),
                        release.getJacketImageUrl()))
                .toList();
    }
}
