package com.ngs.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ngs.pojo.User;
import com.ngs.pojo.UserExample;
import com.ngs.pojo.UserExample.Criteria;
import com.ngs.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class UserServiceImplTest {
	@Autowired
	private UserService userService;
	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteByPrimaryKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByPrimaryKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectByExample() {
		PageHelper.startPage(1, 10);
		UserExample example=new UserExample();
//		条件对象
//		Criteria createCriteria = example.createCriteria();
//		createCriteria.andUserIdEqualTo(2L);
		List<User> selectByExample = userService.selectByExample(example);
		for (User user : selectByExample) {
//			System.out.println(user);
		}
		PageInfo<User> pageInfo = new PageInfo<User>(selectByExample);
		System.out.println(pageInfo);
	}

	@Test
	public void testSelectByPrimaryKey() {
		fail("Not yet implemented");
	}

}
