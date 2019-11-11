package com.ngs.service;

import java.util.List;

import com.ngs.pojo.User;
import com.ngs.pojo.UserExample;

public interface UserService {

    int insert(User record);

    
    int deleteByPrimaryKey(Long userId);
    
    int updateByPrimaryKeySelective(User record);


    int updateByPrimaryKey(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long userId);


}
