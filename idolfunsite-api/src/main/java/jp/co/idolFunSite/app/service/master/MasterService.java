package jp.co.idolFunSite.app.service.master;

import java.util.List;

import jp.co.idolFunSite.app.dto.master.CallTypeMasterResponse;
import jp.co.idolFunSite.domain.common.Status;
import jp.co.idolFunSite.domain.master.CallTypeMasterRepository;
import jp.co.idolFunSite.domain.site.Site;
import jp.co.idolFunSite.domain.site.SiteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * マスタ参照サービスです。
 */
@Service
@Transactional(readOnly = true)
public class MasterService {

    private final SiteRepository siteRepository;
    private final CallTypeMasterRepository callTypeMasterRepository;

    public MasterService(SiteRepository siteRepository, CallTypeMasterRepository callTypeMasterRepository) {
        this.siteRepository = siteRepository;
        this.callTypeMasterRepository = callTypeMasterRepository;
    }

    /**
     * サイト配下の有効なコール種別マスタを取得します。
     *
     * @param siteKey サイト識別子
     * @return コール種別一覧
     */
    public List<CallTypeMasterResponse> getCallTypes(String siteKey) {
        Site site = siteRepository.findBySiteKeyAndStatus(siteKey, Status.PUBLISHED)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Site not found."));

        return callTypeMasterRepository.findBySiteIdAndIsActiveTrueOrderByDisplayOrderAsc(site.getId()).stream()
                .map(callType -> new CallTypeMasterResponse(
                        callType.getCallTypeCode(),
                        callType.getCallTypeLabel(),
                        callType.getColorHex(),
                        callType.getIconKey()))
                .toList();
    }
}
