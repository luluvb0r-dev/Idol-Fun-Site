package jp.co.idolFunSite.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberGroupHistoryRepository extends JpaRepository<MemberGroupHistory, Long> {
    List<MemberGroupHistory> findByMemberId(Long memberId);
}
