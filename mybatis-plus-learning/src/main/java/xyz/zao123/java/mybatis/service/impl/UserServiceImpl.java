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
