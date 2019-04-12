package com.zkyong.demo.mapper;

import com.zkyong.demo.model.User1;

/**
 * 用户表1 Mapper接口
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 15:21:53
 */
public interface User1Mapper {

    int insert(User1 record);

    User1 selectByPrimaryKey(Integer id);
}