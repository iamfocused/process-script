package com.isaac.practice.java8.reduce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Items {
    private String name;
    private Integer amount;
    private BigDecimal price;
}
