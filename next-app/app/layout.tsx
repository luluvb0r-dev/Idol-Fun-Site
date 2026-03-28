import type { Metadata } from 'next';
import { SiteShell } from './_components/site-shell';
import './globals.css';

export const metadata: Metadata = {
    title: '=LOVE Fan Site',
    description: '=LOVE ファンサイト向けのモダンなフロントエンド雛形',
};

export default function RootLayout({
    children,
}: {
    children: React.ReactNode;
}) {
    return (
        <html lang="ja">
            <body>
                <SiteShell>{children}</SiteShell>
            </body>
        </html>
    );
}
