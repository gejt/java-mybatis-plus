package xyz.zao123.java.mybatis;

import org.junit.Test;
import xyz.zao123.java.mybatis.dao.entity.User;

/**
 * @author gejt
 */
public class IServiceUpdateLockTest extends TestApplication{
    /**
     * 更新使用乐观锁
     */
    @Test
    public void testUpdateLock(){
        User user1 = userService.getById(5L);
        User user2 = userService.getById(5L);
        System.out.println("update user1 success?"+userService.updateById(user1));
        System.out.println("update user2 success?"+userService.updateById(user2));
    }
}
