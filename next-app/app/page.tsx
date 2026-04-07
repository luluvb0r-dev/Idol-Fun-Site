import Link from 'next/link';
import { fetchMembers } from './_lib/member-api';
import { pickFeaturedSongs } from './_lib/member-view';
import { fetchSite } from './_lib/site-api';
import { fetchSongs } from './_lib/song-api';

export const dynamic = 'force-dynamic';

export default async function HomePage() {
    const [siteResult, songsResult, membersResult] = await Promise.allSettled([
        fetchSite(),
        fetchSongs({
            keyword: '',
            releaseId: '',
            isTitleTrack: false,
            page: 0,
        }),
        fetchMembers(),
    ]);

    const site = siteResult.status === 'fulfilled' ? siteResult.value : null;
    const songs = songsResult.status === 'fulfilled' ? pickFeaturedSongs(songsResult.value.items) : [];
    const members = membersResult.status === 'fulfilled' ? membersResult.value.slice(0, 3) : [];

    return (
        <div className="page-stack">
            <section className="hero-panel home-hero">
                <div className="hero-copy">
                    <span className="eyebrow">{site?.idolName ?? '=LOVE'} Fan Experience</span>
                    <h1>{site?.siteName ?? '=LOVE Fan Site'} で、楽曲とメンバーをやさしく追いかける</h1>
                    <p>
                        楽曲一覧からコール確認、メンバープロフィールまで、推し活の導線を一つの体験にまとめたファンサイトです。
                    </p>
                    <div className="hero-actions">
                        <Link href="/songs" className="primary-link">
                            楽曲一覧を見る
                        </Link>
                        <Link href="/members" className="text-link">
                            メンバー一覧を見る
                        </Link>
                    </div>
                </div>
                <div className="hero-card">
                    <p className="hero-card__label">Theme Color</p>
                    <h2>{site?.idolName ?? '=LOVE'}</h2>
                    <p>サイト全体でテーマカラーを反映し、楽曲ページでも同じトーンで迷わず閲覧できます。</p>
                    <div className="theme-swatches">
                        <span
                            className="theme-swatches__item"
                            style={{ backgroundColor: site?.theme.primaryColorHex ?? '#e85d95' }}
                        />
                        <span
                            className="theme-swatches__item"
                            style={{ backgroundColor: site?.theme.secondaryColorHex ?? '#fff1f7' }}
                        />
                        <span
                            className="theme-swatches__item"
                            style={{ backgroundColor: site?.theme.accentColorHex ?? '#ffc1da' }}
                        />
                    </div>
                </div>
            </section>

            <section className="section-block">
                <div className="section-heading">
                    <div>
                        <span className="eyebrow">Featured Songs</span>
                        <h2>ピックアップ楽曲</h2>
                    </div>
                    <p>一覧 API の先頭数件を使って、入口からそのまま楽曲詳細へ移動できるようにしています。</p>
                </div>
                {songs.length > 0 ? (
                    <div className="song-card-grid">
                        {songs.map((song) => (
                            <Link key={song.songId} href={`/songs/${song.songId}`} className="song-card-link">
                                <article className="song-card">
                                    <div className="song-card__header">
                                        <div>
                                            <h3>{song.title}</h3>
                                            <p>{song.primaryRelease?.title ?? '代表作品未登録'}</p>
                                        </div>
                                        <span className="song-badge">
                                            {song.hasCallData ? 'コールあり' : 'コール準備中'}
                                        </span>
                                    </div>
                                    <p>{song.originalMembers.map((member) => member.name).join(' / ') || '歌唱メンバー未登録'}</p>
                                </article>
                            </Link>
                        ))}
                    </div>
                ) : (
                    <div className="song-call-empty">
                        <h3>ピックアップ楽曲を表示できませんでした</h3>
                        <p>楽曲 API が利用可能になると、ここにおすすめ楽曲が表示されます。</p>
                    </div>
                )}
            </section>

            <section className="section-block">
                <div className="section-heading">
                    <div>
                        <span className="eyebrow">Members</span>
                        <h2>注目メンバー</h2>
                    </div>
                    <p>メンバー一覧ページへつながる入口として、先頭の数名をカードで見せています。</p>
                </div>
                {members.length > 0 ? (
                    <div className="member-card-grid">
                        {members.map((member) => (
                            <Link key={member.memberId} href={`/members/${member.memberId}`} className="member-card-link">
                                <article className="member-card member-card__compact">
                                    <div
                                        className="member-card__placeholder"
                                        style={{
                                            backgroundColor: member.memberColorHex ?? 'rgba(255, 228, 239, 0.92)',
                                        }}
                                    >
                                        <span>{member.name.slice(0, 1)}</span>
                                    </div>
                                    <div className="member-card__body">
                                        <h3>{member.name}</h3>
                                        <p>{member.currentGroup?.groupName ?? '所属情報未登録'}</p>
                                    </div>
                                </article>
                            </Link>
                        ))}
                    </div>
                ) : (
                    <div className="song-call-empty">
                        <h3>注目メンバーを表示できませんでした</h3>
                        <p>メンバー API が利用可能になると、ここにプロフィール導線が表示されます。</p>
                    </div>
                )}
            </section>
        </div>
    );
}
