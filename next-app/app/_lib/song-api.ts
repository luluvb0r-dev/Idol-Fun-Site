import {
    formatDateLabel,
    requestApi,
    SITE_KEY,
    type ItemListResponse,
    type PagedItemListResponse,
} from './api-client';
import { buildSongSearchQuery, type SongSearchFilters } from './song-search';

export type SongSummary = {
    songId: number;
    title: string;
    titleKana: string;
    primaryRelease: {
        releaseId: number;
        title: string;
        releaseDate: string;
        isTitleTrack: boolean;
    } | null;
    originalMembers: Array<{
        memberId: number;
        name: string;
    }>;
    hasCallData: boolean;
};

export type SongDetail = {
    songId: number;
    title: string;
    titleKana: string;
    description: string;
    hasCallData: boolean;
    primaryRelease: {
        releaseId: number;
        title: string;
        releaseDate: string;
        isTitleTrack: boolean;
    } | null;
    releases: Array<{
        releaseId: number;
        title: string;
        releaseDate: string;
        trackNumber: number | null;
        isPrimary: boolean;
        isTitleTrack: boolean;
    }>;
    originalMembers: Array<{
        memberId: number;
        name: string;
        memberColorHex: string | null;
    }>;
};

export type SongCallItem = {
    callId: number;
    callTypeCode: string;
    callTypeLabel: string;
    callText: string;
    style: {
        colorHex: string | null;
        iconKey: string | null;
    } | null;
};

export type SongCallLine = {
    lineId: number;
    lineNo: number;
    lyrics: string;
    calls: SongCallItem[];
};

export type SongCallBlock = {
    blockId: number;
    blockType: string;
    blockLabel: string;
    orderNo: number;
    lines: SongCallLine[];
};

export type SongCallResult = {
    songId: number;
    title: string;
    blocks: SongCallBlock[];
};

export type ReleaseSummary = {
    releaseId: number;
    title: string;
    releaseDate: string;
    releaseType: string;
    jacketImageUrl: string | null;
};

export type SongListResult = {
    items: SongSummary[];
    pagination: {
        page: number;
        size: number;
        totalElements: number;
        totalPages: number;
    };
};

export type CallTypeMaster = {
    callTypeCode: string;
    callTypeLabel: string;
    colorHex: string | null;
    iconKey: string | null;
};

export async function fetchSongs(filters: SongSearchFilters): Promise<SongListResult> {
    return requestApi<PagedItemListResponse<SongSummary>>(
        `sites/${SITE_KEY}/songs`,
        buildSongSearchQuery(filters),
    );
}

export async function fetchSongDetail(songId: number): Promise<SongDetail> {
    return requestApi<SongDetail>(`sites/${SITE_KEY}/songs/${songId}`);
}

export async function fetchSongCalls(songId: number): Promise<SongCallResult> {
    return requestApi<SongCallResult>(`sites/${SITE_KEY}/songs/${songId}/calls`);
}

export async function fetchReleases(): Promise<ReleaseSummary[]> {
    const data = await requestApi<ItemListResponse<ReleaseSummary>>(`sites/${SITE_KEY}/releases`);
    return data.items;
}

export async function fetchCallTypes(): Promise<CallTypeMaster[]> {
    const data = await requestApi<ItemListResponse<CallTypeMaster>>(
        `sites/${SITE_KEY}/masters/call-types`,
    );
    return data.items;
}

export function formatSongReleaseDate(date: string) {
    return formatDateLabel(date);
}
