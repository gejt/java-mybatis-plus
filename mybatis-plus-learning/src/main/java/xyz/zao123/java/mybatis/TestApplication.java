package xyz.zao123.java.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.zao123.java.mybatis.dao.entity.User;
import xyz.zao123.java.mybatis.dao.mapper.UserMapper;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestApplication {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect(){
        List<User> users = userMapper.selectList(null);
        users.stream().forEach(System.out::println);
    }
}
