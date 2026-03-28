import { buildSongSearchQuery, type SongSearchFilters } from './song-search';

type ApiEnvelope<T> = {
    data: T;
    meta: {
        requestId: string;
        version: string;
    };
    errors: Array<{
        code: string;
        message: string;
    }>;
};

type ItemListResponse<T> = {
    items: T[];
};

type PagedItemListResponse<T> = {
    items: T[];
    pagination: {
        page: number;
        size: number;
        totalElements: number;
        totalPages: number;
    };
};

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

const API_BASE_URL =
    process.env.API_BASE_URL ??
    process.env.NEXT_PUBLIC_API_BASE_URL ??
    'http://localhost:8080/api/v1';

const SITE_KEY = process.env.NEXT_PUBLIC_SITE_KEY ?? 'equal-love';

async function requestApi<T>(path: string, query?: URLSearchParams) {
    const url = new URL(path, API_BASE_URL.endsWith('/') ? API_BASE_URL : `${API_BASE_URL}/`);

    if (query && Array.from(query.keys()).length > 0) {
        url.search = query.toString();
    }

    const response = await fetch(url.toString(), {
        cache: 'no-store',
        headers: {
            Accept: 'application/json',
        },
    });

    if (!response.ok) {
        throw new Error(`API request failed: ${response.status}`);
    }

    const payload = (await response.json()) as ApiEnvelope<T>;

    if (payload.errors.length > 0) {
        throw new Error(payload.errors.map((error) => error.message).join(', '));
    }

    return payload.data;
}

export async function fetchSongs(filters: SongSearchFilters): Promise<SongListResult> {
    return requestApi<PagedItemListResponse<SongSummary>>(
        `sites/${SITE_KEY}/songs`,
        buildSongSearchQuery(filters),
    );
}

export async function fetchReleases(): Promise<ReleaseSummary[]> {
    const data = await requestApi<ItemListResponse<ReleaseSummary>>(`sites/${SITE_KEY}/releases`);
    return data.items;
}

export function formatSongReleaseDate(date: string) {
    return new Intl.DateTimeFormat('ja-JP', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
    }).format(new Date(date));
}
