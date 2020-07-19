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
