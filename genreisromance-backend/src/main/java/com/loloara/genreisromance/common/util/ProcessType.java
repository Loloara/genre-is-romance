package com.loloara.genreisromance.common.util;

import java.util.Arrays;

public enum ProcessType {

    SEARCHING("SEARCHING"), SEARCHED("SEARCHED"), PROPOSE("PROPOSE"), PAYMENT("PAYMENT"), SUCCESS("SUCCESS");

    private String value;

    private ProcessType(String value) {
        this.value = value;
    }

    public static ProcessType fromInteger(int value) {
        for(ProcessType process : values()) {
            if(process.ordinal() == value) {
                return process;
            }
        }

        throw new IllegalArgumentException(
                "Unknown enum type " + value + ", Allowed values are "  + Arrays.toString(values()));
    }
}

    /* ToDo
        add ProcessType for admin ex) ManToWoman, WomanToMan
    */
