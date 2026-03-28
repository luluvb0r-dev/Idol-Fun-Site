package jp.co.idolFunSite.domain.site;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import jp.co.idolFunSite.domain.common.Status;

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

    /**
     * サイト識別キーと公開ステータスを指定してサイト情報を検索します。
     * @param siteKey 対象のサイト識別キー
     * @param status 対象の公開ステータス
     * @return 該当サイト情報（存在しない場合は empty）
     */
    Optional<Site> findBySiteKeyAndStatus(String siteKey, Status status);
}
