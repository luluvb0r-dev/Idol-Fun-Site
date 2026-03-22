package jp.co.idolFunSite.domain.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CallTypeMasterRepository extends JpaRepository<CallTypeMaster, Long> {
    List<CallTypeMaster> findBySiteId(Long siteId);
}
