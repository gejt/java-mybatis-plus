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
