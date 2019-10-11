package com.isaac.test;

import lombok.var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("My Test")
public class JavaTest {

    @Test
    @DisplayName("lombok test")
    @LocalTest
    public void test() {
        var li = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        li.forEach(integer -> {if(integer == 2) {
            return;
        }
            System.out.println(integer);
        });

    }

    @DisplayName("jsr-223 test")
    @RemoteTest
    public void testJSR223() throws ScriptException, IOException {
        var sem = new ScriptEngineManager();
        assertNotNull(sem);
        sem.getEngineFactories().forEach(factory -> System.out.println(factory.getEngineName() + ", " +  factory.getLanguageName() + "-" + factory.getLanguageVersion()));

        var groovyEngine = sem.getEngineByName("Groovy");
        var classPathResource = new ClassPathResource("Download.groovy");

        var fileReader = new FileReader(classPathResource.getFile());
        groovyEngine.eval(fileReader);
        groovyEngine.eval("def li = ['hello','world'];li.each{println it}");

        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;
        LocalDate date = LocalDate.parse("2019-12-12", dateTimeFormatter);
        LocalDateTime of = LocalDateTime.of(date, LocalTime.MIN);
        System.out.println(of);
        System.out.println(of.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
    }

}
