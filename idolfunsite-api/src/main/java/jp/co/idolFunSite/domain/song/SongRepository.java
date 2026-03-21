package jp.co.idolFunSite.domain.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Song エンティティへのデータアクセスを提供するリポジトリ。
 */
@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    /**
     * サイトIDに基づいて楽曲の一覧を取得します。
     * @param siteId 対象サイトID
     * @return 楽曲リスト
     */
    List<Song> findBySiteId(Long siteId);
}
