package com.ngs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ngs.mapper.RoleMapper;
import com.ngs.pojo.Role;
import com.ngs.pojo.RoleExample;
import com.ngs.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleMapper roleMapper;
	
//	Ôö
	@Override
	public int insert(Role record) {
		return roleMapper.insert(record);
	}
//	É¾

	@Override
	public int deleteByPrimaryKey(Long roleId) {
		return roleMapper.deleteByPrimaryKey(roleId);
	}

//	¸Ä
	@Override
	public int updateByPrimaryKey(Role record) {
		return roleMapper.updateByPrimaryKey(record);
	}
	@Override
	public int updateByPrimaryKeySelective(Role record) {
		return roleMapper.updateByPrimaryKeySelective(record);
	}
//	²é
	@Override
	public List<Role> selectByExample(RoleExample example) {
		return roleMapper.selectByExample(example);
	}
	@Override
	public Role selectByPrimaryKey(Long roleId) {
		return roleMapper.selectByPrimaryKey(roleId);
	}

}
