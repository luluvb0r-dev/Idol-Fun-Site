package jp.co.idolFunSite.domain.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SongMemberRepository extends JpaRepository<SongMember, Long> {
    List<SongMember> findBySongId(Long songId);

    /**
     * 楽曲ID一覧に紐づく歌唱メンバーをまとめて取得します。
     *
     * @param songIds 楽曲ID一覧
     * @return 歌唱メンバー一覧
     */
    @EntityGraph(attributePaths = {"song", "member"})
    List<SongMember> findBySongIdIn(List<Long> songIds);
}
