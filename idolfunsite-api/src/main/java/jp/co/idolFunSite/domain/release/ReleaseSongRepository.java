package jp.co.idolFunSite.domain.release;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReleaseSongRepository extends JpaRepository<ReleaseSong, Long> {
    List<ReleaseSong> findByReleaseId(Long releaseId);

    List<ReleaseSong> findBySongId(Long songId);

    List<ReleaseSong> findBySongIdIn(List<Long> songIds);
}
