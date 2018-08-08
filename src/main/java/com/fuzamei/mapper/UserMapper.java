package com.fuzamei.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ylx
 * Created by fuzamei on 2018/8/7.
 */
@Mapper
public interface UserMapper {

    int updateUserAmountById(@Param("uid") Long uid);
}
