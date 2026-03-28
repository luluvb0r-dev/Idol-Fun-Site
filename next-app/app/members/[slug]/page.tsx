import Link from 'next/link';
import { notFound } from 'next/navigation';
import { articles, getMemberBySlug } from '../../_data/mock';

export default function MemberProfilePage({
    params,
}: {
    params: { slug: string };
}) {
    const member = getMemberBySlug(params.slug);

    if (!member) {
        notFound();
    }

    const relatedArticles = articles.filter((article) => article.memberSlug === member.slug);

    return (
        <div className="page-stack">
            <section className="profile-hero">
                <div>
                    <span className="eyebrow">メンバープロフィール</span>
                    <h1>{member.name}</h1>
                    <p className="profile-role">{member.role}</p>
                    <p>{member.catchCopy}</p>
                </div>
                <div className="profile-badge">
                    <span>テーマカラー</span>
                    <strong>{member.themeColor}</strong>
                </div>
            </section>

            <section className="profile-layout">
                <article className="profile-panel">
                    <h2>プロフィール概要</h2>
                    <p>{member.profile}</p>
                    <div className="stats-grid">
                        {member.stats.map((stat) => (
                            <div key={stat.label} className="stat-card">
                                <span>{stat.label}</span>
                                <strong>{stat.value}</strong>
                            </div>
                        ))}
                    </div>
                </article>

                <aside className="side-panel">
                    <span className="eyebrow">関連コンテンツ</span>
                    <div className="related-links">
                        {relatedArticles.map((article) => (
                            <Link key={article.id} href={`/posts/${article.id}`} className="related-links__item">
                                <strong>{article.title}</strong>
                                <span>{article.publishedAt}</span>
                            </Link>
                        ))}
                    </div>
                </aside>
            </section>
        </div>
    );
}
