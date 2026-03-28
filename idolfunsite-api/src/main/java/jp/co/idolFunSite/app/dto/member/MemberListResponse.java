package jp.co.idolFunSite.app.dto.member;

import java.time.LocalDate;

/**
 * メンバー一覧表示用のレスポンスDTOです。
 */
public record MemberListResponse(
        Long memberId,
        String name,
        LocalDate birthday,
        String memberColor,
        String memberColorHex,
        CurrentGroupResponse currentGroup,
        String profileImageUrl) {

    /**
     * 現在所属グループです。
     */
    public record CurrentGroupResponse(
            Long groupId,
            String groupName) {
    }
}
