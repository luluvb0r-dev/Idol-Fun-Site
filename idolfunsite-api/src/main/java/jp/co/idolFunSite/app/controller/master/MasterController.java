package jp.co.idolFunSite.app.controller.master;

import jp.co.idolFunSite.app.dto.common.ItemListResponse;
import jp.co.idolFunSite.app.dto.master.CallTypeMasterResponse;
import jp.co.idolFunSite.app.service.master.MasterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * マスタAPIです。
 */
@RestController
@RequestMapping("/api/v1/sites/{siteKey}/masters")
public class MasterController {

    private final MasterService masterService;

    public MasterController(MasterService masterService) {
        this.masterService = masterService;
    }

    /**
     * コール種別マスタを取得します。
     *
     * @param siteKey サイト識別子
     * @return コール種別一覧
     */
    @GetMapping("/call-types")
    public ItemListResponse<CallTypeMasterResponse> getCallTypes(@PathVariable("siteKey") String siteKey) {
        return new ItemListResponse<>(masterService.getCallTypes(siteKey));
    }
}
