package jp.co.idolFunSite.domain.release;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SingleReleaseRepository extends JpaRepository<SingleRelease, Long> {
    List<SingleRelease> findBySiteId(Long siteId);
}
