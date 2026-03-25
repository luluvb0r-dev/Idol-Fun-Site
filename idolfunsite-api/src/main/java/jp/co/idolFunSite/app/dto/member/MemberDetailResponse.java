package jp.co.idolFunSite.app.dto.member;

import java.time.LocalDate;

/**
 * メンバー詳細表示用のレスポンスDTOです。
 */
public record MemberDetailResponse(
        Long memberId,
        String name,
        LocalDate birthday,
        String memberColor,
        String memberColorHex,
        String shortBio) {
}
