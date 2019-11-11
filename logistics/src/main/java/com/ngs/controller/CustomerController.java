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

//	����ת����
	@RequestMapping("/customerPage")
	public String customerPage() {
		return "customerPage";
	}
//	��ѯ�������ҳ����
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo<Customer> list(@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
//		��ѯ����
		CustomerExample example = new CustomerExample();
//		��������,������������Ϊ��һ���Ļ���Ĭ����ʹ��and����������������
		Criteria createCriteria = example.createCriteria();
		//		����ȡ�õ�ǰ��֤���û�
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
//		�����ǰ��֤������ҵ��Ա����ֻ���Լ��ĵ�
		if(user.getRolename().equals("ҵ��Ա")) {
			createCriteria.andUserIdEqualTo(user.getUserId());
		}
		
//		ʹ��StringUtils���������ж��ǲ��ǿգ���apache��lang3������
		if (StringUtils.isNotBlank(keyword)) {
//			
			createCriteria.andCustomerNameLike("%" + keyword + "%");
		}

		List<Customer> selectByExample = customerService.selectByExample(example);
		PageInfo<Customer> pageInfo = new PageInfo<Customer>(selectByExample);
		return pageInfo;
	}

//	ɾ�����ݣ�������ݸ�������
	@RequestMapping("/delect")
	@ResponseBody
	public InfoObject delect(@RequestParam(value = "delList", required = false) Long customerId,
			@RequestParam(value = "delList[]", required = false) Long[] delList) {
//		���е�
		InfoObject infoObject = new InfoObject(0, "ɾ������ʧ�ܣ�����ϵ����Ա");
		int row = 0;
//		����ɾ��
		if (customerId != null) {
			row = customerService.deleteByPrimaryKey(customerId);
		}
//		���ɾ��
		if (delList != null) {
			for (Long delId : delList) {
				System.out.println(delId);
				row = customerService.deleteByPrimaryKey(delId);
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
	public InfoObject insert(Customer customer) {
		InfoObject infoObject = new InfoObject(0, "�������ʧ�ܣ�����ϵ����Ա");
		int row = customerService.insert(customer);
		if (row == 1) {
			infoObject = new InfoObject(1, "������ݳɹ�");
		}
		return infoObject;
	}

//	�޸�ҳ��
	@RequestMapping("/update")

	@ResponseBody
	public InfoObject update(Customer customer) {
		InfoObject infoObject = new InfoObject(0, "�޸�����ʧ�ܣ�����ϵ����Ա");
// �����ﲻ����һ���޸ķ�����ԭ���ǣ���ʱû�еõ�customername��ֵ������һ�������Ļ�����ȫ�������޸ģ��ǻ��ɿյ�
		int row = customerService.updateByPrimaryKeySelective(customer);
		if (row == 1) {
			infoObject = new InfoObject(1, "�޸����ݳɹ�");
		}
		return infoObject;
	}

//	�༭��ת
	@RequestMapping("/edit")
	public String edit(@RequestParam(value = "customerId", required = false) Long customerId, Model m) {
//		�����ǵ���޸���������
		if (customerId != null) {
			Customer customer = customerService.selectByPrimaryKey(customerId);
			m.addAttribute("customer", customer);
		}

//		����Ȼ���Ҫ�ҵ������е�ҵ��Ա
//		������ѯ����
		UserExample example = new UserExample();
		com.ngs.pojo.UserExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andRolenameEqualTo("ҵ��Ա");
		List<User> userList = userService.selectByExample(example);
		System.out.println(userList);
		m.addAttribute("userList", userList);
//		�������ҵ�������Ϣ��
		
		List<BaseData> baseList=baseDataService.selectByParentName("�������");
		m.addAttribute("baseList", baseList);
		return "customerEdit";
	}
}
