import type { Metadata } from 'next';
import { notFound } from 'next/navigation';
import { SongDetail as SongDetailView } from '../../_components/song-detail';
import { getMockSongCalls, getMockSongDetail } from '../../_data/song-detail-mock';
import { fetchSongCalls, fetchSongDetail } from '../../_lib/song-api';

type SongDetailPageProps = {
    params: {
        songId: string;
    };
};

export const dynamic = 'force-dynamic';

export async function generateMetadata({
    params,
}: SongDetailPageProps): Promise<Metadata> {
    const songId = Number(params.songId);

    if (!Number.isInteger(songId) || songId <= 0) {
        return {
            title: '楽曲詳細 | =LOVE Fan Site',
        };
    }

    try {
        const song = await fetchSongDetail(songId);
        return {
            title: `${song.title} | 楽曲詳細 | =LOVE Fan Site`,
            description: song.description || `${song.title} の歌詞とコールを確認できるページです。`,
        };
    } catch {
        const mockSong = getMockSongDetail(songId);
        return {
            title: `${mockSong?.title ?? '楽曲詳細'} | =LOVE Fan Site`,
            description:
                mockSong?.description ?? '歌詞とコールを見ながら楽曲詳細を確認できるページです。',
        };
    }
}

export default async function SongDetailPage({ params }: SongDetailPageProps) {
    const songId = Number(params.songId);

    if (!Number.isInteger(songId) || songId <= 0) {
        notFound();
    }

    const [songResult, callResult] = await Promise.allSettled([
        fetchSongDetail(songId),
        fetchSongCalls(songId),
    ]);

    const mockSong = getMockSongDetail(songId);
    const mockCalls = getMockSongCalls(songId);
    const song = songResult.status === 'fulfilled' ? songResult.value : mockSong;

    if (!song) {
        notFound();
    }

    const calls =
        callResult.status === 'fulfilled'
            ? callResult.value.blocks
            : mockCalls?.blocks ?? [];

    return (
        <SongDetailView
            song={song}
            callBlocks={calls}
            hasCallError={callResult.status === 'rejected' && Boolean(song.hasCallData)}
            isUsingMockData={songResult.status === 'rejected'}
        />
    );
}
