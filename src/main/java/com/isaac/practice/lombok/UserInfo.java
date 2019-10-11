package com.isaac.practice.lombok;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true,chain = true)
public class UserInfo {
    private String name;
    private Integer age;
    private String phone;

    public static void main(String[] args) {
    }
}
