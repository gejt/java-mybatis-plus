package xyz.zao123.java.mybatis.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
}
