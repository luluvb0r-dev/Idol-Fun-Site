package jp.co.idolFunSite.app.controller.member;

import java.util.List;

import jp.co.idolFunSite.app.dto.member.MemberDetailResponse;
import jp.co.idolFunSite.app.dto.member.MemberListResponse;
import jp.co.idolFunSite.app.service.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * メンバーAPIを提供するコントローラーです。
 */
@RestController
@RequestMapping("/api/v1/sites/{siteKey}/members")
public class MemberController {

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * メンバー一覧を取得します。
     *
     * @param siteKey サイト識別キー
     * @return メンバー一覧
     */
    @GetMapping
    public List<MemberListResponse> getMembers(@PathVariable("siteKey") String siteKey) {
        log.info("getMembers - start. siteKey: {}", siteKey);
        try {
            List<MemberListResponse> response = memberService.getMembers(siteKey);
            log.info("getMembers - end. status: success, memberCount: {}", response.size());
            return response;
        } catch (Exception e) {
            log.error("getMembers - Error occurred while processing request.", e);
            throw e;
        }
    }

    /**
     * メンバー詳細を取得します。
     *
     * @param siteKey サイト識別キー
     * @param memberId メンバーID
     * @return メンバー詳細
     */
    @GetMapping("/{memberId}")
    public MemberDetailResponse getMemberDetail(
            @PathVariable("siteKey") String siteKey,
            @PathVariable("memberId") Long memberId) {

        log.info("getMemberDetail - start. siteKey: {}, memberId: {}", siteKey, memberId);
        try {
            MemberDetailResponse response = memberService.getMemberDetail(siteKey, memberId);
            log.info("getMemberDetail - end. status: success, memberId: {}", response.memberId());
            return response;
        } catch (Exception e) {
            log.error("getMemberDetail - Error occurred while processing request.", e);
            throw e;
        }
    }
}
