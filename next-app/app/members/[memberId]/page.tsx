import type { Metadata } from 'next';
import { notFound } from 'next/navigation';
import { MemberDetailView } from '../../_components/member-detail';
import { ApiRequestError } from '../../_lib/api-client';
import { fetchMemberDetail } from '../../_lib/member-api';

type MemberDetailPageProps = {
    params: {
        memberId: string;
    };
};

export const dynamic = 'force-dynamic';

export async function generateMetadata({
    params,
}: MemberDetailPageProps): Promise<Metadata> {
    const memberId = Number(params.memberId);

    if (!Number.isInteger(memberId) || memberId <= 0) {
        return {
            title: 'メンバー詳細 | =LOVE Fan Site',
        };
    }

    try {
        const member = await fetchMemberDetail(memberId);
        return {
            title: `${member.name} | メンバー詳細 | =LOVE Fan Site`,
            description: member.shortBio ?? `${member.name} のプロフィールを確認できるページです。`,
        };
    } catch {
        return {
            title: 'メンバー詳細 | =LOVE Fan Site',
        };
    }
}

export default async function MemberDetailPage({ params }: MemberDetailPageProps) {
    const memberId = Number(params.memberId);

    if (!Number.isInteger(memberId) || memberId <= 0) {
        notFound();
    }

    try {
        const member = await fetchMemberDetail(memberId);
        return <MemberDetailView member={member} />;
    } catch (error) {
        if (error instanceof ApiRequestError && error.status === 404) {
            notFound();
        }

        return (
            <section className="song-state-panel">
                <span className="eyebrow">API Error</span>
                <h2>メンバー詳細を取得できませんでした</h2>
                <p>時間をおいて再度お試しいただくか、API サーバーの起動状態を確認してください。</p>
            </section>
        );
    }
}
