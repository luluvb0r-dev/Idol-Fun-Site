package jp.co.idolFunSite.app.service.member;

import java.time.LocalDate;
import java.util.List;

import jp.co.idolFunSite.app.dto.member.MemberDetailResponse;
import jp.co.idolFunSite.app.dto.member.MemberListResponse;
import jp.co.idolFunSite.domain.common.Status;
import jp.co.idolFunSite.domain.common.Visibility;
import jp.co.idolFunSite.domain.member.Member;
import jp.co.idolFunSite.domain.member.MemberRepository;
import jp.co.idolFunSite.domain.site.Site;
import jp.co.idolFunSite.domain.site.SiteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * メンバーサービスの統合テストです。
 */
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void getMembers_returnsActiveMembersOrderedByDisplayOrder() {
        TestFixture fixture = createFixture();

        List<MemberListResponse> responses = memberService.getMembers(fixture.site().getSiteKey());

        assertEquals(2, responses.size());
        assertEquals(fixture.firstMember().getId(), responses.get(0).memberId());
        assertEquals("メンバーA", responses.get(0).name());
        assertEquals(LocalDate.of(2001, 1, 1), responses.get(0).birthday());
        assertEquals("Pink", responses.get(0).memberColor());
        assertEquals("紹介文A", responses.get(0).shortBio());
    }

    @Test
    void getMemberDetail_returnsMinimumProfileFields() {
        TestFixture fixture = createFixture();

        MemberDetailResponse response = memberService.getMemberDetail(
                fixture.site().getSiteKey(),
                fixture.secondMember().getId());

        assertEquals(fixture.secondMember().getId(), response.memberId());
        assertEquals("メンバーB", response.name());
        assertEquals(LocalDate.of(2002, 2, 2), response.birthday());
        assertEquals("Blue", response.memberColor());
        assertEquals("#0000FF", response.memberColorHex());
        assertEquals("紹介文B", response.shortBio());
    }

    private TestFixture createFixture() {
        Site site = new Site();
        site.setSiteKey("member-service-test-site");
        site.setSiteName("Member Service Test Site");
        site.setIdolName("Test Idol");
        site.setVisibility(Visibility.PUBLIC);
        site.setStatus(Status.PUBLISHED);
        site = siteRepository.save(site);

        Member firstMember = new Member();
        firstMember.setSite(site);
        firstMember.setMemberName("メンバーA");
        firstMember.setBirthday(LocalDate.of(2001, 1, 1));
        firstMember.setMemberColor("Pink");
        firstMember.setMemberColorHex("#FF66AA");
        firstMember.setShortBio("紹介文A");
        firstMember.setDisplayOrder(1);
        firstMember.setStatus("ACTIVE");
        firstMember = memberRepository.save(firstMember);

        Member secondMember = new Member();
        secondMember.setSite(site);
        secondMember.setMemberName("メンバーB");
        secondMember.setBirthday(LocalDate.of(2002, 2, 2));
        secondMember.setMemberColor("Blue");
        secondMember.setMemberColorHex("#0000FF");
        secondMember.setShortBio("紹介文B");
        secondMember.setDisplayOrder(2);
        secondMember.setStatus("ACTIVE");
        secondMember = memberRepository.save(secondMember);

        Member inactiveMember = new Member();
        inactiveMember.setSite(site);
        inactiveMember.setMemberName("非公開メンバー");
        inactiveMember.setDisplayOrder(3);
        inactiveMember.setStatus("INACTIVE");
        memberRepository.save(inactiveMember);

        return new TestFixture(site, firstMember, secondMember);
    }

    private record TestFixture(Site site, Member firstMember, Member secondMember) {
    }
}
