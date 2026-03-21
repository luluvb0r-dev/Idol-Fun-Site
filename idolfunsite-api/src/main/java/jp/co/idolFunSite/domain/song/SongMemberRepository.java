package jp.co.idolFunSite.domain.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * SongMember エンティティへのデータアクセスを提供するリポジトリ。
 */
@Repository
public interface SongMemberRepository extends JpaRepository<SongMember, Long> {

    /**
     * 楽曲IDに基づいて、その楽曲に参加・歌唱しているメンバー一覧情報を取得します。
     * @param songId 楽曲ID
     * @return 参加メンバーリスト
     */
    List<SongMember> findBySongId(Long songId);
}
