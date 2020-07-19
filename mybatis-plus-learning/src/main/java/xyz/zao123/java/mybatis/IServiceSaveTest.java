package xyz.zao123.java.mybatis;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.zao123.java.mybatis.dao.entity.User;
import xyz.zao123.java.mybatis.service.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * @author gejt
 */
public class IServiceSaveTest extends TestApplication{

    @Autowired
    protected UserService userService;

    /**
     * 保存一条数据
     */
    @Test
    public void save() {
        User user = buildUser();
        boolean save = userService.save(user);
        Assert.assertTrue(save);
    }

    /**
     * 批量保存
     */
    @Test
    public void saveBatch() {
        boolean save = userService.saveBatch(Arrays.asList(buildUser(), buildUser(), buildUser()));
        Assert.assertTrue(save);
    }

    /**
     * 批量保存 拆分为batchSize个批次执行插入语句
     */
    @Test
    public void saveBatchSize() {
        boolean save = userService.saveBatch(Arrays.asList(buildUser(), buildUser(), buildUser()),1);
        Assert.assertTrue(save);
    }

    /**
     * 批量保存或更新
     */
    @Test
    public void saveOrUpdateBatch() {
        boolean save = userService.saveOrUpdateBatch(Arrays.asList(buildUser().setId(5L).setAge(666), buildUser(), buildUser()));
        Assert.assertTrue(save);
    }

    /**
     * 批量保存或更新 拆分为batchSize个批次执行插入语句
     */
    @Test
    public void saveOrUpdateBatchSize() {
        boolean save = userService.saveOrUpdateBatch(Arrays.asList(buildUser().setId(5L).setAge(999), buildUser(), buildUser()),1);
        Assert.assertTrue(save);
    }


    /**
     * 保存或更新
     */
    @Test
    public void saveOrUpdate() {
        boolean test = userService.saveOrUpdate(buildUser().setId(5L).setUserName("test"));
        Assert.assertTrue(test);
    }

    /**
     * 保存或更新 符合条件的记录
     */
    @Test
    public void saveOrUpdateCondition() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",5);
        boolean test = userService.saveOrUpdate(buildUser().setUserName("gejt"), wrapper);
        Assert.assertTrue(test);
    }
}
