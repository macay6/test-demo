package com.macay.test.mockitodemo.service;

import com.macay.test.mockitodemo.dao.UserMapper;
import com.macay.test.mockitodemo.entity.User;
import com.macay.test.mockitodemo.util.RedisUtils;
import com.macay.test.mockitodemo.util.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.transform.Result;
import java.lang.reflect.Method;
import java.sql.SQLException;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Test
    void getUserAccountTestThenReturn() {
        User user = new User();
        user.setId(1);
        user.setEmail("1234556@qq.com");
        user.setName("lisi");
        // mock对象指定行为后返回指定值
        Mockito.when(userMapper.selectUserById(1)).thenReturn(user);
        // 断言返回值
        Assertions.assertEquals(userService.getUserAccount(1), "1234556");
    }

    @Test
    void testAddUserTestThenThrow() throws Exception {
        User user = new User();
        user.setId(1);
        user.setEmail("1234556@qq.com");
        user.setName("lisi");
        // Mock对象在触发指定行为后抛出指定异常
        Mockito.when(userMapper.addUser(user)).thenThrow(new SQLException());
        try {
            userService.addUser(user);
        } catch (Exception exception) {
            // 断言异常类型
            Assertions.assertTrue(exception instanceof SQLException);
        }
    }


    @Test
    void testTransNameTestStaticNormal(){
        User user = new User();
        user.setId(1);
        user.setEmail("1234556@qq.com");
        user.setName("Lisi");
        Mockito.when(userMapper.selectUserById(1)).thenReturn(user);
        // 我们可以使用mock静态方法然后执行thenCallRealMethod（），效果一样，但是比较鸡肋
        // MockedStatic<StringUtils> stringUtilsMocked = Mockito.mockStatic(StringUtils.class);
        // stringUtilsMocked.when(()-> StringUtils.firstCharToLowerCase(user.getName())).thenCallRealMethod();
        Assertions.assertEquals(userService.transName(1), "lisi");

    }

    @Test
    void testGetAddressTestStatic(){
        User user = new User();
        user.setId(1);
        user.setEmail("1234556@qq.com");
        user.setName("lisi");
        user.setAccount("123456");
        Mockito.when(userMapper.selectUserById(1)).thenReturn(user);
        // mock静态方法
        MockedStatic<RedisUtils> redisUtilsMocked = Mockito.mockStatic(RedisUtils.class);
        redisUtilsMocked.when(()-> RedisUtils.getValue(user.getAccount())).thenReturn("a");
        Assertions.assertEquals(userService.getAddress(1), "a");
        // 必须关闭 static mock 对象
        redisUtilsMocked.close();
    }

    @Test
    void testFirstCharToLowerCase() throws Exception {
        // 获取字节码对象
        Class clazz = UserService.class;

        // 创建学生对象
        Object userService = clazz.newInstance();
        // 暴力反射获取方法
        Method method = clazz.getDeclaredMethod("firstCharToLowerCase", String.class);
        // 让jvm不检查权限
        method.setAccessible(true);
        // 执行方法
        String result = (String) method.invoke(userService, "Lisi");
        Assertions.assertEquals(result, "lisi");
    }

    @Test
    void testTransName2ToPrivate() throws SQLException {
        MockedStatic<RedisUtils> redisUtilsMocked = Mockito.mockStatic(RedisUtils.class);
        User user = new User();
        user.setId(1);
        user.setEmail("1234556@qq.com");
        user.setName("Lisi");
        Mockito.when(userMapper.selectUserById(1)).thenReturn(user);
        // 构造的条件name不为空，可以正常进入private方法
        Assertions.assertEquals(userService.transName2(1), "lisi");
        // 要求必须是 `mock` 对象, 这里userService会报错
        Mockito.verify(userMapper).selectUserById(1);
    }
}
