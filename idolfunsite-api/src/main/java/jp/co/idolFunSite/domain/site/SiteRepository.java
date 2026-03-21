package jp.co.idolFunSite.domain.site;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Site エンティティのデータアクセスを提供するリポジトリ。
 */
@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    
    /**
     * サイトの識別キーを利用してサイト情報を検索します。
     * @param siteKey 対象のサイト識別キー
     * @return 該当サイト情報（存在しない場合は empty）
     */
    Optional<Site> findBySiteKey(String siteKey);
}
