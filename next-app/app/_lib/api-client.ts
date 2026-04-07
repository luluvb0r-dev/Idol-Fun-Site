export type ApiEnvelope<T> = {
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

export type ItemListResponse<T> = {
    items: T[];
};

export type PagedItemListResponse<T> = {
    items: T[];
    pagination: {
        page: number;
        size: number;
        totalElements: number;
        totalPages: number;
    };
};

export class ApiRequestError extends Error {
    status: number;

    constructor(status: number, message: string) {
        super(message);
        this.name = 'ApiRequestError';
        this.status = status;
    }
}

export const API_BASE_URL =
    process.env.API_BASE_URL ??
    process.env.NEXT_PUBLIC_API_BASE_URL ??
    'http://localhost:8080/api/v1';

export const SITE_KEY = process.env.NEXT_PUBLIC_SITE_KEY ?? 'equal-love';

export async function requestApi<T>(path: string, query?: URLSearchParams) {
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
        throw new ApiRequestError(response.status, `API request failed: ${response.status}`);
    }

    const payload = (await response.json()) as ApiEnvelope<T>;

    if (payload.errors.length > 0) {
        throw new ApiRequestError(
            response.status,
            payload.errors.map((error) => error.message).join(', '),
        );
    }

    return payload.data;
}

export function formatDateLabel(date: string) {
    return new Intl.DateTimeFormat('ja-JP', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
    }).format(new Date(date));
}
