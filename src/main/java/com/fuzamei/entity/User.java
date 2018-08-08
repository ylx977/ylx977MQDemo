package com.fuzamei.entity;

import lombok.Data;

/**
 * @author ylx
 * Created by fuzamei on 2018/8/7.
 */
@Data
public class User {

    private Long uid;
    private String name;
    private String password;
    private Integer amount;

}
