package jp.co.idolFunSite.app.service.song;

import jp.co.idolFunSite.app.dto.song.SongCallResponse;
import jp.co.idolFunSite.app.dto.song.SongDetailResponse;
import jp.co.idolFunSite.app.dto.song.SongListResponse;
import jp.co.idolFunSite.app.dto.song.SongSearchCondition;
import jp.co.idolFunSite.domain.call.CallType;
import jp.co.idolFunSite.domain.call.SongCallBlock;
import jp.co.idolFunSite.domain.call.SongCallBlockRepository;
import jp.co.idolFunSite.domain.call.SongCallItem;
import jp.co.idolFunSite.domain.call.SongCallItemRepository;
import jp.co.idolFunSite.domain.call.SongCallLine;
import jp.co.idolFunSite.domain.call.SongCallLineRepository;
import jp.co.idolFunSite.domain.common.Status;
import jp.co.idolFunSite.domain.master.CallTypeMaster;
import jp.co.idolFunSite.domain.master.CallTypeMasterRepository;
import jp.co.idolFunSite.domain.release.ReleaseSong;
import jp.co.idolFunSite.domain.release.ReleaseSongRepository;
import jp.co.idolFunSite.domain.site.Site;
import jp.co.idolFunSite.domain.site.SiteRepository;
import jp.co.idolFunSite.domain.song.Song;
import jp.co.idolFunSite.domain.song.SongMember;
import jp.co.idolFunSite.domain.song.SongMemberRepository;
import jp.co.idolFunSite.domain.song.SongRepository;
import jp.co.idolFunSite.domain.song.SongRoleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 楽曲関連のビジネスロジックを提供するサービスです。
 */
@Service
@Transactional(readOnly = true)
public class SongService {

    private static final Logger log = LoggerFactory.getLogger(SongService.class);

    private final SiteRepository siteRepository;
    private final SongRepository songRepository;
    private final SongMemberRepository songMemberRepository;
    private final ReleaseSongRepository releaseSongRepository;
    private final SongCallBlockRepository songCallBlockRepository;
    private final SongCallLineRepository songCallLineRepository;
    private final SongCallItemRepository songCallItemRepository;
    private final CallTypeMasterRepository callTypeMasterRepository;

    public SongService(SiteRepository siteRepository, SongRepository songRepository, SongMemberRepository songMemberRepository,
            ReleaseSongRepository releaseSongRepository, SongCallBlockRepository songCallBlockRepository,
            SongCallLineRepository songCallLineRepository, SongCallItemRepository songCallItemRepository,
            CallTypeMasterRepository callTypeMasterRepository) {
        this.siteRepository = siteRepository;
        this.songRepository = songRepository;
        this.songMemberRepository = songMemberRepository;
        this.releaseSongRepository = releaseSongRepository;
        this.songCallBlockRepository = songCallBlockRepository;
        this.songCallLineRepository = songCallLineRepository;
        this.songCallItemRepository = songCallItemRepository;
        this.callTypeMasterRepository = callTypeMasterRepository;
    }

    /**
     * 検索条件に一致する楽曲をページングして取得します。
     *
     * @param condition 検索条件
     * @param pageable  ページング情報
     * @return 楽曲情報のページ
     */
    public Page<SongListResponse> searchSongs(SongSearchCondition condition, Pageable pageable) {
        log.info("searchSongs - start. condition: {}, pageable: {}", condition, pageable);

        try {
            Site site = siteRepository.findBySiteKeyAndStatus(condition.siteKey(), Status.PUBLISHED)
                    .orElseThrow(() -> {
                        log.warn("searchSongs - Published site was not found. siteKey: {}", condition.siteKey());
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Site not found.");
                    });

            log.debug(
                    "searchSongs - Resolved site. siteId: {}, keyword: {}, releaseId: {}, isTitleTrack: {}, memberId: {}",
                    site.getId(),
                    condition.keyword(),
                    condition.releaseId(),
                    condition.isTitleTrack(),
                    condition.memberId());

            Page<Song> songPage = songRepository.searchSongs(
                    site.getId(),
                    condition.keyword(),
                    condition.releaseId(),
                    condition.isTitleTrack(),
                    condition.memberId(),
                    pageable);

            List<Long> songIds = songPage.getContent().stream().map(Song::getId).toList();
            log.debug("searchSongs - Fetched {} songs", songIds.size());

            List<SongMember> songMembers;
            List<ReleaseSong> releaseSongs;

            if (songIds.isEmpty()) {
                log.debug("searchSongs - No songs found. Skip fetching relations.");
                songMembers = List.of();
                releaseSongs = List.of();
            } else {
                log.debug("searchSongs - Fetching song members and releases.");
                songMembers = songMemberRepository.findBySongIdIn(songIds);
                releaseSongs = releaseSongRepository.findBySongIdIn(songIds);
            }

            Map<Long, List<SongMember>> membersBySongId = songMembers.stream()
                    .collect(Collectors.groupingBy(sm -> sm.getSong().getId()));

            Map<Long, List<ReleaseSong>> releasesBySongId = releaseSongs.stream()
                    .collect(Collectors.groupingBy(rs -> rs.getSong().getId()));

            Page<SongListResponse> result = songPage.map(song -> {
                List<SongMember> members = membersBySongId.getOrDefault(song.getId(), List.of());
                List<String> originalMemberNames = members.stream()
                        .filter(sm -> sm.getRoleType() == SongRoleType.ORIGINAL_VOCAL)
                        .sorted(Comparator.comparing(SongMember::getDisplayOrder)
                                .thenComparing(sm -> sm.getMember().getId()))
                        .map(sm -> sm.getMember().getMemberName())
                        .toList();

                List<ReleaseSong> releases = releasesBySongId.getOrDefault(song.getId(), List.of());

                ReleaseSong mainRelease = releases.stream()
                        .sorted(Comparator
                                .comparing((ReleaseSong rs) -> !Boolean.TRUE.equals(rs.getIsPrimary()))
                                .thenComparing(rs -> !Boolean.TRUE.equals(rs.getIsTitleTrack()))
                                .thenComparing(ReleaseSong::getDisplayOrder)
                                .thenComparing(ReleaseSong::getId))
                        .findFirst()
                        .orElse(releases.isEmpty() ? null : releases.get(0));

                String singleTitle = mainRelease != null && mainRelease.getRelease() != null
                        ? mainRelease.getRelease().getTitle()
                        : null;
                boolean isTitleTrack = mainRelease != null && Boolean.TRUE.equals(mainRelease.getIsTitleTrack());

                return new SongListResponse(
                        song.getId(),
                        song.getTitle(),
                        originalMemberNames,
                        singleTitle,
                        isTitleTrack);
            });

            log.info("searchSongs - end. result size: {}", result.getContent().size());
            return result;
        } catch (Exception e) {
            log.error("searchSongs - Error occurred while searching songs.", e);
            throw e;
        }
    }

    /**
     * 楽曲詳細画面向けの情報を取得します。
     *
     * @param siteKey サイト識別子
     * @param songId  楽曲ID
     * @return 楽曲詳細情報
     */
    public SongDetailResponse getSongDetail(String siteKey, Long songId) {
        log.info("getSongDetail - start. siteKey: {}, songId: {}", siteKey, songId);

        try {
            Site site = getPublishedSite(siteKey, "getSongDetail");
            Song song = getSong(site.getId(), songId, "getSongDetail");
            List<ReleaseSong> releaseSongs = releaseSongRepository.findBySongIdOrderByDisplayOrderAscIdAsc(songId);
            List<SongMember> songMembers = songMemberRepository.findBySongIdOrderByDisplayOrderAscIdAsc(songId);

            ReleaseSong primaryRelease = selectPrimaryRelease(releaseSongs);
            SongDetailResponse response = new SongDetailResponse(
                    song.getId(),
                    song.getTitle(),
                    song.getTitleKana(),
                    song.getDescription(),
                    song.getHasCallData(),
                    toPrimaryReleaseResponse(primaryRelease),
                    releaseSongs.stream().map(this::toReleaseResponse).toList(),
                    songMembers.stream()
                            .filter(songMember -> songMember.getRoleType() == SongRoleType.ORIGINAL_VOCAL)
                            .sorted(Comparator.comparing(SongMember::getDisplayOrder)
                                    .thenComparing(songMember -> songMember.getMember().getId()))
                            .map(songMember -> new SongDetailResponse.OriginalMemberResponse(
                                    songMember.getMember().getId(),
                                    songMember.getMember().getMemberName(),
                                    songMember.getMember().getMemberColorHex()))
                            .toList());

            log.info("getSongDetail - end. status: success, releaseCount: {}, memberCount: {}",
                    response.releases().size(),
                    response.originalMembers().size());
            return response;
        } catch (Exception e) {
            log.error("getSongDetail - Error occurred while getting song detail.", e);
            throw e;
        }
    }

    /**
     * 楽曲コール表示向けの情報を取得します。
     *
     * @param siteKey サイト識別子
     * @param songId  楽曲ID
     * @return 楽曲コール情報
     */
    public SongCallResponse getSongCalls(String siteKey, Long songId) {
        log.info("getSongCalls - start. siteKey: {}, songId: {}", siteKey, songId);

        try {
            Site site = getPublishedSite(siteKey, "getSongCalls");
            Song song = getSong(site.getId(), songId, "getSongCalls");

            List<SongCallBlock> blocks = songCallBlockRepository.findBySongIdOrderByOrderNoAsc(songId);
            List<Long> blockIds = blocks.stream().map(SongCallBlock::getId).toList();
            List<SongCallLine> lines = blockIds.isEmpty()
                    ? List.of()
                    : songCallLineRepository.findByBlockIdInOrderByBlockIdAscLineNoAsc(blockIds);

            List<Long> lineIds = lines.stream().map(SongCallLine::getId).toList();
            List<SongCallItem> callItems = lineIds.isEmpty()
                    ? List.of()
                    : songCallItemRepository.findByCallLineIdInOrderByCallLineIdAscOrderNoAsc(lineIds);

            Map<String, CallTypeMaster> callTypeMap = callTypeMasterRepository
                    .findBySiteIdAndIsActiveTrueOrderByDisplayOrderAsc(site.getId())
                    .stream()
                    .collect(Collectors.toMap(CallTypeMaster::getCallTypeCode, Function.identity(), (left, right) -> left));

            Map<Long, List<SongCallLine>> linesByBlockId = lines.stream()
                    .collect(Collectors.groupingBy(line -> line.getBlock().getId()));
            Map<Long, List<SongCallItem>> callItemsByLineId = callItems.stream()
                    .collect(Collectors.groupingBy(item -> item.getCallLine().getId()));

            List<SongCallResponse.BlockResponse> blockResponses = blocks.stream()
                    .sorted(Comparator.comparing(SongCallBlock::getOrderNo).thenComparing(SongCallBlock::getId))
                    .map(block -> new SongCallResponse.BlockResponse(
                            block.getId(),
                            block.getBlockType().name(),
                            block.getBlockLabel(),
                            block.getOrderNo(),
                            linesByBlockId.getOrDefault(block.getId(), List.of()).stream()
                                    .sorted(Comparator.comparing(SongCallLine::getLineNo).thenComparing(SongCallLine::getId))
                                    .map(line -> new SongCallResponse.LineResponse(
                                            line.getId(),
                                            line.getLineNo(),
                                            line.getLyrics(),
                                            callItemsByLineId.getOrDefault(line.getId(), List.of()).stream()
                                                    .sorted(Comparator.comparing(SongCallItem::getOrderNo)
                                                            .thenComparing(SongCallItem::getId))
                                                    .map(callItem -> toCallItemResponse(callItem, callTypeMap))
                                                    .toList()))
                                    .toList()))
                    .toList();

            SongCallResponse response = new SongCallResponse(song.getId(), song.getTitle(), blockResponses);
            log.info("getSongCalls - end. status: success, blockCount: {}", response.blocks().size());
            return response;
        } catch (Exception e) {
            log.error("getSongCalls - Error occurred while getting song calls.", e);
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

    private Song getSong(Long siteId, Long songId, String logPrefix) {
        return songRepository.findByIdAndSiteId(songId, siteId)
                .orElseThrow(() -> {
                    log.warn("{} - Song was not found. siteId: {}, songId: {}", logPrefix, siteId, songId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found.");
                });
    }

    private ReleaseSong selectPrimaryRelease(List<ReleaseSong> releases) {
        return releases.stream()
                .sorted(Comparator
                        .comparing((ReleaseSong releaseSong) -> !Boolean.TRUE.equals(releaseSong.getIsPrimary()))
                        .thenComparing(releaseSong -> !Boolean.TRUE.equals(releaseSong.getIsTitleTrack()))
                        .thenComparing(ReleaseSong::getDisplayOrder)
                        .thenComparing(ReleaseSong::getId))
                .findFirst()
                .orElse(null);
    }

    private SongDetailResponse.PrimaryReleaseResponse toPrimaryReleaseResponse(ReleaseSong releaseSong) {
        if (releaseSong == null || releaseSong.getRelease() == null) {
            return null;
        }

        return new SongDetailResponse.PrimaryReleaseResponse(
                releaseSong.getRelease().getId(),
                releaseSong.getRelease().getTitle(),
                releaseSong.getRelease().getReleaseDate(),
                releaseSong.getIsTitleTrack());
    }

    private SongDetailResponse.ReleaseResponse toReleaseResponse(ReleaseSong releaseSong) {
        return new SongDetailResponse.ReleaseResponse(
                releaseSong.getRelease().getId(),
                releaseSong.getRelease().getTitle(),
                releaseSong.getRelease().getReleaseDate(),
                releaseSong.getTrackNumber(),
                releaseSong.getIsPrimary(),
                releaseSong.getIsTitleTrack());
    }

    private SongCallResponse.CallItemResponse toCallItemResponse(
            SongCallItem callItem,
            Map<String, CallTypeMaster> callTypeMap) {
        String normalizedCallTypeCode = CallType.fromCode(callItem.getCallTypeCode()).getCode();
        CallTypeMaster callTypeMaster = callTypeMap.get(normalizedCallTypeCode);
        String colorHex = callItem.getStyleColorHex() != null ? callItem.getStyleColorHex()
                : callTypeMaster != null ? callTypeMaster.getColorHex() : null;
        String iconKey = callTypeMaster != null ? callTypeMaster.getIconKey() : null;
        String callTypeLabel = callTypeMaster != null ? callTypeMaster.getCallTypeLabel() : normalizedCallTypeCode;

        return new SongCallResponse.CallItemResponse(
                callItem.getId(),
                normalizedCallTypeCode,
                callTypeLabel,
                callItem.getCallText(),
                new SongCallResponse.StyleResponse(colorHex, iconKey));
    }
}
