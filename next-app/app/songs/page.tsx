import type { Metadata } from 'next';
import { SongList } from '../_components/song-list';
import { SongSearchForm } from '../_components/song-search-form';
import { fetchReleases, fetchSongs } from '../_lib/song-api';
import { normalizeSongSearchParams, type RawSearchParams } from '../_lib/song-search';

export const metadata: Metadata = {
    title: '楽曲一覧 | =LOVE Fan Site',
    description: '=LOVE の楽曲を検索しながら一覧で確認できます。',
};

export const dynamic = 'force-dynamic';

type SongsPageProps = {
    searchParams?: RawSearchParams;
};

export default async function SongsPage({ searchParams = {} }: SongsPageProps) {
    const filters = normalizeSongSearchParams(searchParams);
    const [songsResult, releasesResult] = await Promise.allSettled([
        fetchSongs(filters),
        fetchReleases(),
    ]);

    const hasSongError = songsResult.status === 'rejected';
    const songs = songsResult.status === 'fulfilled' ? songsResult.value.items : [];
    const totalCount =
        songsResult.status === 'fulfilled' ? songsResult.value.pagination.totalElements : 0;
    const releases = releasesResult.status === 'fulfilled' ? releasesResult.value : [];

    return (
        <div className="page-stack">
            <section className="songs-hero">
                <div className="songs-hero__copy">
                    <span className="eyebrow">=LOVE Songs</span>
                    <h1>好きな楽曲を、迷わず一覧から見つける</h1>
                    <p>
                        楽曲名、オリジナル歌唱メンバー、掲載シングル、表題曲かどうかを一画面で確認できる検索ページです。
                    </p>
                </div>
                <div className="songs-hero__summary">
                    <span>表示件数</span>
                    <strong>{totalCount} 曲</strong>
                    <p>
                        キーワード検索と作品選択を組み合わせて、見たい曲まで最短でたどり着けます。
                    </p>
                </div>
            </section>

            <SongSearchForm filters={filters} releases={releases} />
            <SongList
                songs={songs}
                hasError={hasSongError}
                totalCount={totalCount}
                activeKeyword={filters.keyword}
            />
        </div>
    );
}
