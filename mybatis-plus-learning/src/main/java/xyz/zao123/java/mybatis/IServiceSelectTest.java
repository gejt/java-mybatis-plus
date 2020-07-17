package xyz.zao123.java.mybatis;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Assert;
import org.junit.Test;
import xyz.zao123.java.mybatis.dao.entity.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gejt
 */
public class IServiceSelectTest extends TestApplication {

    /**
     * 按照id查询
     */
    @Test
    public void getById() {
        Assert.assertNotNull(userService.getById(5L));
    }


    /**
     * 查询一条数据
     * 如果查询到多条会报错
     */
    @Test
    public void getOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 5);
        Assert.assertNotNull(userService.getOne(queryWrapper));
    }

    /**
     * 查询一条记录
     * 查询到多条记录 false-不抛出异常 true-抛出异常
     */
    @Test
    public void getOneThrowEx() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", "gejt");
        Assert.assertNotNull(userService.getOne(queryWrapper, true));
    }

    /**
     * 查询列表 map查询条件
     */
    @Test
    public void listByMap() {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", 5);
        Assert.assertEquals(1, userService.listByMap(params).size());
    }

    /**
     * 查询列表 idList查询条件
     */
    @Test
    public void listByIds() {
        Assert.assertEquals(1, userService.listByIds(Collections.singletonList(5)));
    }

    /**
     * 查询一条记录 map结果
     */
    @Test
    public void getMap() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", "gejt");
        Map<String, Object> map = userService.getMap(queryWrapper);
        Assert.assertNotNull(map);
    }

    /**
     * 查询总的记录条数
     */
    @Test
    public void count() {
        Assert.assertTrue(userService.count() > 0);
    }

    /**
     * 查询符合条件的记录数
     */
    @Test
    public void countWrapper() {
        QueryWrapper queryWrapper = new QueryWrapper<User>().eq("user_name", "gejt");
        Assert.assertTrue(userService.count(queryWrapper) > 0);
    }

    /**
     * 查询所有记录
     */
    @Test
    public void list() {
        Assert.assertTrue(userService.list().size() > 0);
    }

    /**
     * 查询符合条件的记录
     */
    @Test
    public void listQueryWrapper() {
        QueryWrapper queryWrapper = new QueryWrapper<User>().eq("user_name", "gejt");
        Assert.assertTrue(userService.list(queryWrapper).size() > 0);
    }

    /**
     * 查询所有记录 List<Map>结果
     */
    @Test
    public void listMaps() {
        Assert.assertTrue(userService.listMaps().size() > 0);
    }

    /**
     * 查询符合条件记录 List<Map>结果
     */
    @Test
    public void listMapsQueryWraper() {
        QueryWrapper queryWrapper = new QueryWrapper<User>().eq("user_name", "gejt");
        Assert.assertTrue(userService.listMaps(queryWrapper).size() > 0);
    }

    /**
     * 查询一条记录 只返回第一个字段
     */
    @Test
    public void getObj() {
        Long userId = userService.getObj(new QueryWrapper<User>().eq("user_name", "gejt"), id -> Long.valueOf(id.toString()));
        Assert.assertEquals(Long.valueOf(5),userId);
    }

    /**
     * 查询所有记录 只返回第一个字段
     */
    @Test
    public void listObjs() {
        List<Object> objects = userService.listObjs();
        Assert.assertTrue(objects.size() > 0);
    }

    /**
     * 查询符合条件的记录 只返回第一个字段
     */
    @Test
    public void listObjsQueryWrapper() {
        QueryWrapper queryWrapper = new QueryWrapper<User>().eq("user_name", "gejt");
        Assert.assertTrue(userService.listObjs(queryWrapper).size() > 0);
    }

    /**
     * 查询所有的记录 只返回第一个字段 讲这一个值mapper处理
     */
    @Test
    public void listObjsMapperToOther() {
        List<Long> objects = userService.listObjs(id -> {
            return Long.valueOf(id.toString());
        });
        System.out.println(objects);
        Assert.assertTrue(objects.size() > 0);
    }

    /**
     * 查询符合条件的记录 只返回第一个字段 讲这一个值mapper处理
     */
    @Test
    public void listObjsQueryWrapperMapperToOther() {
        List<Long> objects = userService.listObjs(new QueryWrapper<User>().eq("user_name", "gejt"), id -> {
            return Long.valueOf(id.toString());
        });
        System.out.println(objects);
        Assert.assertTrue(objects.size() > 0);
    }

    /**
     * 查询符合条件的记录 QueryChainWrapper
     */
    @Test
    public void query() {
        List<User> list = userService.query().eq("id", 5L)
                .eq("user_name", "gejt")
                .list();
        Assert.assertTrue(list.size() > 0);
    }

    /**
     * 查询符合条件的记录 LambdaQueryChainWrapper
     */
    @Test
    public void lambdaQuery() {
        List<User> list = userService.lambdaQuery().eq(User::getId, 5L)
                .eq(User::getUserName, "gejt")
                .list();
        Assert.assertTrue(list.size() > 0);
    }


    /**
     * 分页查询所有记录 返回 List<Map>结果集
     */
    @Test
    public void pageMaps() {
        IPage<Map<String, Object>> page = new Page<>(1, 10);
        IPage<Map<String, Object>> mapIPage = userService.pageMaps(page);
        Assert.assertTrue(mapIPage.getTotal() == 28);
    }

    /**
     * 分页查询所有记录QueryWrapper 返回 List<Map>结果集
     */
    @Test
    public void pageMapsQueryWrapper() {
        IPage<Map<String, Object>> page = new Page<>(1, 10);
        IPage<Map<String, Object>> mapIPage = userService.pageMaps(page
                , new QueryWrapper<User>().eq("user_name", "gejt"));
        Assert.assertTrue(mapIPage.getTotal() > 0);
    }

    /**
     * 分页查询所有数据
     */
    @Test
    public void page() {
        IPage<User> page = new Page<>(1,10);
        IPage<User> pageUser = userService.page(page);
        Assert.assertTrue(pageUser.getTotal() > 0);
    }

    /**
     * 分页查询符合条件所有数据
     */
    @Test
    public void pageQueryWrapper() {
        IPage<User> page = new Page<>(1,10);
        IPage<User> pageUser = userService.page(page,new QueryWrapper<User>().eq("user_name","gejt"));
        Assert.assertTrue(pageUser.getTotal() > 0);
    }
}
