package jp.co.idolFunSite.app.service.member;

import java.util.List;

import jp.co.idolFunSite.app.dto.member.MemberDetailResponse;
import jp.co.idolFunSite.app.dto.member.MemberListResponse;
import jp.co.idolFunSite.domain.common.Status;
import jp.co.idolFunSite.domain.member.Member;
import jp.co.idolFunSite.domain.member.MemberRepository;
import jp.co.idolFunSite.domain.site.Site;
import jp.co.idolFunSite.domain.site.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * メンバー関連のビジネスロジックを提供するサービスです。
 */
@Service
@Transactional(readOnly = true)
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);

    private static final String ACTIVE_STATUS = "ACTIVE";

    private final SiteRepository siteRepository;
    private final MemberRepository memberRepository;

    public MemberService(SiteRepository siteRepository, MemberRepository memberRepository) {
        this.siteRepository = siteRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * メンバー一覧を取得します。
     *
     * @param siteKey サイト識別キー
     * @return メンバー一覧
     */
    public List<MemberListResponse> getMembers(String siteKey) {
        log.info("getMembers - start. siteKey: {}", siteKey);

        try {
            Site site = getPublishedSite(siteKey, "getMembers");
            List<MemberListResponse> responses = memberRepository
                    .findBySiteIdAndStatusOrderByDisplayOrderAscIdAsc(site.getId(), ACTIVE_STATUS)
                    .stream()
                    .map(this::toMemberListResponse)
                    .toList();

            if (responses.isEmpty()) {
                log.debug("getMembers - No active members found. siteId: {}", site.getId());
            }

            log.info("getMembers - end. status: success, memberCount: {}", responses.size());
            return responses;
        } catch (Exception e) {
            log.error("getMembers - Error occurred while getting members.", e);
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
    public MemberDetailResponse getMemberDetail(String siteKey, Long memberId) {
        log.info("getMemberDetail - start. siteKey: {}, memberId: {}", siteKey, memberId);

        try {
            Site site = getPublishedSite(siteKey, "getMemberDetail");
            Member member = memberRepository.findByIdAndSiteIdAndStatus(memberId, site.getId(), ACTIVE_STATUS)
                    .orElseThrow(() -> {
                        log.warn("getMemberDetail - Member was not found. siteId: {}, memberId: {}", site.getId(), memberId);
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found.");
                    });

            MemberDetailResponse response = toMemberDetailResponse(member);
            log.info("getMemberDetail - end. status: success, memberId: {}", response.memberId());
            return response;
        } catch (Exception e) {
            log.error("getMemberDetail - Error occurred while getting member detail.", e);
            throw e;
        }
    }

    private Site getPublishedSite(String siteKey, String logPrefix) {
        return siteRepository.findBySiteKeyAndStatus(siteKey, Status.PUBLISHED)
                .orElseThrow(() -> {
                    log.warn("{} - Published site was not found. siteKey: {}", logPrefix, siteKey);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Site not found.");
                });
    }

    private MemberListResponse toMemberListResponse(Member member) {
        return new MemberListResponse(
                member.getId(),
                member.getMemberName(),
                member.getBirthday(),
                member.getMemberColor(),
                member.getMemberColorHex(),
                member.getShortBio());
    }

    private MemberDetailResponse toMemberDetailResponse(Member member) {
        return new MemberDetailResponse(
                member.getId(),
                member.getMemberName(),
                member.getBirthday(),
                member.getMemberColor(),
                member.getMemberColorHex(),
                member.getShortBio());
    }
}
