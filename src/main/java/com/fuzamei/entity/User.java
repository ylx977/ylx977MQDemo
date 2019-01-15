package com.fuzamei.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ylx
 * Created by fuzamei on 2018/8/7.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{

    private Long uid;
    private String name;
    private String password;
    private Integer amount;

}
