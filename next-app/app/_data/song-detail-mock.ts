import type { SongCallResult, SongDetail } from '../_lib/song-api';

const mockSongDetails: Record<number, SongDetail> = {
    1: {
        songId: 1,
        title: '=LOVE',
        titleKana: 'イコールラブ',
        description:
            'デビュー期を象徴する代表曲。歌詞とコールの流れを見比べながら追いやすいサンプルとして表示しています。',
        hasCallData: true,
        primaryRelease: {
            releaseId: 1,
            title: '=LOVE',
            releaseDate: '2017-09-06',
            isTitleTrack: true,
        },
        releases: [
            {
                releaseId: 1,
                title: '=LOVE',
                releaseDate: '2017-09-06',
                trackNumber: 1,
                isPrimary: true,
                isTitleTrack: true,
            },
        ],
        originalMembers: [
            { memberId: 1, name: '高松瞳', memberColorHex: '#f48fb1' },
            { memberId: 2, name: '佐々木舞香', memberColorHex: '#b39ddb' },
            { memberId: 3, name: '齊藤なぎさ', memberColorHex: '#ffcc80' },
        ],
    },
};

const mockSongCalls: Record<number, SongCallResult> = {
    1: {
        songId: 1,
        title: '=LOVE',
        blocks: [
            {
                blockId: 101,
                blockType: 'VERSE',
                blockLabel: 'Verse 1A',
                orderNo: 1,
                lines: [
                    {
                        lineId: 1001,
                        lineNo: 1,
                        lyrics: '君に会えたその日から',
                        calls: [
                            {
                                callId: 5001,
                                callTypeCode: 'CHANT',
                                callTypeLabel: '掛け声',
                                callText: 'はい！',
                                style: {
                                    colorHex: '#F06292',
                                    iconKey: 'mic',
                                },
                            },
                        ],
                    },
                    {
                        lineId: 1002,
                        lineNo: 2,
                        lyrics: '胸の奥が熱くなったよ',
                        calls: [
                            {
                                callId: 5002,
                                callTypeCode: 'CLAP',
                                callTypeLabel: 'クラップ',
                                callText: 'パンパン',
                                style: {
                                    colorHex: '#FFCA28',
                                    iconKey: 'hands',
                                },
                            },
                        ],
                    },
                    {
                        lineId: 1003,
                        lineNo: 3,
                        lyrics: 'まっすぐなまなざしに',
                        calls: [
                            {
                                callId: 5003,
                                callTypeCode: 'CHANT',
                                callTypeLabel: '掛け声',
                                callText: 'おい！ おい！',
                                style: {
                                    colorHex: '#F06292',
                                    iconKey: 'mic',
                                },
                            },
                            {
                                callId: 5004,
                                callTypeCode: 'CLAP',
                                callTypeLabel: 'クラップ',
                                callText: 'パン',
                                style: {
                                    colorHex: '#FFCA28',
                                    iconKey: 'hands',
                                },
                            },
                        ],
                    },
                ],
            },
            {
                blockId: 102,
                blockType: 'CHORUS',
                blockLabel: 'Chorus',
                orderNo: 2,
                lines: [
                    {
                        lineId: 1004,
                        lineNo: 1,
                        lyrics: '大好きだよ =LOVE',
                        calls: [
                            {
                                callId: 5005,
                                callTypeCode: 'CHANT',
                                callTypeLabel: '掛け声',
                                callText: 'イコラブ！',
                                style: {
                                    colorHex: '#F06292',
                                    iconKey: 'mic',
                                },
                            },
                        ],
                    },
                    {
                        lineId: 1005,
                        lineNo: 2,
                        lyrics: 'この気持ちは止まらない',
                        calls: [
                            {
                                callId: 5006,
                                callTypeCode: 'CLAP',
                                callTypeLabel: 'クラップ',
                                callText: 'パンパン パン',
                                style: {
                                    colorHex: '#FFCA28',
                                    iconKey: 'hands',
                                },
                            },
                        ],
                    },
                ],
            },
        ],
    },
};

export function getMockSongDetail(songId: number) {
    return mockSongDetails[songId];
}

export function getMockSongCalls(songId: number) {
    return mockSongCalls[songId];
}
