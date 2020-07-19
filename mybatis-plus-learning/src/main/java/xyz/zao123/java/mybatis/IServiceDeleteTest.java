package xyz.zao123.java.mybatis;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.zao123.java.mybatis.dao.entity.User;
import xyz.zao123.java.mybatis.service.UserService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gejt
 */
public class IServiceDeleteTest extends TestApplication {

    @Autowired
    protected UserService userService;

    /**
     * 按照id删除
     */
    @Test
    public void removeById() {
        Assert.assertTrue(userService.removeById(30L));
    }

    /**
     * 查询符合条件的记录
     */
    @Test
    public void remove() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 30L);
        Assert.assertTrue(userService.remove(queryWrapper));
    }

    /**
     * 按照id批量删除
     */
    @Test
    public void removeByIds() {
        Assert.assertTrue(userService.removeByIds(Arrays.asList(30,31)));
    }

    /**
     * 删除符合map参数的数据
     */
    @Test
    public void removeByMap() {
        Map<String, Object> map = new HashMap<>(5);
        map.put("id",30);
        map.put("user_name","gejt123");
        Assert.assertTrue(userService.removeByMap(map));
    }
}
