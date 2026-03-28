import Link from 'next/link';
import { notFound } from 'next/navigation';
import { getArticleById } from '../../_data/mock';

export default function PostDetailPage({
    params,
}: {
    params: { id: string };
}) {
    const article = getArticleById(params.id);

    if (!article) {
        notFound();
    }

    return (
        <div className="page-stack">
            <section className="detail-hero">
                <div className="detail-hero__meta">
                    <span>{article.category}</span>
                    <span>{article.publishedAt}</span>
                </div>
                <h1>{article.title}</h1>
                <p>{article.lead}</p>
                <div className="tag-row">
                    {article.tags.map((tag) => (
                        <span key={tag} className="tag-chip">
                            {tag}
                        </span>
                    ))}
                </div>
            </section>

            <section className="detail-layout">
                <article className="prose-panel">
                    {article.body.map((paragraph) => (
                        <p key={paragraph}>{paragraph}</p>
                    ))}
                </article>

                <aside className="side-panel">
                    <span className="eyebrow">プロフィール導線</span>
                    <h2>{article.memberName}</h2>
                    <p>詳細画面からメンバー画面へつながる導線をここに配置しています。</p>
                    <Link href={`/members/${article.memberSlug}`} className="primary-link">
                        メンバー画面へ
                    </Link>
                </aside>
            </section>
        </div>
    );
}
