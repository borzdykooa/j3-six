package com.borzdykooa.j3six.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Genre {
    SCIENCE_FICTION("Science fiction"),
    POETRY("Poetry"),
    DETECTIVE("Detective");

    private String value;

    public static Genre getByValue(String value) {
        return Arrays.stream(values())
                .filter(genre -> genre.value.equals(value))
                .findFirst()
                .orElse(null);
    }
}
