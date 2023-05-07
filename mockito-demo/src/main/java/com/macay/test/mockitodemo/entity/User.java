package com.macay.test.mockitodemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: User
 * @Description:
 * @Author: Macay
 * @Date: 2023/5/7 11:28 上午
 */
@Data
public class User {

    private int id;

    private String name;

    private String email;

    private String account;
}
