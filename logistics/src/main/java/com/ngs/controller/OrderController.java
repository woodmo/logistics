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

//	����ת����
	@RequestMapping("/orderPage")
	public String orderPage() {
		return "orderPage";
	}
//	��ѯ�������ҳ����
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Order> list(@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
//		��ѯ����
		OrderExample example = new OrderExample();
//		ʹ��StringUtils���������ж��ǲ��ǿգ���apache��lang3������
		if (StringUtils.isNotBlank(keyword)) {
//			��������,������������Ϊ��һ���Ļ���Ĭ����ʹ��and����������������
			Criteria createCriteria = example.createCriteria();
//			createCriteria.andOrdernameLike("%" + keyword + "%");
		}
		List<Order> selectByExample = orderService.selectByExample(example);
		PageInfo<Order> pageInfo = new PageInfo<Order>(selectByExample);
		return pageInfo;
	}

//	ɾ�����ݣ�������ݸ�������
	@RequestMapping("/delect")
	@ResponseBody
	public InfoObject delect(@RequestParam(value = "delList", required = false) Long orderId,
			@RequestParam(value = "delList[]", required = false) Long[] delList) {
//		���е�
		InfoObject infoObject = new InfoObject(0, "ɾ������ʧ�ܣ�����ϵ����Ա");
		int row = 0;
//		����ɾ��
		if (orderId != null) {
			row = orderService.deleteByPrimaryKey(orderId);
		}
//		���ɾ��
		if (delList != null) {
			for (Long delId : delList) {
				System.out.println(delId);
				row = orderService.deleteByPrimaryKey(delId);
			}
		}
		if (row == 1) {
			infoObject = new InfoObject(1, "ɾ�����ݳɹ�");
		}
		return infoObject;
	}

//	���ҳ��
	@RequestMapping("/insert")
	
	@ResponseBody
	public InfoObject insert(Order order) {
		InfoObject infoObject = new InfoObject(0, "�������ʧ�ܣ�����ϵ����Ա");
		int row = orderService.insert(order);
		if (row == 1) {
			infoObject = new InfoObject(1, "������ݳɹ�");
		}
		return infoObject;
	}

//	�޸�ҳ��
	@RequestMapping("/update")
	@ResponseBody
	public InfoObject update(Order order) {
		InfoObject infoObject = new InfoObject(0, "�޸�����ʧ�ܣ�����ϵ����Ա");

// �����ﲻ����һ���޸ķ�����ԭ���ǣ���ʱû�еõ�ordername��ֵ������һ�������Ļ�����ȫ�������޸ģ��ǻ��ɿյ�
		int row = orderService.updateByPrimaryKeySelective(order);
		if (row == 1) {
			infoObject = new InfoObject(1, "�޸����ݳɹ�");
		}
		return infoObject;
	}

//	�༭��ת
	@RequestMapping("/edit")
	public String edit(@RequestParam(value = "orderId", required = false) Long orderId, Model m) {
//		�����ǵ���޸���������
		if (orderId != null) {
			Order order = orderService.selectByPrimaryKey(orderId);
			m.addAttribute("order", order);
		}
		//�ҵ����е�ҵ��Ա
		UserExample example=new UserExample();
		com.ngs.pojo.UserExample.Criteria criteria = example.createCriteria();
		criteria.andRolenameEqualTo("ҵ��Ա");
		List<User> users = userService.selectByExample(example);
		m.addAttribute("users", users);
//		�ҵ����пͻ�
		
		
		return "orderEdit";
	}
	
	
	
//	���ݸ����id�ҵ��ֱ�һ�����������м��������
	@RequestMapping("/detail")
	@ResponseBody
	public List<OrderDetail> detail(Long orderId) {
		List<OrderDetail> orderDetails = orderService.selectOrderDetailsByOrderId(orderId);
		return orderDetails;
	}
}
