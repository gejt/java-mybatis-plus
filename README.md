# 使用Mybatis Plus简化Spring Boot +Mybatis项目开发

## Mybatis Plus 简介

MyBatis-Plus（简称 MP）是一个 MyBatis 的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

愿景是成为 MyBatis 最好的搭档，就像 魂斗罗 中的 1P、2P，基友搭配，效率翻倍。

![](./mybatis-plus.png)

### MyBatis-Plus有如下特性：

* 无侵入：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
* 损耗小：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作
* 强大的 CRUD 操作：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
* 支持 Lambda 形式调用：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错
* 支持主键自动生成：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题
* 支持 ActiveRecord 模式：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作
* 支持自定义全局通用操作：支持全局通用方法注入（ Write once, use anywhere ）
* 内置代码生成器：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用
* 内置分页插件：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询
* 分页插件支持多种数据库：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer 等多种数据库
* 内置性能分析插件：可输出 Sql 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询
* 内置全局拦截插件：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误操作

## #MyBatis-Plus支持数据库

mysql 、 mariadb 、 oracle 、 db2 、 h2 、 hsql 、 sqlite 、 postgresql 、 sqlserver 、 presto
达梦数据库 、 虚谷数据库 、 人大金仓数据库

### MyBatis-Plus框架结构



## Mybatis Plus 集成Spring Boot项目配置

### 项目管理和依赖

示例项目使用Maven管理项目依赖，集成Spring Boot 2.1.14.RELEASE 编写示例代码，项目依赖如下：
```
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <java.version>1.8</java.version>
    <spring-boot.version>2.1.14.RELEASE</spring-boot.version>
</properties>
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.3.2</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-test</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.72</version>
        </dependency>
    </dependencies>
```

### 项目配置

appliation.properties
```
#数据源
#spring.datasource.driver-class-name=com.mysql.jdbc.Driver #Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
spring.datasource.url=jdbc:mysql://123.206.31.192:3306/test?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=M
#日志
mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl

# 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略配置@TableLogic步骤)
mybatis-plus.global-config.db-config.logic-delete-field=deleted
# 逻辑已删除值(默认为 1)
mybatis-plus.global-config.db-config.logic-delete-value=1
# 逻辑未删除值(默认为 0)
mybatis-plus.global-config.db-config.logic-not-delete-value=0
```
```
MybatisPlusConfig.java
package xyz.zao123.java.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus 配置
 * @author gejt
 */
@Configuration
@MapperScan("xyz.zao123.java.mybatis.dao.mapper")
public class MybatisPlusConfig {

    /**
     *乐观锁插件配置
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 分页插件配置
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

}
```

### 项目中用到的类
User.java
```
package xyz.zao123.java.mybatis.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author gejt
 */
@Data
@Accessors(chain = true)
@TableName("tb_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String userName;
    private Integer age;
    private String email;
    private String mobile;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    @TableLogic
    private Integer deleted;
    @Version
    private Integer version;
}
```

UserDto.java
```
package xyz.zao123.java.mybatis.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author gejt
 */
@Data
@Accessors(chain = true)
public class UserDto {
    private Long id;
    private String userName;
    private Integer age;
    private String email;
    private String mobile;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String test;
}
```

UserMapper.java
```
package xyz.zao123.java.mybatis.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.zao123.java.mybatis.dao.entity.User;
import xyz.zao123.java.mybatis.dto.UserDto;

import java.util.List;

/**
 * @author gejt
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 自定义查询
     * @param userName
     * @param age
     * @return
     */
    @Select("select a.*,'aaaaa' as test from tb_user a where a.user_name=#{userName} and a.age=#{age}")
    List<UserDto> selectUserDtos(@Param("userName") String userName, @Param("age")Integer age);
}
```


UserService.java
```
package xyz.zao123.java.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.zao123.java.mybatis.dao.entity.User;

/**
 * @author gejt
 */
public interface UserService extends IService<User> {
}
```


UserServiceImpl.java
```
package xyz.zao123.java.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.zao123.java.mybatis.dao.entity.User;
import xyz.zao123.java.mybatis.dao.mapper.UserMapper;
import xyz.zao123.java.mybatis.service.UserService;

/**
 * @author gejt
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
```

```
测试基类 TestApplication.java
package xyz.zao123.java.mybatis;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.zao123.java.mybatis.dao.entity.User;
import xyz.zao123.java.mybatis.dao.mapper.UserMapper;
import xyz.zao123.java.mybatis.service.UserService;

import java.util.Date;

/**
 * @author gejt
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestApplication {

    public static User buildUser(){
        User user = new User();
        user.setUserName("gejt");
        user.setAge(100);
        user.setEmail("852442493@qq.com");
        user.setMobile("11111111111");
        user.setStatus(1);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return user;
    }

}
```
## Mybatis Plus Mapper CRUD 接口

说明:

* 通用 CRUD 封装BaseMapper接口，为 Mybatis-Plus 启动时自动解析实体表关系映射转换为 Mybatis 内部对象注入容器

* 泛型 T 为任意实体对象

* 参数 Serializable 为任意类型主键 Mybatis-Plus 不推荐使用复合主键约定每一张表都有自己的唯一 id 主键

* 对象 Wrapper 为 条件构造器

* 分页查询需要配置PaginationInterceptor插件

### UserMapper.java
```
package xyz.zao123.java.mybatis.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.zao123.java.mybatis.dao.entity.User;
import xyz.zao123.java.mybatis.dto.UserDto;

import java.util.List;

/**
 * @author gejt
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 自定义查询
     * @param userName
     * @param age
     * @return
     */
    @Select("select a.*,'aaaaa' as test from tb_user a where a.user_name=#{userName} and a.age=#{age}")
    List<UserDto> selectUserDtos(@Param("userName") String userName, @Param("age")Integer age);

}
```

### 代码示例：MapperTest.java
```
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gejt
 */
public class MapperTest extends TestApplication {

    @Autowired
    private UserMapper userMapper;

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
```


# 继承Mybatis plus IService 实现通用CRUD接口

说明:

* 通用 Service CRUD 封装IService接口，进一步封装 CRUD 采用 get 查询单行 remove 删除list 查询集合 page 分页 前缀命名方式区分 Mapper 层避免混淆，

* 泛型 T 为任意实体对象

* 建议如果存在自定义通用 Service 方法的可能，请创建自己的 IBaseService 继承 Mybatis-Plus 提供的基类

* 对象 Wrapper 为 条件构造器

* 分页查询需要配置PaginationInterceptor插件

### 保存方法代码示例：IServiceSaveTest.java
```
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
```


### 删除方法代码示例：IServiceDeleteTest.java
```
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
```

### 更新方法代码示例：IServiceUpdateTest.java
```
package xyz.zao123.java.mybatis;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.zao123.java.mybatis.dao.entity.User;
import xyz.zao123.java.mybatis.service.UserService;
import java.util.Arrays;

/**
 * @author gejt
 */
public class IServiceUpdateTest extends TestApplication {

    @Autowired
    protected UserService userService;

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

    /**
     * LambdaUpdateChainWrapper 链式操作更新
     */
    @Test
    public void updateLambdaChainWrapper() {
        userService.lambdaUpdate()
                .set(User::getUserName,"gejt123")
                .eq(User::getId,21L)
                .update();
    }
}
```


### 查询方法代码示例：IServiceSelectTest.java
```
package xyz.zao123.java.mybatis;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.zao123.java.mybatis.dao.entity.User;
import xyz.zao123.java.mybatis.service.UserService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gejt
 */
public class IServiceSelectTest extends TestApplication {

    @Autowired
    protected UserService userService;

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
```


## Mybatis plus 简化逻辑删除

说明:

* 只对自动注入的sql起效:

* 插入: 不作限制

* 查找: 追加where条件过滤掉已删除数据,且使用 wrapper.entity 生成的where条件会忽略该字段

* 更新: 追加where条件防止更新到已删除数据,且使用 wrapper.entity 生成的where条件会忽略该字段

* 删除: 转变为 更新

例如:
删除: update user set deleted=1 where id = 1 and deleted=0

*查找: select id,name,deleted from user where deleted=0

字段类型支持说明:

支持所有数据类型(推荐使用 Integer,Boolean,LocalDateTime)

如果数据库字段使用datetime,逻辑未删除值和已删除值支持配置为字符串null,另一个值支持配置为函数来获取值如now()

附录:

逻辑删除是为了方便数据恢复和保护数据本身价值等等的一种方案，但实际就是删除。

如果你需要频繁查出来看就不应使用逻辑删除，而是以一个状态去表示。

逻辑删除配置


### application.properties
```
# 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略配置@TableLogic步骤)
mybatis-plus.global-config.db-config.logic-delete-field=deleted
# 逻辑已删除值(默认为 1)
mybatis-plus.global-config.db-config.logic-delete-value=1
# 逻辑未删除值(默认为 0)
mybatis-plus.global-config.db-config.logic-not-delete-value=0
```


###User.java
```
package xyz.zao123.java.mybatis.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author gejt
 */
@Data
@Accessors(chain = true)
@TableName("tb_user")
public class User {
    .
    .
    .
    @TableLogic
    private Integer deleted;
}
```


### 代码示例：IServiceLogicDeleteTest.java
```
package xyz.zao123.java.mybatis;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.zao123.java.mybatis.service.UserService;

/**
 * @author gejt
 */
public class IServiceLogicDeleteTest extends TestApplication{

    @Autowired
    protected UserService userService;

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
```



## Mybatis plus 乐观锁插件

主要适用场景

意图：

当要更新一条记录的时候，希望这条记录没有被别人更新

乐观锁实现方式：

取出记录时，获取当前version

更新时，带上这个version

执行更新时， set version = newVersion where version = oldVersion

如果version不对，就更新失败

---
乐观锁配置需要2步 记得两步

### 1.插件配置
```
package xyz.zao123.java.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus 配置
 * @author gejt
 */
@Configuration
@MapperScan("xyz.zao123.java.mybatis.dao.mapper")
public class MybatisPlusConfig {
    /**
     *乐观锁插件配置
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
```


### 2.注解实体字段 @Version 必须要!

```
package xyz.zao123.java.mybatis.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author gejt
 */
@Data
@Accessors(chain = true)
@TableName("tb_user")
public class User {
    .
    .
    .
    @Version
    private Integer version;
}
```



### 代码示例：IServiceUpdateLockTest.java
```
package xyz.zao123.java.mybatis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.zao123.java.mybatis.dao.entity.User;
import xyz.zao123.java.mybatis.service.UserService;

/**
 * @author gejt
 */
public class IServiceUpdateLockTest extends TestApplication{
    @Autowired
    protected UserService userService;

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
```

