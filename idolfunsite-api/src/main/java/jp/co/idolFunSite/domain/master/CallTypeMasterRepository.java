package jp.co.idolFunSite.domain.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CallTypeMasterRepository extends JpaRepository<CallTypeMaster, Long> {
    List<CallTypeMaster> findBySiteId(Long siteId);

    /**
     * 有効なコール種別マスタを表示順で取得します。
     *
     * @param siteId サイトID
     * @return コール種別マスタ一覧
     */
    List<CallTypeMaster> findBySiteIdAndIsActiveTrueOrderByDisplayOrderAsc(Long siteId);
}
