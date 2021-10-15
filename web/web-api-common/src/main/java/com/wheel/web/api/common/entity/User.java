package com.wheel.web.api.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @desc 这里不是全部数据库的信息，仅存储了重要的字段
 * @author: zhouf
 */
@Data
public class User implements Serializable {

    private String userName;

    private String phone;

    private String email;

    private Integer status;
}
