package jp.co.idolFunSite.domain.call;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * SongCallItem エンティティへのデータアクセスを提供するリポジトリ。
 */
@Repository
public interface SongCallItemRepository extends JpaRepository<SongCallItem, Long> {

    /**
     * 特定の歌詞行に紐づく全てのコール要素を表示順（orderNo）で昇順取得します。
     * @param callLineId 対象の歌詞行ID
     * @return コール項目のリスト
     */
    List<SongCallItem> findByCallLineIdOrderByOrderNoAsc(Long callLineId);
}
