package jp.co.idolFunSite.app.controller.song;

import java.time.LocalDate;
import jp.co.idolFunSite.domain.alias.AliasType;
import jp.co.idolFunSite.domain.alias.SongAlias;
import jp.co.idolFunSite.domain.alias.SongAliasRepository;
import jp.co.idolFunSite.domain.call.BlockType;
import jp.co.idolFunSite.domain.call.CallType;
import jp.co.idolFunSite.domain.call.SongCallBlock;
import jp.co.idolFunSite.domain.call.SongCallBlockRepository;
import jp.co.idolFunSite.domain.call.SongCallItem;
import jp.co.idolFunSite.domain.call.SongCallItemRepository;
import jp.co.idolFunSite.domain.call.SongCallLine;
import jp.co.idolFunSite.domain.call.SongCallLineRepository;
import jp.co.idolFunSite.domain.common.Status;
import jp.co.idolFunSite.domain.common.Visibility;
import jp.co.idolFunSite.domain.group.IdolGroup;
import jp.co.idolFunSite.domain.group.IdolGroupRepository;
import jp.co.idolFunSite.domain.master.CallTypeMaster;
import jp.co.idolFunSite.domain.master.CallTypeMasterRepository;
import jp.co.idolFunSite.domain.member.Member;
import jp.co.idolFunSite.domain.member.MemberRepository;
import jp.co.idolFunSite.domain.release.ReleaseSong;
import jp.co.idolFunSite.domain.release.ReleaseSongRepository;
import jp.co.idolFunSite.domain.release.ReleaseType;
import jp.co.idolFunSite.domain.release.SingleRelease;
import jp.co.idolFunSite.domain.release.SingleReleaseRepository;
import jp.co.idolFunSite.domain.site.Site;
import jp.co.idolFunSite.domain.site.SiteRepository;
import jp.co.idolFunSite.domain.song.Song;
import jp.co.idolFunSite.domain.song.SongMember;
import jp.co.idolFunSite.domain.song.SongMemberRepository;
import jp.co.idolFunSite.domain.song.SongRepository;
import jp.co.idolFunSite.domain.song.SongRoleType;
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
 * 楽曲APIの統合テストです。
 */
@SpringBootTest
@Transactional
class SongControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private IdolGroupRepository idolGroupRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongAliasRepository songAliasRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SongMemberRepository songMemberRepository;

    @Autowired
    private SingleReleaseRepository singleReleaseRepository;

    @Autowired
    private ReleaseSongRepository releaseSongRepository;

    @Autowired
    private SongCallBlockRepository songCallBlockRepository;

    @Autowired
    private SongCallLineRepository songCallLineRepository;

    @Autowired
    private SongCallItemRepository songCallItemRepository;

    @Autowired
    private CallTypeMasterRepository callTypeMasterRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getSongs_returnsListResponse() throws Exception {
        TestFixture fixture = createFixture();

        mockMvc.perform(get("/api/v1/sites/{siteKey}/songs", fixture.site.getSiteKey())
                        .param("keyword", "てすと別名"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].songId").value(fixture.song.getId()))
                .andExpect(jsonPath("$.content[0].title").value("テスト楽曲"))
                .andExpect(jsonPath("$.content[0].singleTitle").value("テストシングル"))
                .andExpect(jsonPath("$.content[0].isTitleTrack").value(true))
                .andExpect(jsonPath("$.content[0].originalMembers[0]").value("メンバーA"));
    }

    @Test
    void getSongDetail_returnsSongDetailResponse() throws Exception {
        TestFixture fixture = createFixture();

        mockMvc.perform(get("/api/v1/sites/{siteKey}/songs/{songId}", fixture.site.getSiteKey(), fixture.song.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songId").value(fixture.song.getId()))
                .andExpect(jsonPath("$.title").value("テスト楽曲"))
                .andExpect(jsonPath("$.description").value("詳細説明"))
                .andExpect(jsonPath("$.primaryRelease.title").value("テストシングル"))
                .andExpect(jsonPath("$.releases.length()").value(2))
                .andExpect(jsonPath("$.originalMembers.length()").value(2))
                .andExpect(jsonPath("$.originalMembers[0].name").value("メンバーA"));
    }

    @Test
    void getSongCalls_returnsSongCallResponse() throws Exception {
        TestFixture fixture = createFixture();

        mockMvc.perform(get("/api/v1/sites/{siteKey}/songs/{songId}/calls", fixture.site.getSiteKey(), fixture.song.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songId").value(fixture.song.getId()))
                .andExpect(jsonPath("$.blocks.length()").value(1))
                .andExpect(jsonPath("$.blocks[0].blockType").value("VERSE"))
                .andExpect(jsonPath("$.blocks[0].lines[0].lyrics").value("歌詞1行目"))
                .andExpect(jsonPath("$.blocks[0].lines[0].calls.length()").value(2))
                .andExpect(jsonPath("$.blocks[0].lines[0].calls[0].callTypeCode").value("CHANT"))
                .andExpect(jsonPath("$.blocks[0].lines[0].calls[0].callTypeLabel").value("掛け声"))
                .andExpect(jsonPath("$.blocks[0].lines[0].calls[1].callTypeCode").value("CLAP"))
                .andExpect(jsonPath("$.blocks[0].lines[0].calls[1].callTypeLabel").value("クラップ"));
    }

    private TestFixture createFixture() {
        Site site = new Site();
        site.setSiteKey("controller-test-site");
        site.setSiteName("Controller Test Site");
        site.setIdolName("Test Idol");
        site.setVisibility(Visibility.PUBLIC);
        site.setStatus(Status.PUBLISHED);
        site = siteRepository.save(site);

        IdolGroup group = new IdolGroup();
        group.setSite(site);
        group.setGroupName("Test Group");
        group.setOfficialName("Test Group");
        group.setDisplayOrder(1);
        group = idolGroupRepository.save(group);

        Member memberA = new Member();
        memberA.setSite(site);
        memberA.setMemberName("メンバーA");
        memberA.setMemberColorHex("#FFAAAA");
        memberA.setStatus("ACTIVE");
        memberA = memberRepository.save(memberA);

        Member memberB = new Member();
        memberB.setSite(site);
        memberB.setMemberName("メンバーB");
        memberB.setMemberColorHex("#AAAFFF");
        memberB.setStatus("ACTIVE");
        memberB = memberRepository.save(memberB);

        Song song = new Song();
        song.setSite(site);
        song.setGroup(group);
        song.setTitle("テスト楽曲");
        song.setTitleKana("てすとがっきょく");
        song.setDescription("詳細説明");
        song.setHasCallData(true);
        song.setDisplayOrder(1);
        song = songRepository.save(song);

        SongAlias songAlias = new SongAlias();
        songAlias.setSite(site);
        songAlias.setSong(song);
        songAlias.setAliasType(AliasType.SEARCH);
        songAlias.setAliasText("てすと別名");
        songAlias.setNormalizedText("てすと別名");
        songAlias.setDisplayOrder(1);
        songAliasRepository.save(songAlias);

        SingleRelease primaryRelease = new SingleRelease();
        primaryRelease.setSite(site);
        primaryRelease.setReleaseType(ReleaseType.SINGLE);
        primaryRelease.setTitle("テストシングル");
        primaryRelease.setReleaseDate(LocalDate.of(2024, 1, 1));
        primaryRelease.setDisplayOrder(1);
        primaryRelease = singleReleaseRepository.save(primaryRelease);

        SingleRelease subRelease = new SingleRelease();
        subRelease.setSite(site);
        subRelease.setReleaseType(ReleaseType.SINGLE);
        subRelease.setTitle("別収録");
        subRelease.setReleaseDate(LocalDate.of(2024, 2, 1));
        subRelease.setDisplayOrder(2);
        subRelease = singleReleaseRepository.save(subRelease);

        ReleaseSong releaseSong1 = new ReleaseSong();
        releaseSong1.setSite(site);
        releaseSong1.setSong(song);
        releaseSong1.setRelease(primaryRelease);
        releaseSong1.setTrackNumber(1);
        releaseSong1.setIsPrimary(true);
        releaseSong1.setIsTitleTrack(true);
        releaseSong1.setDisplayOrder(1);
        releaseSongRepository.save(releaseSong1);

        ReleaseSong releaseSong2 = new ReleaseSong();
        releaseSong2.setSite(site);
        releaseSong2.setSong(song);
        releaseSong2.setRelease(subRelease);
        releaseSong2.setTrackNumber(2);
        releaseSong2.setIsPrimary(false);
        releaseSong2.setIsTitleTrack(false);
        releaseSong2.setDisplayOrder(2);
        releaseSongRepository.save(releaseSong2);

        SongMember songMemberA = new SongMember();
        songMemberA.setSong(song);
        songMemberA.setMember(memberA);
        songMemberA.setRoleType(SongRoleType.ORIGINAL_VOCAL);
        songMemberA.setDisplayOrder(1);
        songMemberRepository.save(songMemberA);

        SongMember songMemberB = new SongMember();
        songMemberB.setSong(song);
        songMemberB.setMember(memberB);
        songMemberB.setRoleType(SongRoleType.ORIGINAL_VOCAL);
        songMemberB.setDisplayOrder(2);
        songMemberRepository.save(songMemberB);

        saveCallType(site, CallType.CHANT, "掛け声", "#F06292", "mic", 1);
        saveCallType(site, CallType.CLAP, "クラップ", "#FFCA28", "hands", 2);

        SongCallBlock block = new SongCallBlock();
        block.setSong(song);
        block.setBlockType(BlockType.VERSE);
        block.setBlockLabel("Aメロ");
        block.setOrderNo(1);
        block = songCallBlockRepository.save(block);

        SongCallLine line = new SongCallLine();
        line.setBlock(block);
        line.setLineNo(1);
        line.setLyrics("歌詞1行目");
        line = songCallLineRepository.save(line);

        SongCallItem chant = new SongCallItem();
        chant.setCallLine(line);
        chant.setCallTypeCode(CallType.CHANT.getCode());
        chant.setCallText("はい！");
        chant.setOrderNo(1);
        songCallItemRepository.save(chant);

        SongCallItem clap = new SongCallItem();
        clap.setCallLine(line);
        clap.setCallTypeCode(CallType.CLAP.getCode());
        clap.setCallText("クラップ");
        clap.setOrderNo(2);
        songCallItemRepository.save(clap);

        return new TestFixture(site, song);
    }

    private void saveCallType(Site site, CallType callType, String label, String colorHex, String iconKey, int displayOrder) {
        CallTypeMaster callTypeMaster = new CallTypeMaster();
        callTypeMaster.setSite(site);
        callTypeMaster.setCallTypeCode(callType.getCode());
        callTypeMaster.setCallTypeLabel(label);
        callTypeMaster.setColorHex(colorHex);
        callTypeMaster.setIconKey(iconKey);
        callTypeMaster.setDisplayOrder(displayOrder);
        callTypeMaster.setIsActive(true);
        callTypeMasterRepository.save(callTypeMaster);
    }

    private record TestFixture(Site site, Song song) {
    }
}
