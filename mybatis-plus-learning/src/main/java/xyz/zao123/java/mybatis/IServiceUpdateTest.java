package xyz.zao123.java.mybatis;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.Assert;
import org.junit.Test;
import xyz.zao123.java.mybatis.dao.entity.User;

import java.util.Arrays;

/**
 * @author gejt
 */
public class IServiceUpdateTest extends TestApplication {

    /**
     * 按照id更新
     */
    @Test
    public void updateById() {
        User user = buildUser();
        user.setId(5L);
        user.setUserName("gejt123");
        Assert.assertTrue(userService.updateById(user));
    }

    /**
     * 更新符合条件的数据
     */
    @Test
    public void update() {
        User user = buildUser();
        user.setUserName("gejt");
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name", "gejt123");
        Assert.assertTrue(userService.update(user, updateWrapper));
    }

    /**
     * 更新符合条件的数据 UpdateWrapper设置更新数据
     */
    @Test
    public void updateWrapper() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", 5L);
        updateWrapper.set("user_name", "gejt");
        Assert.assertTrue(userService.update(updateWrapper));
    }

    /**
     * 根据id批量更新
     */
    @Test
    public void updateBatchById() {
        Assert.assertTrue(userService.updateBatchById(Arrays.asList(buildUser().setId(20L).setUserName("test20"), buildUser().setId(21L).setUserName("test21"))));
    }

    /**
     * 分batchSize个批次 根据id批量更新
     */
    @Test
    public void updateBatchByIdBatchSize() {
        Assert.assertTrue(userService.updateBatchById(Arrays.asList(buildUser().setId(20L).setUserName("test20"), buildUser().setId(21L).setUserName("test21")), 1));
    }

    /**
     * UpdateChainWrapper 链式操作更新
     */
    @Test
    public void updateChainWrapper() {
        userService.update()
                .set("user_name", "gejt")
                .in("id", Arrays.asList(20, 21))
                .update();
    }
}
