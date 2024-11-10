package com.hysea.user.domain;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

/**
 * @author hysea
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {


	/**
	 * 用户id
	 */
	@TableId(value = "user_id", type = IdType.AUTO)
	private Long userId;


	/**
	 * 用户姓名
	 */
	@TableField("username")
	private String username;


	/**
	 * 性别 0女 1男
	 */
	@TableField("sex")
	private String sex;


	/**
	 * 用户账号
	 */
	@TableField("account")
	private String account;


	/**
	 * md5加密密码 (长度相等使用定长字符串类型char)
	 */
	@TableField("password")
	private String password;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time",fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 修改时间
	 */
	@TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;


}