import Link from 'next/link';
import { articles, members } from './_data/mock';

export default function HomePage() {
    const featuredMember = members[0];

    return (
        <div className="page-stack">
            <section className="hero-panel">
                <div className="hero-copy">
                    <span className="eyebrow">=LOVE Fan Experience</span>
                    <h1>=LOVE の魅力へ自然につながる、やわらかく見やすい一覧画面。</h1>
                    <p>
                        ピンク基調のトーンで、記事一覧から詳細、さらにメンバープロフィールへ自然に移動できる骨組みを先に整えています。
                    </p>
                </div>
                <div className="hero-card">
                    <p className="hero-card__label">注目メンバー</p>
                    <h2>{featuredMember.name}</h2>
                    <p>{featuredMember.catchCopy}</p>
                    <Link href={`/members/${featuredMember.slug}`} className="primary-link">
                        プロフィールを見る
                    </Link>
                </div>
            </section>

            <section className="section-block">
                <div className="section-heading">
                    <div>
                        <span className="eyebrow">Article List</span>
                        <h2>一覧画面の雛形</h2>
                    </div>
                    <p>カード単位で情報密度を揃え、詳細遷移をわかりやすくしたレイアウトです。</p>
                </div>
                <div className="card-grid">
                    {articles.map((article) => (
                        <article key={article.id} className="content-card">
                            <div className="content-card__meta">
                                <span>{article.category}</span>
                                <span>{article.publishedAt}</span>
                            </div>
                            <h3>{article.title}</h3>
                            <p>{article.lead}</p>
                            <div className="tag-row">
                                {article.tags.map((tag) => (
                                    <span key={tag} className="tag-chip">
                                        {tag}
                                    </span>
                                ))}
                            </div>
                            <div className="content-card__footer">
                                <span>{article.memberName}</span>
                                <Link href={`/posts/${article.id}`} className="text-link">
                                    詳細へ
                                </Link>
                            </div>
                        </article>
                    ))}
                </div>
            </section>
        </div>
    );
}
