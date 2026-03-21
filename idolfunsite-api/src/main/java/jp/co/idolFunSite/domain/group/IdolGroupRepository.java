package jp.co.idolFunSite.domain.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * IdolGroup エンティティへのデータアクセスを提供するリポジトリ。
 */
@Repository
public interface IdolGroupRepository extends JpaRepository<IdolGroup, Long> {
    
    /**
     * 特定のサイトに紐づく全てのアイドルグループを取得します。
     * @param siteId 対象のサイトID
     * @return 該当するグループのリスト
     */
    List<IdolGroup> findBySiteId(Long siteId);
}
