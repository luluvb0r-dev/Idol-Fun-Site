package jp.co.idolFunSite.app.service.song;

import java.time.LocalDate;
import jp.co.idolFunSite.app.dto.song.SongCallResponse;
import jp.co.idolFunSite.app.dto.song.SongDetailResponse;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 楽曲詳細・コール取得の結合テストです。
 */
@SpringBootTest
@Transactional
class SongServiceIntegrationTest {

    @Autowired
    private SongService songService;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private IdolGroupRepository idolGroupRepository;

    @Autowired
    private SongRepository songRepository;

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

    @Test
    void getSongDetail_returnsPrimaryReleaseAndOriginalMembers() {
        TestFixture fixture = createFixture();

        SongDetailResponse response = songService.getSongDetail(fixture.site.getSiteKey(), fixture.song.getId());

        assertEquals(fixture.song.getId(), response.songId());
        assertEquals("テスト楽曲", response.title());
        assertNotNull(response.primaryRelease());
        assertEquals("テストシングル", response.primaryRelease().title());
        assertEquals(2, response.releases().size());
        assertEquals(2, response.originalMembers().size());
        assertEquals("メンバーA", response.originalMembers().get(0).name());
        assertTrue(response.hasCallData());
    }

    @Test
    void getSongCalls_returnsLyricsAndCallsGroupedByLine() {
        TestFixture fixture = createFixture();

        SongCallResponse response = songService.getSongCalls(fixture.site.getSiteKey(), fixture.song.getId());

        assertEquals(fixture.song.getId(), response.songId());
        assertEquals(1, response.blocks().size());
        assertEquals(2, response.blocks().get(0).lines().size());
        assertEquals("歌詞1行目", response.blocks().get(0).lines().get(0).lyrics());
        assertEquals(2, response.blocks().get(0).lines().get(0).calls().size());
        assertEquals("CHANT", response.blocks().get(0).lines().get(0).calls().get(0).callTypeCode());
        assertEquals("掛け声", response.blocks().get(0).lines().get(0).calls().get(0).callTypeLabel());
        assertEquals("CLAP", response.blocks().get(0).lines().get(0).calls().get(1).callTypeCode());
        assertEquals("クラップ", response.blocks().get(0).lines().get(0).calls().get(1).callTypeLabel());
        assertNotNull(response.blocks().get(0).lines().get(0).calls().get(0).style());
        assertFalse(response.blocks().get(0).lines().get(1).calls().isEmpty());
    }

    private TestFixture createFixture() {
        Site site = new Site();
        site.setSiteKey("test-site");
        site.setSiteName("Test Site");
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
        memberA = saveMember(memberA);

        Member memberB = new Member();
        memberB.setSite(site);
        memberB.setMemberName("メンバーB");
        memberB.setMemberColorHex("#AAAFFF");
        memberB.setStatus("ACTIVE");
        memberB = saveMember(memberB);

        Song song = new Song();
        song.setSite(site);
        song.setGroup(group);
        song.setTitle("テスト楽曲");
        song.setTitleKana("てすとがっきょく");
        song.setDescription("詳細説明");
        song.setHasCallData(true);
        song.setDisplayOrder(1);
        song = songRepository.save(song);

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
        subRelease.setTitle("カップリング収録");
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
        releaseSong2.setTrackNumber(3);
        releaseSong2.setIsPrimary(false);
        releaseSong2.setIsTitleTrack(false);
        releaseSong2.setDisplayOrder(2);
        releaseSongRepository.save(releaseSong2);

        saveSongMember(song, memberA, 1);
        saveSongMember(song, memberB, 2);

        saveCallType(site, CallType.CHANT, "掛け声", "#F06292", "mic", 1);
        saveCallType(site, CallType.CLAP, "クラップ", "#FFCA28", "hands", 2);

        SongCallBlock block = new SongCallBlock();
        block.setSong(song);
        block.setBlockType(BlockType.VERSE);
        block.setBlockLabel("Aメロ");
        block.setOrderNo(1);
        block = songCallBlockRepository.save(block);

        SongCallLine line1 = new SongCallLine();
        line1.setBlock(block);
        line1.setLineNo(1);
        line1.setLyrics("歌詞1行目");
        line1 = songCallLineRepository.save(line1);

        SongCallLine line2 = new SongCallLine();
        line2.setBlock(block);
        line2.setLineNo(2);
        line2.setLyrics("歌詞2行目");
        line2 = songCallLineRepository.save(line2);

        saveCallItem(line1, CallType.CHANT, "はい！", null, 1);
        saveCallItem(line1, CallType.CLAP, "クラップ", "#CCCC00", 2);
        saveCallItem(line2, CallType.CHANT, "おい！", null, 1);

        return new TestFixture(site, song);
    }

    private Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    private void saveSongMember(Song song, Member member, int displayOrder) {
        SongMember songMember = new SongMember();
        songMember.setSong(song);
        songMember.setMember(member);
        songMember.setRoleType(SongRoleType.ORIGINAL_VOCAL);
        songMember.setDisplayOrder(displayOrder);
        songMemberRepository.save(songMember);
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

    private void saveCallItem(SongCallLine line, CallType callType, String callText, String styleColorHex, int orderNo) {
        SongCallItem item = new SongCallItem();
        item.setCallLine(line);
        item.setCallTypeCode(callType.getCode());
        item.setCallText(callText);
        item.setStyleColorHex(styleColorHex);
        item.setOrderNo(orderNo);
        songCallItemRepository.save(item);
    }

    private record TestFixture(Site site, Song song) {
    }
}
