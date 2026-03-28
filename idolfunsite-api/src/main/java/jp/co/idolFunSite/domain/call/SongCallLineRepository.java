package jp.co.idolFunSite.domain.call;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * SongCallLine エンティティへのデータアクセスを提供するリポジトリ。
 */
@Repository
public interface SongCallLineRepository extends JpaRepository<SongCallLine, Long> {

    /**
     * 対象ブロック内のすべての行番号（lineNo）で昇順取得します。
     * @param blockId ブロックID
     * @return 歌詞行のリスト
     */
    List<SongCallLine> findByBlockIdOrderByLineNoAsc(Long blockId);

    /**
     * 対象ブロック群の歌詞行をまとめて取得します。
     *
     * @param blockIds ブロックID一覧
     * @return 歌詞行一覧
     */
    @EntityGraph(attributePaths = {"block"})
    List<SongCallLine> findByBlockIdInOrderByBlockIdAscLineNoAsc(List<Long> blockIds);
}
