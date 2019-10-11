package com.isaac.processscript.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.List;

public class JavaTest {
    public static void main(String[] args) throws Exception{
        String jsonCarArray =
                "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
        ObjectMapper objectMapper = new ObjectMapper();
        List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});
        System.out.println(listCar);

        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec key = new SecretKeySpec("1".getBytes(), "HmacSHA256");
        hmacSHA256.init(key);

        System.out.println(Arrays.toString(hmacSHA256.doFinal("1".getBytes())));
    }

}
@Data @ToString
class Car {
    private String color;
    private String type;
}
