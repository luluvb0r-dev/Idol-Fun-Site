package jp.co.idolFunSite.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("""
            SELECT m
            FROM Member m
            JOIN MemberGroupHistory mgh ON mgh.member = m
            WHERE mgh.group.id = :groupId
            ORDER BY mgh.displayOrder ASC, m.displayOrder ASC, m.id ASC
            """)
    List<Member> findByGroupId(@Param("groupId") Long groupId);
}
