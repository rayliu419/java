package com.sample.testObjectMapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TestObjectMapper2 {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        BSimilar bSimilar = new BSimilar();
        bSimilar.name = "luru";
        bSimilar.age = 35;
        bSimilar.id = "ray";

        String temp = objectMapper.writeValueAsString(bSimilar);
        System.out.println(temp);

        B b = objectMapper.readValue(temp, B.class);
        System.out.println(b);
    }
}
