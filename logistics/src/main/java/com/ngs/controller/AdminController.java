package com.ngs.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ngs.info.InfoObject;
import com.ngs.pojo.Role;
import com.ngs.pojo.RoleExample;
import com.ngs.pojo.User;
import com.ngs.pojo.UserExample;
import com.ngs.pojo.UserExample.Criteria;
import com.ngs.service.RoleService;
import com.ngs.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

//	简单跳转而已
	@RequestMapping("/adminPage")
	@RequiresPermissions("admin:adminPage")
	public String adminPage() {
		return "adminPage";
	}
//	登录跳转或者失败的longin页面
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model m) {
//		shiroLoginFailure不是自己的东西，是认证失败后，在shiro中存放着失败的信息
		String shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure");
//		当他不等于空的时候，说明已经失败了，就是不知道是密码不对，还是账号不对
		if (shiroLoginFailure != null) {
			if (UnknownAccountException.class.getName().equals(shiroLoginFailure)) {
				m.addAttribute("errorInfo", "你输入的账号不存在！请重新输入。");
			} else if (IncorrectCredentialsException.class.getName().equals(shiroLoginFailure)) {
				m.addAttribute("errorInfo", "你输入的密码错误！请重新输入。");
			}
		}
		return "forward:/login.jsp";
	}

//	查询出来，填到页面上
	@RequestMapping("/list")
	@RequiresPermissions("admin:list")
	@ResponseBody
	public PageInfo<User> list(@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
//		查询对象
		UserExample example = new UserExample();
//		使用StringUtils工具类来判断是不是空，在apache的lang3包里面
		if (StringUtils.isNotBlank(keyword)) {
//			条件对象,创建两个是因为，一个的话，默认是使用and把两个链接起来的
			Criteria createCriteria = example.createCriteria();
			Criteria createCriteria2 = example.createCriteria();
			createCriteria.andUsernameLike("%" + keyword + "%");
			createCriteria2.andRealnameLike("%" + keyword + "%");
			example.or(createCriteria2);
		}

		List<User> selectByExample = userService.selectByExample(example);
		PageInfo<User> pageInfo = new PageInfo<User>(selectByExample);
		return pageInfo;
	}

//	删除数据，多个数据跟单个的
	@RequestMapping("/delect")
	@RequiresPermissions("admin:delete")
	@ResponseBody
	public InfoObject delect(@RequestParam(value = "delList", required = false) Long userId,
			@RequestParam(value = "delList[]", required = false) Long[] delList) {
//		共有的
		InfoObject infoObject = new InfoObject(0, "删除数据失败，请联系管理员");
		int row = 0;
//		单个删除
		if (userId != null) {
			row = userService.deleteByPrimaryKey(userId);
		}
//		多个删除
		if (delList != null) {
			for (Long delId : delList) {
				System.out.println(delId);
				row = userService.deleteByPrimaryKey(delId);
			}
		}
		if (row == 1) {
			infoObject = new InfoObject(1, "删除数据成功");
		}
		return infoObject;
	}

//	添加页面
	@RequestMapping("/insert")
	@RequiresPermissions("admin:insert")
	
	@ResponseBody
	public InfoObject insert(User user) {
		user.setCreateDate(new Date());
//		设置密码
		String salt = UUID.randomUUID().toString().substring(0, 5);
		user.setSalt(salt);
		Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 3);
		user.setPassword(md5Hash.toString());

		InfoObject infoObject = new InfoObject(0, "添加数据失败，请联系管理员");
		int row = userService.insert(user);
		if (row == 1) {
			infoObject = new InfoObject(1, "添加数据成功");
		}
		return infoObject;
	}

//	修改页面
	@RequestMapping("/update")

	@RequiresPermissions("admin:update")
	@ResponseBody
	public InfoObject update(User user) {
		InfoObject infoObject = new InfoObject(0, "修改数据失败，请联系管理员");
//		设置密码
		String salt = UUID.randomUUID().toString().substring(0, 5);
		user.setSalt(salt);
		Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 3);
		user.setPassword(md5Hash.toString());
// 在这里不用另一个修改方法的原因是，此时没有得到username的值，用另一个方法的话，会全部进行修改，那会变成空的
		int row = userService.updateByPrimaryKeySelective(user);
		if (row == 1) {
			infoObject = new InfoObject(1, "修改数据成功");
		}
		return infoObject;
	}

//	编辑跳转
	@RequestMapping("/edit")
	public String edit(@RequestParam(value = "userId", required = false) Long userId, Model m) {
//		这里是点击修改跳过来的
		if (userId != null) {
			User user = userService.selectByPrimaryKey(userId);
			m.addAttribute("user", user);
		}

//		这里等会需要找到，所有的角色分类
//		创建查询条件
		RoleExample example = new RoleExample();
		List<Role> roleList = roleService.selectByExample(example);
		m.addAttribute("roleList", roleList);
		return "adminEdit";
	}

//	检查是否重名字
	@RequestMapping("/checkUsername")
	@RequiresPermissions("admin:checkUsername")
	@ResponseBody
	public boolean checkUserName(String username) {
		// 这里等会需要找到，所有的角色分类
//		创建查询条件
		UserExample example = new UserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUsernameEqualTo(username);
		List<User> userList = userService.selectByExample(example);
		return userList.size() > 0 ? false : true;
	}
}
