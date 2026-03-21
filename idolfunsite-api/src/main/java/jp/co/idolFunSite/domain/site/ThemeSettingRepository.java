package jp.co.idolFunSite.domain.site;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * ThemeSetting エンティティへのデータアクセスを提供するリポジトリ。
 */
@Repository
public interface ThemeSettingRepository extends JpaRepository<ThemeSetting, Long> {
    
    /**
     * サイトIDからテーマ設定情報を取得します。
     * @param siteId 対象のサイトID
     * @return 該当するテーマ設定
     */
    Optional<ThemeSetting> findBySiteId(Long siteId);
}
