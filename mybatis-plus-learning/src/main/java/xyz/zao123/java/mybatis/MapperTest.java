package xyz.zao123.java.mybatis;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Assert;
import org.junit.Test;
import xyz.zao123.java.mybatis.dao.entity.User;
import xyz.zao123.java.mybatis.dto.UserDto;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gejt
 */
public class MapperTest extends TestApplication {


    /**
     * 新增一条数据
     */
    @Test
    public void insert() {
        User user = buildUser();
        int insert = userMapper.insert(user);
        Assert.assertEquals(1, insert);
    }


    /**
     * 按照id删除一条数据
     */
    @Test
    public void deleteById() {
        Assert.assertEquals(1, userMapper.deleteById(1));
    }


    /**
     * 删除满足查询条件的记录
     */
    @Test
    public void delete() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 2);
        Assert.assertEquals(1, userMapper.delete(wrapper));
    }

    /**
     * 批量id删除
     */
    @Test
    public void deleteBatchIds() {
        Assert.assertNotNull(userMapper.deleteBatchIds(Arrays.asList(1, 2, 3)));
    }


    /**
     * 按照 map 里的值删除
     */
    @Test
    public void deleteByMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        params.put("user_name", "gejt");
        Assert.assertNotNull(userMapper.deleteByMap(params));
    }

    /**
     * 按照id更新
     */
    @Test
    public void updateById() {
        User user = buildUser();
        user.setId(5L);
        int update = userMapper.updateById(user);
        Assert.assertEquals(1, update);
    }


    /**
     * 更新符合条件的所有记录
     */
    @Test
    public void update() {
        User user = buildUser();
        user.setId(5L);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "gejt1111")
                .eq("id", 5);
        int update = userMapper.update(user, wrapper);

        Assert.assertNotNull(update);
    }

    /**
     * 按照id查询一条记录
     */
    @Test
    public void selectById() {
        Assert.assertNotNull(userMapper.selectById(5));
    }

    /**
     * 查询符合条件的一条记录
     */
    @Test
    public void selectOne() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "gejt");
        Assert.assertNotNull(userMapper.selectOne(wrapper));
    }


    /**
     * 查询符合条件的记录数量
     */
    @Test
    public void selectCount() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "gejt");
        Assert.assertEquals(Integer.valueOf(1), userMapper.selectCount(wrapper));
    }

    /**
     * 查询符合条件的记录列表
     */
    @Test
    public void selectList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "gejt");
        Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(userMapper.selectList(wrapper).size()));
    }

    /**
     * 查询符合条件的记录列表 返回map结果
     */
    @Test
    public void selectMaps() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "gejt");
        Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(userMapper.selectMaps(wrapper).size()));
    }

    /**
     * 查询符合条件的记录列表 Object  只返回第一个字段的值
     */
    @Test
    public void selectObjs() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", "gejt");
        List<Object> objects = userMapper.selectObjs(wrapper);
        Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(objects.size()));
    }


    /**
     * 查询符合条件的记录列表 按照map查询
     */
    @Test
    public void selectByMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("user_name","gejt");
        Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(userMapper.selectByMap(params).size()));
    }

    /**
     * 查询批量id记录列表
     */
    @Test
    public void selectBatchIds() {
        Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(userMapper.selectBatchIds(Arrays.asList(5)).size()));
    }

    /**
     * 分页查询
     */
    @Test
    public void selectPage() {
        IPage<User> page = new Page<>(1,10);
        QueryWrapper<User> wapper = new QueryWrapper<>();
        wapper.eq("user_name","gejt");
        IPage<User> userIPage = userMapper.selectPage(page, wapper);
        Assert.assertEquals(1,userIPage.getPages());
    }

    /**
     * 分页查询 map 结果
     */
    @Test
    public void selectMapsPage() {
        IPage<Map<String, Object>> page = new Page<>(1,10);
        QueryWrapper<User> wapper = new QueryWrapper<>();
        wapper.eq("user_name","gejt");
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, wapper);
        System.out.println(JSON.toJSONString(mapIPage));
        Assert.assertEquals(1,mapIPage.getRecords().size());
    }
    /**
     * 自定义查询
     */
    @Test
    public void selectUserDtos(){
        List<UserDto> list = userMapper.selectUserDtos("gejt", 100);
        Assert.assertTrue(list.size()>0);
    }
}
