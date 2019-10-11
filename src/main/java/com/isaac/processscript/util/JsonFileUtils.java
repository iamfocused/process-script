package com.isaac.processscript.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class JsonFileUtils {
    public static String parseJsonFileToStringValue(String fileName) throws IOException {
        return IOUtils.toString(JsonFileUtils.class.getResourceAsStream("/input_json/" + fileName), "UTF-8");
    }

    public static void main(String[] args) throws Exception{
        System.out.println(parseJsonFileToStringValue("demo.json"));
    }
}
