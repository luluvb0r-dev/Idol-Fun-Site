package jp.co.idolFunSite.app.service.member;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jp.co.idolFunSite.app.dto.member.MemberDetailResponse;
import jp.co.idolFunSite.app.dto.member.MemberListResponse;
import jp.co.idolFunSite.domain.common.Status;
import jp.co.idolFunSite.domain.member.Member;
import jp.co.idolFunSite.domain.member.MemberGroupHistory;
import jp.co.idolFunSite.domain.member.MemberGroupHistoryRepository;
import jp.co.idolFunSite.domain.member.MemberRepository;
import jp.co.idolFunSite.domain.site.Site;
import jp.co.idolFunSite.domain.site.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
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

    private final SiteRepository siteRepository;
    private final MemberRepository memberRepository;
    private final MemberGroupHistoryRepository memberGroupHistoryRepository;

    public MemberService(
            SiteRepository siteRepository,
            MemberRepository memberRepository,
            MemberGroupHistoryRepository memberGroupHistoryRepository) {
        this.siteRepository = siteRepository;
        this.memberRepository = memberRepository;
        this.memberGroupHistoryRepository = memberGroupHistoryRepository;
    }

    /**
     * メンバー一覧を取得します。
     *
     * @param siteKey サイト識別キー
     * @param status メンバーステータス
     * @param sort ソート条件
     * @return メンバー一覧
     */
    public List<MemberListResponse> getMembers(String siteKey, String status, Sort sort) {
        log.info("getMembers - start. siteKey: {}, status: {}, sort: {}", siteKey, status, sort);

        try {
            Site site = getPublishedSite(siteKey, "getMembers");
            List<Member> members = memberRepository.findBySiteIdAndStatus(site.getId(), status, sort);
            List<Long> memberIds = members.stream().map(Member::getId).toList();

            Map<Long, List<MemberGroupHistory>> historiesByMemberId = memberIds.isEmpty()
                    ? Map.of()
                    : memberGroupHistoryRepository.findBySiteIdAndMemberIdIn(site.getId(), memberIds).stream()
                            .collect(Collectors.groupingBy(history -> history.getMember().getId()));

            List<MemberListResponse> responses = members.stream()
                    .map(member -> toMemberListResponse(member, historiesByMemberId.getOrDefault(member.getId(), List.of())))
                    .toList();

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
            Member member = memberRepository.findByIdAndSiteId(memberId, site.getId())
                    .orElseThrow(() -> {
                        log.warn("getMemberDetail - Member was not found. siteId: {}, memberId: {}", site.getId(), memberId);
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found.");
                    });

            List<MemberGroupHistory> histories = memberGroupHistoryRepository
                    .findBySiteIdAndMemberIdOrderByJoinedOnAscDisplayOrderAscIdAsc(site.getId(), memberId);

            MemberDetailResponse response = toMemberDetailResponse(member, histories);
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

    private MemberListResponse toMemberListResponse(Member member, List<MemberGroupHistory> histories) {
        MemberGroupHistory currentHistory = selectCurrentGroups(histories).stream().findFirst().orElse(null);
        return new MemberListResponse(
                member.getId(),
                member.getMemberName(),
                member.getBirthday(),
                member.getMemberColor(),
                member.getMemberColorHex(),
                currentHistory != null
                        ? new MemberListResponse.CurrentGroupResponse(
                                currentHistory.getGroup().getId(),
                                currentHistory.getGroup().getGroupName())
                        : null,
                member.getProfileImageUrl());
    }

    private MemberDetailResponse toMemberDetailResponse(Member member, List<MemberGroupHistory> histories) {
        List<MemberGroupHistory> currentGroups = selectCurrentGroups(histories);
        return new MemberDetailResponse(
                member.getId(),
                member.getMemberName(),
                member.getBirthday(),
                member.getMemberColor(),
                member.getMemberColorHex(),
                member.getBirthplace(),
                member.getShortBio(),
                currentGroups.stream()
                        .map(history -> new MemberDetailResponse.CurrentGroupResponse(
                                history.getGroup().getId(),
                                history.getGroup().getGroupName(),
                                history.getGenerationLabel(),
                                history.getJoinedOn()))
                        .toList(),
                histories.stream()
                        .sorted(Comparator.comparing(MemberGroupHistory::getJoinedOn, Comparator.nullsLast(Comparator.naturalOrder()))
                                .thenComparing(MemberGroupHistory::getDisplayOrder)
                                .thenComparing(MemberGroupHistory::getId))
                        .map(history -> new MemberDetailResponse.GroupHistoryResponse(
                                history.getGroup().getId(),
                                history.getGroup().getGroupName(),
                                history.getGenerationLabel(),
                                history.getJoinedOn(),
                                history.getLeftOn(),
                                history.getIsPrimary()))
                        .toList(),
                member.getProfileImageUrl());
    }

    private List<MemberGroupHistory> selectCurrentGroups(List<MemberGroupHistory> histories) {
        LocalDate today = LocalDate.now();

        return histories.stream()
                .filter(history -> history.getJoinedOn() == null || !history.getJoinedOn().isAfter(today))
                .filter(history -> history.getLeftOn() == null || !history.getLeftOn().isBefore(today))
                .sorted(Comparator
                        .comparing((MemberGroupHistory history) -> !Boolean.TRUE.equals(history.getIsPrimary()))
                        .thenComparing(MemberGroupHistory::getDisplayOrder)
                        .thenComparing(MemberGroupHistory::getId))
                .toList();
    }
}
