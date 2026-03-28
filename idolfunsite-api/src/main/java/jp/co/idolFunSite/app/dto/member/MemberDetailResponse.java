package jp.co.idolFunSite.app.dto.member;

import java.time.LocalDate;
import java.util.List;

/**
 * メンバー詳細表示用のレスポンスDTOです。
 */
public record MemberDetailResponse(
        Long memberId,
        String name,
        LocalDate birthday,
        String memberColor,
        String memberColorHex,
        String birthplace,
        String shortBio,
        List<CurrentGroupResponse> currentGroups,
        List<GroupHistoryResponse> groupHistory,
        String profileImageUrl) {

    /**
     * 現在所属グループです。
     */
    public record CurrentGroupResponse(
            Long groupId,
            String groupName,
            String generationLabel,
            LocalDate joinedOn) {
    }

    /**
     * 所属履歴です。
     */
    public record GroupHistoryResponse(
            Long groupId,
            String groupName,
            String generationLabel,
            LocalDate joinedOn,
            LocalDate leftOn,
            Boolean isPrimary) {
    }
}
