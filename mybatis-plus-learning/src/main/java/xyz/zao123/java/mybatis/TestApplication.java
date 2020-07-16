package xyz.zao123.java.mybatis;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.zao123.java.mybatis.dao.entity.User;
import xyz.zao123.java.mybatis.dao.mapper.UserMapper;
import xyz.zao123.java.mybatis.service.UserService;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestApplication {
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected UserService userService;

    public static User buildUser(){
        User user = new User();
        user.setUserName("gejt");
        user.setAge(100);
        user.setEmail("852442493@qq.com");
        user.setMobile("11111111111");
        user.setStatus(1);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return user;
    }
}
