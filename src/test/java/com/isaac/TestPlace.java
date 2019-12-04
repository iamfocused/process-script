package com.isaac;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestPlace {

    static boolean includeItem(Map map, String items) {
        if (items.equals("all")) {
            return true;
        } else {
            if (Arrays.stream(items.split(",")).anyMatch(map.get("ioitem").toString().substring(0, 4)::equals)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("ioitem","100401");
        String item = "1003";
        System.out.println(includeItem(map,item));
    }

}




