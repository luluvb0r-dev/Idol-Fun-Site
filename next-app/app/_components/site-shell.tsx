import Link from 'next/link';
import type { ReactNode } from 'react';

const navItems = [
    { href: '/', label: '一覧' },
    { href: '/posts/spring-tour-preview', label: '詳細' },
    { href: '/members/hana', label: 'メンバー' },
];

export function SiteShell({ children }: { children: ReactNode }) {
    return (
        <div className="site-frame">
            <div className="ambient ambient-left" />
            <div className="ambient ambient-right" />
            <header className="site-header">
                <div className="site-header__inner">
                    <Link href="/" className="brand-mark">
                        <span className="brand-mark__eyebrow">=LOVE Fan Site</span>
                        <span className="brand-mark__title">Pink Mood Archive</span>
                    </Link>
                    <nav className="site-nav" aria-label="主要ナビゲーション">
                        {navItems.map((item) => (
                            <Link key={item.href} href={item.href} className="site-nav__link">
                                {item.label}
                            </Link>
                        ))}
                    </nav>
                </div>
            </header>
            <main className="site-main">{children}</main>
            <footer className="site-footer">
                <p>Step 6: Next.js App Router の画面骨組み</p>
            </footer>
        </div>
    );
}
