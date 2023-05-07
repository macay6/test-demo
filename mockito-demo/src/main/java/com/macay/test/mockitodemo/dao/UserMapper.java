package com.macay.test.mockitodemo.dao;

import com.macay.test.mockitodemo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

/**
 * @ClassName: UserMapper
 * @Description:
 * @Author: Macay
 * @Date: 2023/5/7 11:27 上午
 */
@Mapper
public interface UserMapper {

    User selectUserById(int id);

    int addUser(User user) throws SQLException;
}
