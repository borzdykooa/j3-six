package com.borzdykooa.j3six.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genre {
    SCIENCE_FICTION("Science fiction"),
    POETRY("Poetry"),
    DETECTIVE("Detective");

    private String value;

    public static Genre getByValue(String value) {
        for (Genre genre : Genre.values()) {
            if (value.equalsIgnoreCase(genre.value)) {
                return genre;
            }
        }
        return null;
    }
}
