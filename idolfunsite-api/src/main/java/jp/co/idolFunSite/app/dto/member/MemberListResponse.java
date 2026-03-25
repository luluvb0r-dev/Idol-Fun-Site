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
        String shortBio) {
}
