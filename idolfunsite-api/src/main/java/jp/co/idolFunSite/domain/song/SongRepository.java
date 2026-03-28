package jp.co.idolFunSite.domain.song;

import jp.co.idolFunSite.domain.alias.SongAlias;
import jp.co.idolFunSite.domain.release.ReleaseSong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findBySiteId(Long siteId);

    Page<Song> findBySiteId(Long siteId, Pageable pageable);

    /**
     * サイト配下の楽曲を1件取得します。
     *
     * @param id     楽曲ID
     * @param siteId サイトID
     * @return 楽曲
     */
    java.util.Optional<Song> findByIdAndSiteId(Long id, Long siteId);

    /**
     * 条件に一致する楽曲を検索します。
     *
     * @param siteId       サイトID
     * @param keyword      キーワード
     * @param releaseId    作品ID
     * @param isTitleTrack 表題曲フラグ
     * @param memberId     メンバーID
     * @param pageable     ページング情報
     * @return 楽曲一覧
     */
    @Query(
            value = """
                    SELECT DISTINCT s
                    FROM Song s
                    WHERE s.site.id = :siteId
                      AND (
                            :keyword IS NULL
                            OR LOWER(s.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                            OR LOWER(COALESCE(s.titleKana, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
                            OR LOWER(COALESCE(s.titleRoman, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
                            OR EXISTS (
                                SELECT 1
                                FROM SongAlias sa
                                WHERE sa.song = s
                                  AND LOWER(sa.normalizedText) LIKE LOWER(CONCAT('%', :keyword, '%'))
                            )
                      )
                      AND (
                            :releaseId IS NULL
                            OR EXISTS (
                                SELECT 1
                                FROM ReleaseSong rs
                                WHERE rs.song = s
                                  AND rs.release.id = :releaseId
                            )
                      )
                      AND (
                            :isTitleTrack IS NULL
                            OR EXISTS (
                                SELECT 1
                                FROM ReleaseSong rs
                                WHERE rs.song = s
                                  AND rs.isTitleTrack = :isTitleTrack
                            )
                      )
                      AND (
                            :memberId IS NULL
                            OR EXISTS (
                                SELECT 1
                                FROM SongMember sm
                                WHERE sm.song = s
                                  AND sm.member.id = :memberId
                            )
                      )
                    """,
            countQuery = """
                    SELECT COUNT(DISTINCT s.id)
                    FROM Song s
                    WHERE s.site.id = :siteId
                      AND (
                            :keyword IS NULL
                            OR LOWER(s.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                            OR LOWER(COALESCE(s.titleKana, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
                            OR LOWER(COALESCE(s.titleRoman, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
                            OR EXISTS (
                                SELECT 1
                                FROM SongAlias sa
                                WHERE sa.song = s
                                  AND LOWER(sa.normalizedText) LIKE LOWER(CONCAT('%', :keyword, '%'))
                            )
                      )
                      AND (
                            :releaseId IS NULL
                            OR EXISTS (
                                SELECT 1
                                FROM ReleaseSong rs
                                WHERE rs.song = s
                                  AND rs.release.id = :releaseId
                            )
                      )
                      AND (
                            :isTitleTrack IS NULL
                            OR EXISTS (
                                SELECT 1
                                FROM ReleaseSong rs
                                WHERE rs.song = s
                                  AND rs.isTitleTrack = :isTitleTrack
                            )
                      )
                      AND (
                            :memberId IS NULL
                            OR EXISTS (
                                SELECT 1
                                FROM SongMember sm
                                WHERE sm.song = s
                                  AND sm.member.id = :memberId
                            )
                      )
                    """)
    Page<Song> searchSongs(
            @Param("siteId") Long siteId,
            @Param("keyword") String keyword,
            @Param("releaseId") Long releaseId,
            @Param("isTitleTrack") Boolean isTitleTrack,
            @Param("memberId") Long memberId,
            Pageable pageable);
}
