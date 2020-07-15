package xyz.zao123.java.mybatis;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.zao123.java.mybatis.dao.mapper.UserMapper;
import xyz.zao123.java.mybatis.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestApplication {
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected UserService userService;
}
