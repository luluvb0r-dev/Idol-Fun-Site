import { formatDateLabel } from './api-client';
import type { MemberDetail, MemberSummary } from './member-api';
import type { SongSummary } from './song-api';

export function formatOptionalDate(date: string | null | undefined, fallback = '未登録') {
    if (!date) {
        return fallback;
    }

    return formatDateLabel(date);
}

export function getMemberCardGroupName(member: MemberSummary) {
    return member.currentGroup?.groupName ?? '所属情報未登録';
}

export function getCurrentGroupSummary(member: MemberDetail) {
    if (member.currentGroups.length === 0) {
        return '現在所属情報はありません';
    }

    return member.currentGroups
        .map((group) =>
            group.generationLabel ? `${group.groupName} ${group.generationLabel}` : group.groupName,
        )
        .join(' / ');
}

export function getHistoryStatusLabel(leftOn: string | null) {
    return leftOn ? `${formatDateLabel(leftOn)} まで在籍` : '在籍中';
}

export function getMemberInitial(name: string) {
    return name.trim().slice(0, 1) || '?';
}

export function pickFeaturedSongs(songs: SongSummary[], limit = 3) {
    return songs.slice(0, limit);
}
