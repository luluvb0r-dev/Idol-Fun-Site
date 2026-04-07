import Link from 'next/link';
import type { CSSProperties } from 'react';
import {
    formatSongReleaseDate,
    type CallTypeMaster,
    type SongCallBlock,
    type SongCallItem,
    type SongDetail,
} from '../_lib/song-api';

type SongDetailProps = {
    song: SongDetail;
    callBlocks: SongCallBlock[];
    callTypes: CallTypeMaster[];
    hasCallError: boolean;
    isUsingMockData: boolean;
};

type CallTypeMeta = {
    label: string;
    icon: string;
    className: string;
    colorHex: string;
};

const fallbackCallTypeMeta: Record<string, CallTypeMeta> = {
    CHANT: {
        label: '掛け声',
        icon: '声',
        className: 'call-pill__chant',
        colorHex: '#F06292',
    },
    CLAP: {
        label: 'クラップ',
        icon: '拍',
        className: 'call-pill__clap',
        colorHex: '#FFCA28',
    },
    MIX: {
        label: 'MIX',
        icon: '混',
        className: 'call-pill__mix',
        colorHex: '#4FC3F7',
    },
    JUMP: {
        label: 'ジャンプ',
        icon: '跳',
        className: 'call-pill__jump',
        colorHex: '#81C784',
    },
    OTHER: {
        label: 'コール',
        icon: '補',
        className: 'call-pill__other',
        colorHex: '#B39DDB',
    },
};

function getOriginalMemberText(song: SongDetail) {
    if (song.originalMembers.length === 0) {
        return '未登録';
    }

    return song.originalMembers.map((member) => member.name).join(' / ');
}

function getBlockTypeLabel(blockType: string) {
    const normalized = blockType.toUpperCase();

    if (normalized === 'VERSE') {
        return 'Aメロ';
    }

    if (normalized === 'CHORUS') {
        return 'サビ';
    }

    if (normalized === 'BRIDGE') {
        return 'ブリッジ';
    }

    return 'ブロック';
}

function getCallTypeMeta(call: SongCallItem, callTypeMap: Record<string, CallTypeMaster>): CallTypeMeta {
    const fallback = fallbackCallTypeMeta[call.callTypeCode.toUpperCase()] ?? fallbackCallTypeMeta.OTHER;
    const master = callTypeMap[call.callTypeCode.toUpperCase()];

    return {
        label: call.callTypeLabel || master?.callTypeLabel || fallback.label,
        icon: master?.iconKey || fallback.icon,
        className: fallback.className,
        colorHex: call.style?.colorHex ?? master?.colorHex ?? fallback.colorHex,
    };
}

function getReleaseTrackText(trackNumber: number | null) {
    return trackNumber ? `Track ${trackNumber}` : '収録順未設定';
}

export function SongDetail({
    song,
    callBlocks,
    callTypes,
    hasCallError,
    isUsingMockData,
}: SongDetailProps) {
    const callTypeMap = Object.fromEntries(
        callTypes.map((callType) => [callType.callTypeCode.toUpperCase(), callType]),
    );
    const legendItems =
        callTypes.length > 0
            ? callTypes
            : Object.entries(fallbackCallTypeMeta).map(([callTypeCode, meta]) => ({
                  callTypeCode,
                  callTypeLabel: meta.label,
                  colorHex: meta.colorHex,
                  iconKey: meta.icon,
              }));

    return (
        <div className="page-stack">
            <nav aria-label="パンくず" className="breadcrumbs">
                <Link href="/" className="text-link">
                    ホーム
                </Link>
                <span>/</span>
                <Link href="/songs" className="text-link">
                    楽曲一覧
                </Link>
                <span>/</span>
                <span>{song.title}</span>
            </nav>

            <section className="detail-hero song-detail-hero">
                <div className="song-detail-hero__copy">
                    <span className="eyebrow">Song Detail</span>
                    <h1>{song.title}</h1>
                    {song.titleKana ? <p className="song-detail-hero__kana">{song.titleKana}</p> : null}
                    <div className="tag-row">
                        {song.primaryRelease?.isTitleTrack ? (
                            <span className="song-badge">表題曲</span>
                        ) : (
                            <span className="song-badge song-badge__muted">カップリング</span>
                        )}
                        <span className="detail-chip detail-chip__soft">
                            オリジナル歌唱: {getOriginalMemberText(song)}
                        </span>
                    </div>
                    <p>{song.description || '楽曲の詳細情報をまとめたページです。'}</p>
                    {isUsingMockData ? (
                        <p className="song-detail-note">
                            API 未接続のため、見た目確認用のサンプルデータを表示しています。
                        </p>
                    ) : null}
                </div>

                <div className="song-detail-hero__panel">
                    <span className="hero-card__label">代表作品</span>
                    <strong>{song.primaryRelease?.title ?? '未登録'}</strong>
                    <p>
                        {song.primaryRelease
                            ? formatSongReleaseDate(song.primaryRelease.releaseDate)
                            : 'リリース情報はまだ登録されていません。'}
                    </p>
                    <div className="song-detail-hero__actions">
                        <Link href="/songs" className="text-link">
                            楽曲一覧へ戻る
                        </Link>
                    </div>
                </div>
            </section>

            <div className="song-detail-layout">
                <section className="section-block song-call-section">
                    <div className="section-heading song-call-section__heading">
                        <div>
                            <span className="eyebrow">Lyric & Call</span>
                            <h2>歌詞とコール</h2>
                        </div>
                        <p>歌詞の直下にコールを並べ、見比べながら追える縦レイアウトにしています。</p>
                    </div>

                    {song.hasCallData && callBlocks.length > 0 ? (
                        <div className="song-call-blocks">
                            {callBlocks.map((block) => (
                                <section key={block.blockId} className="song-call-block">
                                    <header className="song-call-block__header">
                                        <span className="song-call-block__eyebrow">
                                            {getBlockTypeLabel(block.blockType)}
                                        </span>
                                        <h3>{block.blockLabel}</h3>
                                    </header>

                                    <div className="song-call-lines">
                                        {block.lines.map((line) => (
                                            <article key={line.lineId} className="song-call-line">
                                                <div className="song-call-line__lyrics">
                                                    <span className="song-call-line__number">
                                                        {line.lineNo.toString().padStart(2, '0')}
                                                    </span>
                                                    <p>{line.lyrics}</p>
                                                </div>

                                                <div className="song-call-line__calls" aria-label="コール">
                                                    {line.calls.length > 0 ? (
                                                        line.calls.map((call) => {
                                                            const meta = getCallTypeMeta(call, callTypeMap);
                                                            return (
                                                                <div
                                                                    key={call.callId}
                                                                    className={`call-pill ${meta.className}`}
                                                                    style={
                                                                        {
                                                                            '--call-accent': meta.colorHex,
                                                                        } as CSSProperties
                                                                    }
                                                                >
                                                                    <span className="call-pill__icon">
                                                                        {meta.icon}
                                                                    </span>
                                                                    <span className="call-pill__label">
                                                                        {meta.label}
                                                                    </span>
                                                                    <strong>{call.callText}</strong>
                                                                </div>
                                                            );
                                                        })
                                                    ) : (
                                                        <div className="call-pill call-pill__empty">
                                                            <span className="call-pill__label">コールなし</span>
                                                            <strong>この行のコールは未登録です</strong>
                                                        </div>
                                                    )}
                                                </div>
                                            </article>
                                        ))}
                                    </div>
                                </section>
                            ))}
                        </div>
                    ) : (
                        <div className="song-call-empty">
                            <span className="eyebrow">Call Data</span>
                            <h3>コール情報はまだ登録されていません</h3>
                            <p>歌詞データは確認できますが、この楽曲のコール行はこれから追加される予定です。</p>
                        </div>
                    )}

                    {hasCallError ? (
                        <div className="song-call-warning">
                            <span className="eyebrow">Partial Error</span>
                            <p>コール API の取得に失敗したため、取得できた情報のみを表示しています。</p>
                        </div>
                    ) : null}
                </section>

                <aside className="side-panel song-detail-side">
                    <div className="song-detail-side__group">
                        <span className="eyebrow">Call Legend</span>
                        <h2>コール凡例</h2>
                        <div className="song-detail-legend">
                            {legendItems.map((item) => {
                                const fallback =
                                    fallbackCallTypeMeta[item.callTypeCode.toUpperCase()] ??
                                    fallbackCallTypeMeta.OTHER;
                                return (
                                    <div
                                        key={item.callTypeCode}
                                        className={`call-pill ${fallback.className}`}
                                        style={
                                            {
                                                '--call-accent': item.colorHex ?? fallback.colorHex,
                                            } as CSSProperties
                                        }
                                    >
                                        <span className="call-pill__icon">{item.iconKey ?? fallback.icon}</span>
                                        <span className="call-pill__label">{item.callTypeLabel}</span>
                                        <strong>{item.callTypeCode}</strong>
                                    </div>
                                );
                            })}
                        </div>
                    </div>

                    <div className="song-detail-side__group">
                        <span className="eyebrow">Releases</span>
                        <h2>収録作品</h2>
                        <div className="song-release-list">
                            {song.releases.map((release) => (
                                <article key={release.releaseId} className="song-release-card">
                                    <div className="song-release-card__head">
                                        <strong>{release.title}</strong>
                                        {release.isPrimary ? (
                                            <span className="detail-chip">代表作品</span>
                                        ) : null}
                                    </div>
                                    <p>{formatSongReleaseDate(release.releaseDate)}</p>
                                    <span>{getReleaseTrackText(release.trackNumber)}</span>
                                </article>
                            ))}
                        </div>
                    </div>

                    <div className="song-detail-side__group">
                        <span className="eyebrow">Original Members</span>
                        <h2>オリジナル歌唱メンバー</h2>
                        <div className="song-member-chips">
                            {song.originalMembers.map((member) => (
                                <div key={member.memberId} className="song-member-chip">
                                    <span
                                        aria-hidden="true"
                                        className="song-member-chip__dot"
                                        style={{
                                            backgroundColor: member.memberColorHex ?? '#f8bbd0',
                                        }}
                                    />
                                    <span>{member.name}</span>
                                </div>
                            ))}
                        </div>
                    </div>
                </aside>
            </div>
        </div>
    );
}
