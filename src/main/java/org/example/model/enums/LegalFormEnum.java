package org.example.model.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Проверка на организационно-правовая форма
 */
@RequiredArgsConstructor
public enum LegalFormEnum {
    UR("ЮЛ"),
    IP("ИП"),
    FL("ФЛ");
    private final String code;

    public static boolean isExistByCode(String code) {
        return Arrays.stream(values()).anyMatch(
                value -> value.code.equals(code)
        );
    }
}
