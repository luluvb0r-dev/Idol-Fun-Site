package jp.co.idolFunSite.domain.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SongMemberRepository extends JpaRepository<SongMember, Long> {
    List<SongMember> findBySongId(Long songId);

    List<SongMember> findBySongIdIn(List<Long> songIds);
}
