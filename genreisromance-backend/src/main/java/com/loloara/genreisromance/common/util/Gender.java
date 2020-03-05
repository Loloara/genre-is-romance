package com.loloara.genreisromance.common.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Gender {

    MALE("MALE"), FEMALE("FEMALE");

    private String value;

    private Gender(String value) {
        this.value = value;
    }

    @JsonValue
    public String jsonValue() {
        return value;
    }

    @JsonCreator
    public static Gender fromValue(String value) {
        for(Gender gender : values()) {
            if(gender.value.equalsIgnoreCase(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException(
                "Unknown enum type " + value + ", Allowed values are "  + Arrays.toString(values()));
    }
}
