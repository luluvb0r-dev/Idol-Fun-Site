import Link from 'next/link';
import type { ReactNode } from 'react';

const navItems = [
    { href: '/', label: 'トップ' },
    { href: '/songs', label: '楽曲一覧' },
    { href: '/members', label: 'メンバー' },
];

type SiteShellProps = {
    children: ReactNode;
    siteName: string;
    idolName: string;
};

export function SiteShell({ children, siteName, idolName }: SiteShellProps) {
    return (
        <div className="site-frame">
            <div className="ambient ambient-left" />
            <div className="ambient ambient-right" />
            <header className="site-header">
                <div className="site-header__inner">
                    <Link href="/" className="brand-mark">
                        <span className="brand-mark__eyebrow">{idolName} Fan Site</span>
                        <span className="brand-mark__title">{siteName}</span>
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
                <p>{idolName} の楽曲とメンバーを見やすく追えるファンサイト</p>
            </footer>
        </div>
    );
}
