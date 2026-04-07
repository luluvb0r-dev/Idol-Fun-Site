import type { Metadata } from 'next';
import type { CSSProperties, ReactNode } from 'react';
import { SiteShell } from './_components/site-shell';
import { fetchSite } from './_lib/site-api';
import './globals.css';

export const metadata: Metadata = {
    title: '=LOVE Fan Site',
    description: '=LOVE ファンサイト向けのモダンなフロントエンド雛形',
};

export default function RootLayout({
    children,
}: {
    children: ReactNode;
}) {
    return <RootLayoutContent>{children}</RootLayoutContent>;
}

async function RootLayoutContent({
    children,
}: {
    children: ReactNode;
}) {
    const site = await fetchSite().catch(() => null);
    const siteName = site?.siteName ?? '=LOVE Fan Site';
    const idolName = site?.idolName ?? '=LOVE';
    const themeStyle = {
        '--primary': site?.theme.primaryColorHex ?? '#e85d95',
        '--bg-strong': site?.theme.secondaryColorHex ?? '#fff1f7',
        '--accent': site?.theme.accentColorHex ?? '#ffc1da',
    } as CSSProperties;

    return (
        <html lang="ja">
            <body style={themeStyle}>
                <SiteShell siteName={siteName} idolName={idolName}>
                    {children}
                </SiteShell>
            </body>
        </html>
    );
}
