package jp.co.idolFunSite.domain.member;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberGroupHistoryRepository extends JpaRepository<MemberGroupHistory, Long> {
    @EntityGraph(attributePaths = {"member", "group"})
    List<MemberGroupHistory> findByMemberId(Long memberId);

    /**
     * サイト配下のメンバー所属履歴を表示順で取得します。
     *
     * @param siteId サイトID
     * @param memberId メンバーID
     * @return 所属履歴
     */
    @EntityGraph(attributePaths = {"member", "group"})
    List<MemberGroupHistory> findBySiteIdAndMemberIdOrderByJoinedOnAscDisplayOrderAscIdAsc(Long siteId, Long memberId);

    /**
     * サイト配下の複数メンバー所属履歴を一括取得します。
     *
     * @param siteId サイトID
     * @param memberIds メンバーID一覧
     * @return 所属履歴
     */
    @EntityGraph(attributePaths = {"member", "group"})
    List<MemberGroupHistory> findBySiteIdAndMemberIdIn(Long siteId, List<Long> memberIds);
}
