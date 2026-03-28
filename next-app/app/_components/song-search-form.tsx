import Link from 'next/link';
import type { ReleaseSummary } from '../_lib/song-api';
import type { SongSearchFilters } from '../_lib/song-search';

type SongSearchFormProps = {
    filters: SongSearchFilters;
    releases: ReleaseSummary[];
};

export function SongSearchForm({ filters, releases }: SongSearchFormProps) {
    return (
        <section className="song-search-panel">
            <div className="song-search-panel__copy">
                <span className="eyebrow">Song Search</span>
                <h2>楽曲をすばやく探す</h2>
                <p>
                    曲名で検索しながら、掲載シングルと表題曲バッジで候補を絞り込めます。
                </p>
            </div>
            <form method="get" className="song-search-form">
                <label className="song-search-field">
                    <span>キーワード</span>
                    <input
                        type="search"
                        name="keyword"
                        defaultValue={filters.keyword}
                        placeholder="楽曲名で検索"
                    />
                </label>

                <label className="song-search-field">
                    <span>掲載シングル</span>
                    <select name="releaseId" defaultValue={filters.releaseId}>
                        <option value="">すべての作品</option>
                        {releases.map((release) => (
                            <option key={release.releaseId} value={release.releaseId}>
                                {release.title}
                            </option>
                        ))}
                    </select>
                </label>

                <label className="song-search-toggle">
                    <input
                        type="checkbox"
                        name="isTitleTrack"
                        value="true"
                        defaultChecked={filters.isTitleTrack}
                    />
                    <span>表題曲のみ表示</span>
                </label>

                <div className="song-search-actions">
                    <button type="submit" className="primary-link song-search-submit">
                        検索する
                    </button>
                    <Link href="/songs" className="text-link">
                        条件をクリア
                    </Link>
                </div>
            </form>
        </section>
    );
}
