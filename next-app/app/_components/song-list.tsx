import Link from 'next/link';
import { formatSongReleaseDate, type SongSummary } from '../_lib/song-api';

type SongListProps = {
    songs: SongSummary[];
    hasError: boolean;
    totalCount: number;
    activeKeyword: string;
};

function getOriginalMemberText(song: SongSummary) {
    if (song.originalMembers.length === 0) {
        return '未登録';
    }

    return song.originalMembers.map((member) => member.name).join(' / ');
}

export function SongList({ songs, hasError, totalCount, activeKeyword }: SongListProps) {
    if (hasError) {
        return (
            <section className="song-state-panel">
                <span className="eyebrow">API Error</span>
                <h2>楽曲データを取得できませんでした</h2>
                <p>
                    API サーバーに接続できないため、`API_BASE_URL` の設定値と起動状態を確認してください。
                </p>
            </section>
        );
    }

    if (songs.length === 0) {
        return (
            <section className="song-state-panel">
                <span className="eyebrow">No Result</span>
                <h2>条件に合う楽曲が見つかりませんでした</h2>
                <p>
                    {activeKeyword
                        ? `「${activeKeyword}」に近い表記や別のシングル条件で、もう一度検索してみてください。`
                        : 'キーワードや絞り込み条件を変えて、もう一度検索してみてください。'}
                </p>
            </section>
        );
    }

    return (
        <section className="section-block">
            <div className="section-heading song-list-heading">
                <div>
                    <span className="eyebrow">Song Index</span>
                    <h2>楽曲一覧</h2>
                </div>
                <p>{totalCount} 曲の楽曲を表示しています。</p>
            </div>

            <div className="song-card-grid">
                {songs.map((song) => (
                    <Link key={song.songId} href={`/songs/${song.songId}`} className="song-card-link">
                        <article className="song-card">
                            <div className="song-card__header">
                                <div>
                                    <h3>{song.title}</h3>
                                    {song.titleKana ? <p>{song.titleKana}</p> : null}
                                </div>
                                {song.primaryRelease?.isTitleTrack ? (
                                    <span className="song-badge">表題曲</span>
                                ) : (
                                    <span className="song-badge song-badge__muted">カップリング</span>
                                )}
                            </div>

                            <dl className="song-card__details">
                                <div>
                                    <dt>オリジナル歌唱メンバー</dt>
                                    <dd>{getOriginalMemberText(song)}</dd>
                                </div>
                                <div>
                                    <dt>代表作品</dt>
                                    <dd>{song.primaryRelease ? song.primaryRelease.title : '未登録'}</dd>
                                </div>
                                <div>
                                    <dt>発売日</dt>
                                    <dd>
                                        {song.primaryRelease
                                            ? formatSongReleaseDate(song.primaryRelease.releaseDate)
                                            : '未登録'}
                                    </dd>
                                </div>
                                <div>
                                    <dt>コール情報</dt>
                                    <dd>{song.hasCallData ? 'あり' : 'なし'}</dd>
                                </div>
                            </dl>

                            <div className="song-card__footer">
                                <span className="text-link">詳細を見る</span>
                            </div>
                        </article>
                    </Link>
                ))}
            </div>
        </section>
    );
}
