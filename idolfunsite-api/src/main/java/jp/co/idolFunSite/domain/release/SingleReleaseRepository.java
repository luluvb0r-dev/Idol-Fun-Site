package jp.co.idolFunSite.domain.release;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleReleaseRepository extends JpaRepository<SingleRelease, Long> {
    List<SingleRelease> findBySiteId(Long siteId);

    /**
     * サイト配下の作品一覧をソート付きで取得します。
     *
     * @param siteId サイトID
     * @param sort ソート条件
     * @return 作品一覧
     */
    List<SingleRelease> findBySiteId(Long siteId, Sort sort);

    /**
     * サイト配下の作品詳細を取得します。
     *
     * @param id 作品ID
     * @param siteId サイトID
     * @return 作品
     */
    Optional<SingleRelease> findByIdAndSiteId(Long id, Long siteId);
}
