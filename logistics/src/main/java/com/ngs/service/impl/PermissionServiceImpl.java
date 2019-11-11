package com.ngs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngs.mapper.PermissionMapper;
import com.ngs.pojo.Permission;
import com.ngs.pojo.PermissionExample;
import com.ngs.service.PermissionService;
@Service
public class PermissionServiceImpl implements PermissionService{
	@Autowired
	private PermissionMapper permissionMapper;
	
//	Ôö
	@Override
	public int insert(Permission record) {
		return permissionMapper.insert(record);
	}
//	É¾

	@Override
	public int deleteByPrimaryKey(Long permissionId) {
		return permissionMapper.deleteByPrimaryKey(permissionId);
	}

//	¸Ä
	@Override
	public int updateByPrimaryKey(Permission record) {
		return permissionMapper.updateByPrimaryKey(record);
	}
	@Override
	public int updateByPrimaryKeySelective(Permission record) {
		return permissionMapper.updateByPrimaryKeySelective(record);
	}
//	²é
	@Override
	public List<Permission> selectByExample(PermissionExample example) {
		return permissionMapper.selectByExample(example);
	}
	@Override
	public Permission selectByPrimaryKey(Long permissionId) {
		return permissionMapper.selectByPrimaryKey(permissionId);
	}

	@Override
	public List<String> selectPermissionByIds(List<Long> permissionIdList) {
		// TODO Auto-generated method stub
		return permissionMapper.selectPermissionByIds(permissionIdList);
	}

}
