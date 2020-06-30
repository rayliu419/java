package com.sample.testObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TestObjectMapper {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        A a = new A();
        A1 a1 = new com.sample.testObjectMapper.A1();
        String aString = objectMapper.writeValueAsString(a);
        String a1String = objectMapper.writeValueAsString(a1);
        System.out.println(aString);
        System.out.println(a1String);
        A aa = objectMapper.readValue(aString, A.class);
        System.out.println(aa);
        A1 aa1 = objectMapper.readValue(a1String, com.sample.testObjectMapper.A1.class);
        System.out.println(aa1);

        // Exception, 父类也不行
        System.out.println("Exception occur!");
        A aaa = objectMapper.readValue(a1String, A.class);
        System.out.println(aaa);
    }
}
