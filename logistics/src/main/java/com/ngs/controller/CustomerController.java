package com.ngs.controller;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ngs.info.InfoObject;
import com.ngs.pojo.Role;
import com.ngs.pojo.RoleExample;
import com.ngs.pojo.User;
import com.ngs.pojo.UserExample;
import com.ngs.pojo.BaseData;
import com.ngs.pojo.Customer;
import com.ngs.pojo.CustomerExample;
import com.ngs.pojo.CustomerExample.Criteria;
import com.ngs.service.RoleService;
import com.ngs.service.UserService;
import com.ngs.service.BaseDataService;
import com.ngs.service.CustomerService;
@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private UserService userService;
	@Autowired
	private BaseDataService baseDataService;

//	简单跳转而已
	@RequestMapping("/customerPage")
	public String customerPage() {
		return "customerPage";
	}
//	查询出来，填到页面上
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Customer> list(@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
//		查询对象
		CustomerExample example = new CustomerExample();
//		条件对象,创建两个是因为，一个的话，默认是使用and把两个链接起来的
		Criteria createCriteria = example.createCriteria();
		//		首先取得当前认证的用户
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
//		如果当前认证的人是业务员，就只找自己的单
		if(user.getRolename().equals("业务员")) {
			createCriteria.andUserIdEqualTo(user.getUserId());
		}
		
//		使用StringUtils工具类来判断是不是空，在apache的lang3包里面
		if (StringUtils.isNotBlank(keyword)) {
//			
			createCriteria.andCustomerNameLike("%" + keyword + "%");
		}

		List<Customer> selectByExample = customerService.selectByExample(example);
		PageInfo<Customer> pageInfo = new PageInfo<Customer>(selectByExample);
		return pageInfo;
	}

//	删除数据，多个数据跟单个的
	@RequestMapping("/delect")
	@ResponseBody
	public InfoObject delect(@RequestParam(value = "delList", required = false) Long customerId,
			@RequestParam(value = "delList[]", required = false) Long[] delList) {
//		共有的
		InfoObject infoObject = new InfoObject(0, "删除数据失败，请联系管理员");
		int row = 0;
//		单个删除
		if (customerId != null) {
			row = customerService.deleteByPrimaryKey(customerId);
		}
//		多个删除
		if (delList != null) {
			for (Long delId : delList) {
				System.out.println(delId);
				row = customerService.deleteByPrimaryKey(delId);
			}
		}
		if (row == 1) {
			infoObject = new InfoObject(1, "删除数据成功");
		}
		return infoObject;
	}

//	添加页面
	@RequestMapping("/insert")
	@ResponseBody
	public InfoObject insert(Customer customer) {
		InfoObject infoObject = new InfoObject(0, "添加数据失败，请联系管理员");
		int row = customerService.insert(customer);
		if (row == 1) {
			infoObject = new InfoObject(1, "添加数据成功");
		}
		return infoObject;
	}

//	修改页面
	@RequestMapping("/update")

	@ResponseBody
	public InfoObject update(Customer customer) {
		InfoObject infoObject = new InfoObject(0, "修改数据失败，请联系管理员");
// 在这里不用另一个修改方法的原因是，此时没有得到customername的值，用另一个方法的话，会全部进行修改，那会变成空的
		int row = customerService.updateByPrimaryKeySelective(customer);
		if (row == 1) {
			infoObject = new InfoObject(1, "修改数据成功");
		}
		return infoObject;
	}

//	编辑跳转
	@RequestMapping("/edit")
	public String edit(@RequestParam(value = "customerId", required = false) Long customerId, Model m) {
//		这里是点击修改跳过来的
		if (customerId != null) {
			Customer customer = customerService.selectByPrimaryKey(customerId);
			m.addAttribute("customer", customer);
		}

//		这里等会需要找到，所有的业务员
//		创建查询条件
		UserExample example = new UserExample();
		com.ngs.pojo.UserExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andRolenameEqualTo("业务员");
		List<User> userList = userService.selectByExample(example);
		System.out.println(userList);
		m.addAttribute("userList", userList);
//		这里是找到区间信息的
		
		List<BaseData> baseList=baseDataService.selectByParentName("区间管理");
		m.addAttribute("baseList", baseList);
		return "customerEdit";
	}
}
