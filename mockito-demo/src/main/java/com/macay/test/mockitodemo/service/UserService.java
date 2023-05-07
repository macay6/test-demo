package com.macay.test.mockitodemo.service;

import com.macay.test.mockitodemo.dao.UserMapper;
import com.macay.test.mockitodemo.entity.User;
import com.macay.test.mockitodemo.util.RedisUtils;
import com.macay.test.mockitodemo.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * @ClassName: UserService
 * @Description:
 * @Author: Macay
 * @Date: 2023/5/7 11:26 上午
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public String getUserAccount(int id){

        User user = userMapper.selectUserById(id);
        String email = user.getEmail();
        String result = email.substring(0, email.indexOf("@"));
        return result;
    };

    public int addUser(User user) {
        int count = 0;
        try {
           count = userMapper.addUser(user);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return count;
    }


    public String transName(int id) {
        User user = userMapper.selectUserById(id);
        String name = StringUtils.firstCharToLowerCase(user.getName());
        return name;
    }

    public String transName2(int id) {
        String name = "";
        User user = userMapper.selectUserById(id);
        if (!user.getName().isEmpty()) {
            name = firstCharToLowerCase(user.getName());
        }
        return name;
    }

    public String getAddress(int id) {
        User user = userMapper.selectUserById(id);
        String address = RedisUtils.getValue(user.getAccount());
        return address;
    }

    private String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }
}
