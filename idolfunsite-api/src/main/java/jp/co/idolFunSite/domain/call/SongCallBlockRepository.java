package jp.co.idolFunSite.domain.call;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * SongCallBlock エンティティへのデータアクセスを提供するリポジトリ。
 */
@Repository
public interface SongCallBlockRepository extends JpaRepository<SongCallBlock, Long> {

    /**
     * 該当楽曲に含まれるすべてのブロックを表示順（orderNo）で昇順取得します。
     * @param songId 楽曲ID
     * @return 歌詞ブロックのリスト
     */
    List<SongCallBlock> findBySongIdOrderByOrderNoAsc(Long songId);
}
