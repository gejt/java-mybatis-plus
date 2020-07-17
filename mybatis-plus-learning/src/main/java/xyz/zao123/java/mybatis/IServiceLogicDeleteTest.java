package xyz.zao123.java.mybatis;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author gejt
 */
public class IServiceLogicDeleteTest extends TestApplication{

    /**
     * 逻辑删除
     * UPDATE tb_user SET deleted=1 WHERE id=? AND deleted=0
     */
    @Test
    public void logicDeleted(){
        Assert.assertTrue(userService.removeById(6L));
    }

    /**
     * 查询删除后的数据
     * SELECT id,user_name,age,email,mobile,status,create_time,update_time,deleted FROM tb_user WHERE id=? AND deleted=0
     */
    @Test
    public void queryDate(){
        Assert.assertNull(userService.getById(6L));
    }
}
