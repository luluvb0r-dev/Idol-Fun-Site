export type RawSearchParams = Record<string, string | string[] | undefined>;

export type SongSearchFilters = {
    keyword: string;
    releaseId: string;
    isTitleTrack: boolean;
    page: number;
};

export const SONG_PAGE_SIZE = 20;
export const SONG_DEFAULT_SORT = 'releaseDate,desc';

function getFirstValue(value: string | string[] | undefined) {
    if (Array.isArray(value)) {
        return value[0] ?? '';
    }

    return value ?? '';
}

export function normalizeSongSearchParams(searchParams: RawSearchParams): SongSearchFilters {
    const keyword = getFirstValue(searchParams.keyword).trim();
    const releaseId = getFirstValue(searchParams.releaseId).trim();
    const isTitleTrackValue = getFirstValue(searchParams.isTitleTrack).trim().toLowerCase();
    const pageValue = Number.parseInt(getFirstValue(searchParams.page), 10);

    return {
        keyword,
        releaseId: /^\d+$/.test(releaseId) ? releaseId : '',
        isTitleTrack: ['1', 'true', 'on'].includes(isTitleTrackValue),
        page: Number.isFinite(pageValue) && pageValue >= 0 ? pageValue : 0,
    };
}

export function buildSongSearchQuery(filters: SongSearchFilters) {
    const query = new URLSearchParams();

    query.set('page', String(filters.page));
    query.set('size', String(SONG_PAGE_SIZE));
    query.set('sort', SONG_DEFAULT_SORT);

    if (filters.keyword) {
        query.set('keyword', filters.keyword);
    }

    if (filters.releaseId) {
        query.set('releaseId', filters.releaseId);
    }

    if (filters.isTitleTrack) {
        query.set('isTitleTrack', 'true');
    }

    return query;
}
