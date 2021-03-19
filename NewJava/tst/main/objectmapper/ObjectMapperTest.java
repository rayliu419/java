package main.objectmapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

public class ObjectMapperTest {

    @Test
    public void testObjectMapper() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        A a = new A();
        A1 a1 = new main.objectmapper.A1();
        String aString = objectMapper.writeValueAsString(a);
        String a1String = objectMapper.writeValueAsString(a1);
        System.out.println(aString);
        System.out.println(a1String);
        A aa = objectMapper.readValue(aString, A.class);
        System.out.println(aa);
        A1 aa1 = objectMapper.readValue(a1String, main.objectmapper.A1.class);
        System.out.println(aa1);

        // Exception, 父类也不行
        System.out.println("Exception occur!");
        A aaa = objectMapper.readValue(a1String, A.class);
        System.out.println(aaa);
    }

    @Test
    public void testObjectMapper2() throws IOException {
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
