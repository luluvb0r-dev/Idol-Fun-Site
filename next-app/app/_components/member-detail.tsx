import Link from 'next/link';
import {
    formatOptionalDate,
    getCurrentGroupSummary,
    getHistoryStatusLabel,
    getMemberInitial,
} from '../_lib/member-view';
import type { MemberDetail } from '../_lib/member-api';

type MemberDetailProps = {
    member: MemberDetail;
};

export function MemberDetailView({ member }: MemberDetailProps) {
    return (
        <div className="page-stack">
            <nav aria-label="パンくず" className="breadcrumbs">
                <Link href="/" className="text-link">
                    ホーム
                </Link>
                <span>/</span>
                <Link href="/members" className="text-link">
                    メンバー一覧
                </Link>
                <span>/</span>
                <span>{member.name}</span>
            </nav>

            <section className="profile-hero member-detail-hero">
                <div className="member-detail-hero__copy">
                    <span className="eyebrow">Member Profile</span>
                    <h1>{member.name}</h1>
                    <p>{member.shortBio ?? 'メンバー紹介はこれから登録されます。'}</p>
                    <div className="tag-row">
                        <span className="detail-chip">{member.memberColor ?? 'カラー未登録'}</span>
                        <span className="detail-chip detail-chip__soft">
                            現在所属: {getCurrentGroupSummary(member)}
                        </span>
                    </div>
                </div>

                {member.profileImageUrl ? (
                    <div
                        className="member-detail-hero__image"
                        style={{
                            backgroundImage: `url(${member.profileImageUrl})`,
                        }}
                    />
                ) : (
                    <div
                        className="member-detail-hero__placeholder"
                        style={{
                            backgroundColor: member.memberColorHex ?? 'rgba(255, 228, 239, 0.92)',
                        }}
                    >
                        <span>{getMemberInitial(member.name)}</span>
                    </div>
                )}
            </section>

            <div className="profile-layout">
                <section className="profile-panel member-profile-panel">
                    <div className="section-heading member-profile-panel__heading">
                        <div>
                            <span className="eyebrow">Profile</span>
                            <h2>基本プロフィール</h2>
                        </div>
                        <p>余白を広めに取り、必要な情報だけを落ち着いて確認できる構成にしています。</p>
                    </div>

                    <div className="member-profile-grid">
                        <article className="member-profile-card">
                            <span>誕生日</span>
                            <strong>{formatOptionalDate(member.birthday)}</strong>
                        </article>
                        <article className="member-profile-card">
                            <span>出身地</span>
                            <strong>{member.birthplace ?? '未登録'}</strong>
                        </article>
                        <article className="member-profile-card">
                            <span>メンバーカラー</span>
                            <strong>{member.memberColor ?? '未登録'}</strong>
                        </article>
                    </div>

                    <section className="member-history-section">
                        <span className="eyebrow">History</span>
                        <h2>所属履歴</h2>
                        {member.groupHistory.length > 0 ? (
                            <div className="member-history-list">
                                {member.groupHistory.map((history, index) => (
                                    <article key={`${history.groupId}-${index}`} className="member-history-item">
                                        <div className="member-history-item__line" aria-hidden="true" />
                                        <div className="member-history-item__body">
                                            <div className="member-history-item__header">
                                                <strong>
                                                    {history.groupName}
                                                    {history.generationLabel ? ` ${history.generationLabel}` : ''}
                                                </strong>
                                                {history.isPrimary ? (
                                                    <span className="detail-chip">メイン所属</span>
                                                ) : null}
                                            </div>
                                            <p>
                                                {formatOptionalDate(history.joinedOn)}
                                                {' から '}
                                                {getHistoryStatusLabel(history.leftOn)}
                                            </p>
                                        </div>
                                    </article>
                                ))}
                            </div>
                        ) : (
                            <div className="song-call-empty">
                                <h3>所属履歴はまだ登録されていません</h3>
                                <p>現在所属のみが登録されている状態です。</p>
                            </div>
                        )}
                    </section>
                </section>

                <aside className="side-panel member-side-panel">
                    <span className="eyebrow">Current Groups</span>
                    <h2>現在所属</h2>
                    <div className="member-current-groups">
                        {member.currentGroups.length > 0 ? (
                            member.currentGroups.map((group, index) => (
                                <article key={`${group.groupId}-${index}`} className="song-release-card">
                                    <div className="song-release-card__head">
                                        <strong>{group.groupName}</strong>
                                        {group.generationLabel ? (
                                            <span className="detail-chip detail-chip__soft">
                                                {group.generationLabel}
                                            </span>
                                        ) : null}
                                    </div>
                                    <span>加入日: {formatOptionalDate(group.joinedOn)}</span>
                                </article>
                            ))
                        ) : (
                            <div className="song-call-empty">
                                <p>現在所属情報はまだ登録されていません。</p>
                            </div>
                        )}
                    </div>
                </aside>
            </div>
        </div>
    );
}
