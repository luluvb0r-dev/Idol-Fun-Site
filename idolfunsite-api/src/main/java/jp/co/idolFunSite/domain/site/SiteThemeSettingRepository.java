package jp.co.idolFunSite.domain.site;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SiteThemeSettingRepository extends JpaRepository<SiteThemeSetting, Long> {
    Optional<SiteThemeSetting> findBySiteId(Long siteId);
}
