package jp.co.idolFunSite.app.service.song;

import jp.co.idolFunSite.app.dto.song.SongListResponse;
import jp.co.idolFunSite.app.dto.song.SongSearchCondition;
import jp.co.idolFunSite.domain.release.ReleaseSong;
import jp.co.idolFunSite.domain.release.ReleaseSongRepository;
import jp.co.idolFunSite.domain.song.Song;
import jp.co.idolFunSite.domain.song.SongMember;
import jp.co.idolFunSite.domain.song.SongMemberRepository;
import jp.co.idolFunSite.domain.song.SongRepository;
import jp.co.idolFunSite.domain.song.SongRoleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 楽曲関連のビジネスロジックを提供するサービスです。
 */
@Service
@Transactional(readOnly = true)
public class SongService {

    private static final Logger log = LoggerFactory.getLogger(SongService.class);

    private final SongRepository songRepository;
    private final SongMemberRepository songMemberRepository;
    private final ReleaseSongRepository releaseSongRepository;

    public SongService(SongRepository songRepository, SongMemberRepository songMemberRepository,
            ReleaseSongRepository releaseSongRepository) {
        this.songRepository = songRepository;
        this.songMemberRepository = songMemberRepository;
        this.releaseSongRepository = releaseSongRepository;
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
            Page<Song> songPage;

            if (condition.keyword() != null && !condition.keyword().isBlank()) {
                log.debug("searchSongs - Keyword is present, searching by keyword: {}", condition.keyword());
                songPage = songRepository.findBySiteIdAndKeyword(condition.siteId(), condition.keyword(), pageable);
            } else {
                log.debug("searchSongs - Keyword is empty, searching all for siteId: {}", condition.siteId());
                songPage = songRepository.findBySiteId(condition.siteId(), pageable);
            }

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
                        .map(sm -> sm.getMember().getMemberName())
                        .toList();

                List<ReleaseSong> releases = releasesBySongId.getOrDefault(song.getId(), List.of());

                ReleaseSong mainRelease = releases.stream()
                        .filter(rs -> Boolean.TRUE.equals(rs.getIsTitleTrack())
                                || Boolean.TRUE.equals(rs.getIsPrimary()))
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
}
