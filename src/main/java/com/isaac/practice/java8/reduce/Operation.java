package com.isaac.practice.java8.reduce;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Operation {
    private List<Items> items;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        items = new ArrayList<>();
        items.add(new Items("铅笔", 12, new BigDecimal("5.56")));
        items.add(new Items("铅笔", 10, new BigDecimal("1.111")));
        items.add(new Items("橡皮", 10, new BigDecimal("1.114")));
        items.add(new Items("橡皮", 10, new BigDecimal("1.111")));
    }

    @Test
    public void testSumItem() {
//        BigDecimal sum = items.stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getAmount())).setScale(1, BigDecimal.ROUND_HALF_UP)).reduce(BigDecimal.ZERO, BigDecimal::add);
//        System.out.println(sum.toString());
        Map<String, List<Items>> groupByName = items.stream().collect(Collectors.groupingBy(Items::getName));
        Map<String, BigDecimal> priceMap = new HashMap<>();//key:name value:price
        groupByName.forEach((k, v) ->
                priceMap.put(
                        k,
                        Optional.ofNullable(
                                v.stream().map(item ->
                                        item.getPrice().multiply(BigDecimal.valueOf(item.getAmount()))).reduce(BigDecimal.ZERO, BigDecimal::add)
                        ).map(bd ->
                                bd.setScale(2, BigDecimal.ROUND_HALF_UP)
                        ).orElse(BigDecimal.ZERO)
                )
        );
        System.out.println(priceMap);
    }


    @Test
    public void testScale() {
        BigDecimal result = BigDecimal.valueOf(22.25).setScale(1, BigDecimal.ROUND_HALF_UP);
        System.out.println(result);
        expectedException.expect(ArithmeticException.class);
        System.out.println("You can't see");
    }

    @After
    public void after() {
        System.out.println("Can you see?");
    }
}
