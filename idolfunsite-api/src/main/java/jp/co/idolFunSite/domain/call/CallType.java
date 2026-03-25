package jp.co.idolFunSite.domain.call;

import java.util.Arrays;

/**
 * コール種別コードを表す列挙です。
 * 表示名や色などの詳細情報は call_type_master を参照し、
 * この enum はコードの揺れ防止と最低限の型安全性を担います。
 */
public enum CallType {
    CHANT("CHANT"),
    CLAP("CLAP"),
    MIX("MIX"),
    JUMP("JUMP"),
    OTHER("OTHER");

    private final String code;

    CallType(String code) {
        this.code = code;
    }

    /**
     * DB や API で扱うコード値を返します。
     *
     * @return コード値
     */
    public String getCode() {
        return code;
    }

    /**
     * コード値から enum を解決します。
     *
     * @param code コード値
     * @return 対応する enum。該当しない場合は OTHER
     */
    public static CallType fromCode(String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equalsIgnoreCase(code))
                .findFirst()
                .orElse(OTHER);
    }
}
