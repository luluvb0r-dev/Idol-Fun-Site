package jp.co.idolFunSite.domain.alias;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberAliasRepository extends JpaRepository<MemberAlias, Long> {
    List<MemberAlias> findByMemberId(Long memberId);
}
