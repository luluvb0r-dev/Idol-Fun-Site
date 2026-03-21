package jp.co.idolFunSite.domain.single;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * SingleRelease エンティティへのデータアクセスを提供するリポジトリ。
 */
@Repository
public interface SingleReleaseRepository extends JpaRepository<SingleRelease, Long> {

    /**
     * 特定のサイトの全リリース情報を取得します。
     * @param siteId 対象サイトID
     * @return リリース作品のリスト
     */
    List<SingleRelease> findBySiteId(Long siteId);
}
