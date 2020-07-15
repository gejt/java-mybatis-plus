package xyz.zao123.java.mybatis;

import org.junit.Test;
import xyz.zao123.java.mybatis.dao.entity.User;

import java.util.Date;

/**
 * @author gejt
 */
public class TestInsert extends TestApplication{
    @Test
    public void testInsert(){
        User user = new User();
        user.setUserName("gejt");
        user.setAge(100);
        user.setEmail("852442493@qq.com");
        user.setMobile("11111111111");
        user.setStatus(1);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }
}
