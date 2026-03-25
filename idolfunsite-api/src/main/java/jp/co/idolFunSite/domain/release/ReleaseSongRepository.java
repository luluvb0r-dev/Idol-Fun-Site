package jp.co.idolFunSite.domain.release;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReleaseSongRepository extends JpaRepository<ReleaseSong, Long> {
    List<ReleaseSong> findByReleaseId(Long releaseId);

    List<ReleaseSong> findBySongId(Long songId);

    /**
     * 楽曲に紐づく掲載作品情報を表示順で取得します。
     *
     * @param songId 楽曲ID
     * @return 掲載作品一覧
     */
    @EntityGraph(attributePaths = {"song", "release"})
    List<ReleaseSong> findBySongIdOrderByDisplayOrderAscIdAsc(Long songId);

    /**
     * 楽曲ID一覧に紐づく掲載作品情報をまとめて取得します。
     *
     * @param songIds 楽曲ID一覧
     * @return 掲載作品一覧
     */
    @EntityGraph(attributePaths = {"song", "release"})
    List<ReleaseSong> findBySongIdIn(List<Long> songIds);
}
