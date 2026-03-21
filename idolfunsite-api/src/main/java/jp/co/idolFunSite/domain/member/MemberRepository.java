package jp.co.idolFunSite.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Member エンティティへのデータアクセスを提供するリポジトリ。
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    /**
     * サイトに属する全メンバー情報を取得します。
     * @param siteId サイトのID
     * @return メンバーのリスト
     */
    List<Member> findBySiteId(Long siteId);
    
    /**
     * 所属グループに属するメンバー情報を取得します。
     * @param groupId グループのID
     * @return 該当グループのメンバーのリスト
     */
    List<Member> findByGroupId(Long groupId);
}
