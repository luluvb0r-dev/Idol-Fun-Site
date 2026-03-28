package jp.co.idolFunSite.app.controller.member;

import java.time.LocalDate;

import jp.co.idolFunSite.domain.common.Status;
import jp.co.idolFunSite.domain.common.Visibility;
import jp.co.idolFunSite.domain.group.IdolGroup;
import jp.co.idolFunSite.domain.group.IdolGroupRepository;
import jp.co.idolFunSite.domain.member.Member;
import jp.co.idolFunSite.domain.member.MemberGroupHistory;
import jp.co.idolFunSite.domain.member.MemberGroupHistoryRepository;
import jp.co.idolFunSite.domain.member.MemberRepository;
import jp.co.idolFunSite.domain.site.Site;
import jp.co.idolFunSite.domain.site.SiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * メンバーコントローラーの統合テストです。
 */
@SpringBootTest
@Transactional
class MemberControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private IdolGroupRepository idolGroupRepository;

    @Autowired
    private MemberGroupHistoryRepository memberGroupHistoryRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getMembers_returnsMinimumProfileList() throws Exception {
        TestFixture fixture = createFixture();

        mockMvc.perform(get("/api/v1/sites/{siteKey}/members", fixture.site().getSiteKey()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.items[0].memberId").value(fixture.firstMember().getId()))
                .andExpect(jsonPath("$.data.items[0].name").value("メンバーA"))
                .andExpect(jsonPath("$.data.items[0].birthday").value("2001-01-01"))
                .andExpect(jsonPath("$.data.items[0].memberColor").value("Pink"))
                .andExpect(jsonPath("$.data.items[0].currentGroup.groupName").value("=LOVE"))
                .andExpect(jsonPath("$.data.items.length()").value(2))
                .andExpect(jsonPath("$.meta.version").value("v1"));
    }

    @Test
    void getMemberDetail_returnsMinimumProfileDetail() throws Exception {
        TestFixture fixture = createFixture();

        mockMvc.perform(get(
                        "/api/v1/sites/{siteKey}/members/{memberId}",
                        fixture.site().getSiteKey(),
                        fixture.secondMember().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(fixture.secondMember().getId()))
                .andExpect(jsonPath("$.data.name").value("メンバーB"))
                .andExpect(jsonPath("$.data.birthday").value("2002-02-02"))
                .andExpect(jsonPath("$.data.memberColor").value("Blue"))
                .andExpect(jsonPath("$.data.memberColorHex").value("#0000FF"))
                .andExpect(jsonPath("$.data.birthplace").value("大阪府"))
                .andExpect(jsonPath("$.data.shortBio").value("紹介文B"))
                .andExpect(jsonPath("$.data.currentGroups[0].groupName").value("=LOVE"))
                .andExpect(jsonPath("$.data.groupHistory[0].generationLabel").value("1期"))
                .andExpect(jsonPath("$.meta.version").value("v1"));
    }

    private TestFixture createFixture() {
        Site site = new Site();
        site.setSiteKey("member-controller-test-site");
        site.setSiteName("Member Controller Test Site");
        site.setIdolName("Test Idol");
        site.setVisibility(Visibility.PUBLIC);
        site.setStatus(Status.PUBLISHED);
        site = siteRepository.save(site);

        IdolGroup group = new IdolGroup();
        group.setSite(site);
        group.setGroupName("=LOVE");
        group.setDisplayOrder(1);
        group = idolGroupRepository.save(group);

        Member firstMember = new Member();
        firstMember.setSite(site);
        firstMember.setMemberName("メンバーA");
        firstMember.setBirthday(LocalDate.of(2001, 1, 1));
        firstMember.setMemberColor("Pink");
        firstMember.setMemberColorHex("#FF66AA");
        firstMember.setBirthplace("東京都");
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
        secondMember.setBirthplace("大阪府");
        secondMember.setShortBio("紹介文B");
        secondMember.setDisplayOrder(2);
        secondMember.setStatus("ACTIVE");
        secondMember = memberRepository.save(secondMember);

        Member inactiveMember = new Member();
        inactiveMember.setSite(site);
        inactiveMember.setMemberName("非表示メンバー");
        inactiveMember.setDisplayOrder(3);
        inactiveMember.setStatus("INACTIVE");
        memberRepository.save(inactiveMember);

        memberGroupHistoryRepository.save(createHistory(site, group, firstMember, 1));
        memberGroupHistoryRepository.save(createHistory(site, group, secondMember, 2));

        return new TestFixture(site, firstMember, secondMember);
    }

    private MemberGroupHistory createHistory(Site site, IdolGroup group, Member member, int displayOrder) {
        MemberGroupHistory history = new MemberGroupHistory();
        history.setSite(site);
        history.setGroup(group);
        history.setMember(member);
        history.setGenerationLabel("1期");
        history.setJoinedOn(LocalDate.of(2017, 9, 6));
        history.setIsPrimary(true);
        history.setDisplayOrder(displayOrder);
        return history;
    }

    private record TestFixture(Site site, Member firstMember, Member secondMember) {
    }
}
