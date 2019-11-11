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

//	����ת����
	@RequestMapping("/adminPage")
	@RequiresPermissions("admin:adminPage")
	public String adminPage() {
		return "adminPage";
	}
//	��¼��ת����ʧ�ܵ�longinҳ��
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model m) {
//		shiroLoginFailure�����Լ��Ķ���������֤ʧ�ܺ���shiro�д����ʧ�ܵ���Ϣ
		String shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure");
//		���������ڿյ�ʱ��˵���Ѿ�ʧ���ˣ����ǲ�֪�������벻�ԣ������˺Ų���
		if (shiroLoginFailure != null) {
			if (UnknownAccountException.class.getName().equals(shiroLoginFailure)) {
				m.addAttribute("errorInfo", "��������˺Ų����ڣ����������롣");
			} else if (IncorrectCredentialsException.class.getName().equals(shiroLoginFailure)) {
				m.addAttribute("errorInfo", "�����������������������롣");
			}
		}
		return "forward:/login.jsp";
	}

//	��ѯ�������ҳ����
	@RequestMapping("/list")
	@RequiresPermissions("admin:list")
	@ResponseBody
	public PageInfo<User> list(@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "10") int pageSize, String keyword) {
		PageHelper.startPage(pageNum, pageSize);
//		��ѯ����
		UserExample example = new UserExample();
//		ʹ��StringUtils���������ж��ǲ��ǿգ���apache��lang3������
		if (StringUtils.isNotBlank(keyword)) {
//			��������,������������Ϊ��һ���Ļ���Ĭ����ʹ��and����������������
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

//	ɾ�����ݣ�������ݸ�������
	@RequestMapping("/delect")
	@RequiresPermissions("admin:delete")
	@ResponseBody
	public InfoObject delect(@RequestParam(value = "delList", required = false) Long userId,
			@RequestParam(value = "delList[]", required = false) Long[] delList) {
//		���е�
		InfoObject infoObject = new InfoObject(0, "ɾ������ʧ�ܣ�����ϵ����Ա");
		int row = 0;
//		����ɾ��
		if (userId != null) {
			row = userService.deleteByPrimaryKey(userId);
		}
//		���ɾ��
		if (delList != null) {
			for (Long delId : delList) {
				System.out.println(delId);
				row = userService.deleteByPrimaryKey(delId);
			}
		}
		if (row == 1) {
			infoObject = new InfoObject(1, "ɾ�����ݳɹ�");
		}
		return infoObject;
	}

//	���ҳ��
	@RequestMapping("/insert")
	@RequiresPermissions("admin:insert")
	
	@ResponseBody
	public InfoObject insert(User user) {
		user.setCreateDate(new Date());
//		��������
		String salt = UUID.randomUUID().toString().substring(0, 5);
		user.setSalt(salt);
		Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 3);
		user.setPassword(md5Hash.toString());

		InfoObject infoObject = new InfoObject(0, "�������ʧ�ܣ�����ϵ����Ա");
		int row = userService.insert(user);
		if (row == 1) {
			infoObject = new InfoObject(1, "������ݳɹ�");
		}
		return infoObject;
	}

//	�޸�ҳ��
	@RequestMapping("/update")

	@RequiresPermissions("admin:update")
	@ResponseBody
	public InfoObject update(User user) {
		InfoObject infoObject = new InfoObject(0, "�޸�����ʧ�ܣ�����ϵ����Ա");
//		��������
		String salt = UUID.randomUUID().toString().substring(0, 5);
		user.setSalt(salt);
		Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 3);
		user.setPassword(md5Hash.toString());
// �����ﲻ����һ���޸ķ�����ԭ���ǣ���ʱû�еõ�username��ֵ������һ�������Ļ�����ȫ�������޸ģ��ǻ��ɿյ�
		int row = userService.updateByPrimaryKeySelective(user);
		if (row == 1) {
			infoObject = new InfoObject(1, "�޸����ݳɹ�");
		}
		return infoObject;
	}

//	�༭��ת
	@RequestMapping("/edit")
	public String edit(@RequestParam(value = "userId", required = false) Long userId, Model m) {
//		�����ǵ���޸���������
		if (userId != null) {
			User user = userService.selectByPrimaryKey(userId);
			m.addAttribute("user", user);
		}

//		����Ȼ���Ҫ�ҵ������еĽ�ɫ����
//		������ѯ����
		RoleExample example = new RoleExample();
		List<Role> roleList = roleService.selectByExample(example);
		m.addAttribute("roleList", roleList);
		return "adminEdit";
	}

//	����Ƿ�������
	@RequestMapping("/checkUsername")
	@RequiresPermissions("admin:checkUsername")
	@ResponseBody
	public boolean checkUserName(String username) {
		// ����Ȼ���Ҫ�ҵ������еĽ�ɫ����
//		������ѯ����
		UserExample example = new UserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUsernameEqualTo(username);
		List<User> userList = userService.selectByExample(example);
		return userList.size() > 0 ? false : true;
	}
}
