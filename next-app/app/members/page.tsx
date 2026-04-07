import type { Metadata } from 'next';
import { MemberList } from '../_components/member-list';
import { fetchMembers } from '../_lib/member-api';

export const metadata: Metadata = {
    title: 'メンバー一覧 | =LOVE Fan Site',
    description: '=LOVE のメンバーを一覧で確認できます。',
};

export const dynamic = 'force-dynamic';

type MembersPageProps = {
    searchParams?: {
        status?: string;
        sort?: string;
    };
};

export default async function MembersPage({ searchParams }: MembersPageProps) {
    const status = searchParams?.status === 'INACTIVE' ? 'INACTIVE' : 'ACTIVE';
    const sort = searchParams?.sort === 'name,asc' ? 'name,asc' : 'displayOrder,asc';
    const memberResult = await Promise.allSettled([fetchMembers(status, sort)]);
    const members = memberResult[0].status === 'fulfilled' ? memberResult[0].value : [];
    const hasError = memberResult[0].status === 'rejected';

    return (
        <div className="page-stack">
            <section className="profile-hero members-hero">
                <div>
                    <span className="eyebrow">=LOVE Members</span>
                    <h1>推しを探しやすい、見やすいメンバー一覧</h1>
                    <p>
                        メンバーカラー、誕生日、現在所属をカード単位で揃え、一覧から詳細へ自然に遷移できる構成にしています。
                    </p>
                </div>
                <div className="profile-badge">
                    <span>表示件数</span>
                    <strong>{members.length} 名</strong>
                    <p>現在は `{status}` / `{sort}` 条件でメンバーを表示しています。</p>
                </div>
            </section>

            <MemberList
                members={members}
                hasError={hasError}
                activeStatus={status}
                activeSort={sort}
            />
        </div>
    );
}
