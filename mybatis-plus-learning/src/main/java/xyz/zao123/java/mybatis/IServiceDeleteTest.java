package xyz.zao123.java.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author gejt
 */
public class IServiceDeleteTest extends TestApplication{
    public boolean removeById(Serializable id) {
        return false;
    }

    @Override
    public boolean remove(Wrapper queryWrapper) {
        return false;
    }

    @Override
    public boolean removeByIds(Collection idList) {
        return false;
    }

    @Override
    public boolean removeByMap(Map columnMap) {
        return false;
    }
}
