package com.ngs.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.ngs.pojo.Order;
import com.ngs.pojo.OrderDetail;
import com.ngs.pojo.OrderExample;
import com.ngs.pojo.OrderExample.Criteria;
import com.ngs.service.RoleService;
import com.ngs.service.UserService;
import com.ngs.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;

//	简单跳转而已
	@RequestMapping("/orderPage")
	public String orderPage() {
		return "orderPage";
	}
//	查询出来，填到页面上
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Order> list(@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
//		查询对象
		OrderExample example = new OrderExample();
//		使用StringUtils工具类来判断是不是空，在apache的lang3包里面
		if (StringUtils.isNotBlank(keyword)) {
//			条件对象,创建两个是因为，一个的话，默认是使用and把两个链接起来的
			Criteria createCriteria = example.createCriteria();
//			createCriteria.andOrdernameLike("%" + keyword + "%");
		}
		List<Order> selectByExample = orderService.selectByExample(example);
		PageInfo<Order> pageInfo = new PageInfo<Order>(selectByExample);
		return pageInfo;
	}

//	删除数据，多个数据跟单个的
	@RequestMapping("/delect")
	@ResponseBody
	public InfoObject delect(@RequestParam(value = "delList", required = false) Long orderId,
			@RequestParam(value = "delList[]", required = false) Long[] delList) {
//		共有的
		InfoObject infoObject = new InfoObject(0, "删除数据失败，请联系管理员");
		int row = 0;
//		单个删除
		if (orderId != null) {
			row = orderService.deleteByPrimaryKey(orderId);
		}
//		多个删除
		if (delList != null) {
			for (Long delId : delList) {
				System.out.println(delId);
				row = orderService.deleteByPrimaryKey(delId);
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
	public InfoObject insert(Order order) {
		InfoObject infoObject = new InfoObject(0, "添加数据失败，请联系管理员");
		int row = orderService.insert(order);
		if (row == 1) {
			infoObject = new InfoObject(1, "添加数据成功");
		}
		return infoObject;
	}

//	修改页面
	@RequestMapping("/update")
	@ResponseBody
	public InfoObject update(Order order) {
		InfoObject infoObject = new InfoObject(0, "修改数据失败，请联系管理员");

// 在这里不用另一个修改方法的原因是，此时没有得到ordername的值，用另一个方法的话，会全部进行修改，那会变成空的
		int row = orderService.updateByPrimaryKeySelective(order);
		if (row == 1) {
			infoObject = new InfoObject(1, "修改数据成功");
		}
		return infoObject;
	}

//	编辑跳转
	@RequestMapping("/edit")
	public String edit(@RequestParam(value = "orderId", required = false) Long orderId, Model m) {
//		这里是点击修改跳过来的
		if (orderId != null) {
			Order order = orderService.selectByPrimaryKey(orderId);
			m.addAttribute("order", order);
		}
		//找到所有的业务员
		UserExample example=new UserExample();
		com.ngs.pojo.UserExample.Criteria criteria = example.createCriteria();
		criteria.andRolenameEqualTo("业务员");
		List<User> users = userService.selectByExample(example);
		m.addAttribute("users", users);
//		找到所有客户
		
		
		return "orderEdit";
	}
	
	
	
//	根据父表的id找到字表（一个订单里面有几个伙件）
	@RequestMapping("/detail")
	@ResponseBody
	public List<OrderDetail> detail(Long orderId) {
		List<OrderDetail> orderDetails = orderService.selectOrderDetailsByOrderId(orderId);
		return orderDetails;
	}
}
