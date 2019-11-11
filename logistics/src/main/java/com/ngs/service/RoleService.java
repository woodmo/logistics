package com.ngs.service;

import java.util.List;

import com.ngs.pojo.Role;
import com.ngs.pojo.RoleExample;
import com.ngs.pojo.User;

public interface RoleService {

    int insert(Role record);

    
    int deleteByPrimaryKey(Long roleId);
    

    int updateByPrimaryKey(Role record);

    int updateByPrimaryKeySelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Long roleId);


}
