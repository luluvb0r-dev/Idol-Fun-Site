import Link from 'next/link';
import { formatOptionalDate, getMemberCardGroupName, getMemberInitial } from '../_lib/member-view';
import type { MemberSummary } from '../_lib/member-api';

type MemberListProps = {
    members: MemberSummary[];
    hasError: boolean;
    activeStatus: string;
    activeSort: string;
};

const filterItems = [
    { value: 'ACTIVE', label: '現役メンバー' },
    { value: 'INACTIVE', label: '卒業メンバー' },
];

const sortItems = [
    { value: 'displayOrder,asc', label: '表示順' },
    { value: 'name,asc', label: '名前順' },
];

export function MemberList({ members, hasError, activeStatus, activeSort }: MemberListProps) {
    if (hasError) {
        return (
            <section className="song-state-panel">
                <span className="eyebrow">API Error</span>
                <h2>メンバーデータを取得できませんでした</h2>
                <p>API サーバーの起動状態、または `API_BASE_URL` の設定値を確認してください。</p>
            </section>
        );
    }

    return (
        <section className="section-block member-section">
            <div className="section-heading member-section__heading">
                <div>
                    <span className="eyebrow">Members</span>
                    <h2>メンバー一覧</h2>
                </div>
                <div className="member-filter-groups">
                    <div className="filter-chip-row" aria-label="メンバー表示切替">
                        {filterItems.map((item) => (
                            <Link
                                key={item.value}
                                href={`/members?status=${item.value}&sort=${encodeURIComponent(activeSort)}`}
                                className={`filter-chip ${activeStatus === item.value ? 'filter-chip__active' : ''}`}
                            >
                                {item.label}
                            </Link>
                        ))}
                    </div>
                    <div className="filter-chip-row" aria-label="メンバー並び順切替">
                        {sortItems.map((item) => (
                            <Link
                                key={item.value}
                                href={`/members?status=${activeStatus}&sort=${encodeURIComponent(item.value)}`}
                                className={`filter-chip ${activeSort === item.value ? 'filter-chip__active' : ''}`}
                            >
                                {item.label}
                            </Link>
                        ))}
                    </div>
                </div>
            </div>

            {members.length === 0 ? (
                <div className="song-call-empty">
                    <span className="eyebrow">No Result</span>
                    <h3>表示できるメンバーが見つかりませんでした</h3>
                    <p>表示条件を切り替えて、もう一度確認してみてください。</p>
                </div>
            ) : (
                <div className="member-card-grid">
                    {members.map((member) => (
                        <Link key={member.memberId} href={`/members/${member.memberId}`} className="member-card-link">
                            <article className="member-card">
                                {member.profileImageUrl ? (
                                    <div
                                        className="member-card__image"
                                        style={{
                                            backgroundImage: `url(${member.profileImageUrl})`,
                                        }}
                                    />
                                ) : (
                                    <div
                                        className="member-card__placeholder"
                                        style={{
                                            backgroundColor: member.memberColorHex ?? 'rgba(255, 228, 239, 0.92)',
                                        }}
                                    >
                                        <span>{getMemberInitial(member.name)}</span>
                                    </div>
                                )}
                                <div className="member-card__body">
                                    <div className="member-card__header">
                                        <div>
                                            <h3>{member.name}</h3>
                                            <p>{getMemberCardGroupName(member)}</p>
                                        </div>
                                        <span className="detail-chip detail-chip__soft">
                                            {member.memberColor ?? 'カラー未登録'}
                                        </span>
                                    </div>
                                    <dl className="member-card__details">
                                        <div>
                                            <dt>誕生日</dt>
                                            <dd>{formatOptionalDate(member.birthday)}</dd>
                                        </div>
                                        <div>
                                            <dt>メンバーカラー</dt>
                                            <dd>{member.memberColorHex ?? '未登録'}</dd>
                                        </div>
                                    </dl>
                                    <div className="song-card__footer">
                                        <span className="text-link">プロフィールを見る</span>
                                    </div>
                                </div>
                            </article>
                        </Link>
                    ))}
                </div>
            )}
        </section>
    );
}
