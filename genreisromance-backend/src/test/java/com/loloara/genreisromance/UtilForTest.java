package com.loloara.genreisromance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class UtilForTest {
    public static String asJsonString(final Object o) {
        try{
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .writeValueAsString(o);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
