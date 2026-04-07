import { requestApi, SITE_KEY, type ItemListResponse } from './api-client';

export type MemberSummary = {
    memberId: number;
    name: string;
    birthday: string;
    memberColor: string | null;
    memberColorHex: string | null;
    currentGroup: {
        groupId: number;
        groupName: string;
    } | null;
    profileImageUrl: string | null;
};

export type MemberDetail = {
    memberId: number;
    name: string;
    birthday: string;
    memberColor: string | null;
    memberColorHex: string | null;
    birthplace: string | null;
    shortBio: string | null;
    currentGroups: Array<{
        groupId: number;
        groupName: string;
        generationLabel: string | null;
        joinedOn: string | null;
    }>;
    groupHistory: Array<{
        groupId: number;
        groupName: string;
        generationLabel: string | null;
        joinedOn: string | null;
        leftOn: string | null;
        isPrimary: boolean;
    }>;
    profileImageUrl: string | null;
};

export async function fetchMembers(status = 'ACTIVE', sort = 'displayOrder,asc'): Promise<MemberSummary[]> {
    const query = new URLSearchParams();

    if (status) {
        query.set('status', status);
    }

    if (sort) {
        query.set('sort', sort);
    }

    const data = await requestApi<ItemListResponse<MemberSummary>>(`sites/${SITE_KEY}/members`, query);
    return data.items;
}

export async function fetchMemberDetail(memberId: number): Promise<MemberDetail> {
    return requestApi<MemberDetail>(`sites/${SITE_KEY}/members/${memberId}`);
}
