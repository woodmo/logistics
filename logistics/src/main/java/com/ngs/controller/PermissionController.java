package com.ngs.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.ngs.pojo.Permission;
import com.ngs.pojo.PermissionExample;
import com.ngs.pojo.PermissionExample.Criteria;
import com.ngs.service.PermissionService;
import com.ngs.service.RoleService;

@Controller
@RequestMapping("/permission")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;
//	����ת����
	@RequestMapping("/permissionPage")
	public String permissionPage() {
		return "permissionPage";
	}
	
//	��ѯ�������ҳ����
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo list(@RequestParam(defaultValue = "1")int pageNum,@RequestParam(defaultValue = "10")int pageSize,String keyword) {
		PageHelper.startPage(pageNum, pageSize);
//		��ѯ����
		PermissionExample example=new PermissionExample();
//		ʹ��StringUtils���������ж��ǲ��ǿգ���apache��lang3������
		if(StringUtils.isNotBlank(keyword)){
//			��������,������������Ϊ��һ���Ļ���Ĭ����ʹ��and����������������
			Criteria createCriteria = example.createCriteria();
			createCriteria.andNameLike("%"+keyword+"%");
		}

		List<Permission> selectByExample = permissionService.selectByExample(example);
		PageInfo<Permission> pageInfo = new PageInfo<Permission>(selectByExample);
		return pageInfo;
	}
//	ɾ�����ݣ�������ݸ�������
	@RequestMapping("/delect")
	@ResponseBody
	public InfoObject delect(@RequestParam(value="delList",required=false)Long permissionId,@RequestParam(value="delList[]",required=false)Long[] delList) {
//		���е�
		InfoObject infoObject = new InfoObject(0,"ɾ������ʧ�ܣ�����ϵ����Ա");
		int row =0;
//		����ɾ��
		if(permissionId!=null) {
			row = permissionService.deleteByPrimaryKey(permissionId);
		}
//		���ɾ��
		if(delList!=null){
			for (Long delId : delList) {
				System.out.println(delId);
				row = permissionService.deleteByPrimaryKey(delId);
			}	
		}
		if(row==1) {
			 infoObject = new InfoObject(1,"ɾ�����ݳɹ�");
		}
		return infoObject;
	}
//	���ҳ��
	@RequestMapping("/insert")
	@ResponseBody
	public InfoObject insert(Permission permission) {
		InfoObject infoObject = new InfoObject(0,"�������ʧ�ܣ�����ϵ����Ա");
		int row = permissionService.insert(permission);
		if(row==1) {
			 infoObject = new InfoObject(1,"������ݳɹ�");
		}
		return infoObject;
	}
	
//	�޸�ҳ��
	@RequestMapping("/update")
	@ResponseBody
	public InfoObject update(Permission permission) {
		InfoObject infoObject = new InfoObject(0,"�޸�����ʧ�ܣ�����ϵ����Ա");
//		�����ﲻ����һ���޸ķ�����ԭ���ǣ���ʱû�еõ�username��ֵ������һ�������Ļ�����ȫ�������޸ģ��ǻ��ɿյ�
		int row = permissionService.updateByPrimaryKeySelective(permission);
		if(row==1) {
			 infoObject = new InfoObject(1,"�޸����ݳɹ�");
		}
		return infoObject;
	}
//	�༭��ת
	@RequestMapping("/edit")
	public String edit(@RequestParam(value="permissionId",required = false)Long permissionId,Model m) {
//		�����ǵ���޸���������
		if(permissionId!=null) {
			Permission permission = permissionService.selectByPrimaryKey(permissionId);
			m.addAttribute("permission",permission);
		}
		
//		����Ȼ���Ҫ�ҵ������еĽ�ɫ����
//		������ѯ����
		PermissionExample example=new PermissionExample();
		List<Permission> permissionList = permissionService.selectByExample(example);
		m.addAttribute("permissionList",permissionList);
		return "permissionEdit";
	}
	
	
//	����Ƿ�������,�޸Ļ�����ӵ�ʱ���첽��֤�õ�
	@RequestMapping("/checkName")
	@ResponseBody
	public boolean checkPermissionName(String name) {
//		����Ȼ���Ҫ�ҵ������еĽ�ɫ����
//		������ѯ����
		PermissionExample example=new PermissionExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andNameEqualTo(name);
		List<Permission> userList = permissionService.selectByExample(example);
		return userList.size() > 0 ? false : true ;
	}
//	�ڽ�ɫ��ҳ�棬ȡ�����е�Ȩ��
	@RequestMapping("/getAllpermissions")
	@ResponseBody
	public List<Permission> getAllpermission() {
		PermissionExample example=new PermissionExample();
		List<Permission> permissionList = permissionService.selectByExample(example);
		return permissionList;
	}
}
