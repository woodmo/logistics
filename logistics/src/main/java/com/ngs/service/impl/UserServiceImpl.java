package com.ngs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngs.mapper.UserMapper;
import com.ngs.pojo.User;
import com.ngs.pojo.UserExample;
import com.ngs.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	
//	Ôö
	@Override
	public int insert(User record) {
		return userMapper.insert(record);
	}
//	É¾

	@Override
	public int deleteByPrimaryKey(Long userId) {
		return userMapper.deleteByPrimaryKey(userId);
	}

//	¸Ä
	@Override
	public int updateByPrimaryKey(User record) {
		return userMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}
//	²é
	@Override
	public List<User> selectByExample(UserExample example) {
		return userMapper.selectByExample(example);
	}
	@Override
	public User selectByPrimaryKey(Long userId) {
		return userMapper.selectByPrimaryKey(userId);
	}


}
