package jp.co.idolFunSite.domain.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Member エンティティへのデータアクセスを提供するリポジトリです。
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * サイト配下の有効なメンバーを表示順で取得します。
     *
     * @param siteId サイトID
     * @param status メンバーステータス
     * @return メンバー一覧
     */
    List<Member> findBySiteIdAndStatusOrderByDisplayOrderAscIdAsc(Long siteId, String status);

    /**
     * サイト配下の有効なメンバー詳細を取得します。
     *
     * @param id メンバーID
     * @param siteId サイトID
     * @param status メンバーステータス
     * @return メンバー
     */
    Optional<Member> findByIdAndSiteIdAndStatus(Long id, Long siteId, String status);

    /**
     * 指定グループに所属するメンバー一覧を取得します。
     *
     * @param groupId グループID
     * @return 所属グループのメンバー一覧
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
