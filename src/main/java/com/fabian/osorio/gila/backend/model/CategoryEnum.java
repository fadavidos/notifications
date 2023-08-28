package com.fabian.osorio.gila.backend.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum CategoryEnum {
    SPORTS,
    FINANCE,
    FILMS;

    public static String validValues() {
        return Arrays.stream(CategoryEnum.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
}
