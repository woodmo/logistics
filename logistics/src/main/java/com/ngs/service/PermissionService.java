package com.ngs.service;

import java.util.List;

import com.ngs.pojo.Permission;
import com.ngs.pojo.PermissionExample;

public interface PermissionService {

    int insert(Permission record);
    
    int deleteByPrimaryKey(Long permissionId);  

    int updateByPrimaryKey(Permission record);

    int updateByPrimaryKeySelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Long permissionId);

	List<String> selectPermissionByIds(List<Long> permissionIdList);


}
