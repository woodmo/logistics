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
//	简单跳转而已
	@RequestMapping("/permissionPage")
	public String permissionPage() {
		return "permissionPage";
	}
	
//	查询出来，填到页面上
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo list(@RequestParam(defaultValue = "1")int pageNum,@RequestParam(defaultValue = "10")int pageSize,String keyword) {
		PageHelper.startPage(pageNum, pageSize);
//		查询对象
		PermissionExample example=new PermissionExample();
//		使用StringUtils工具类来判断是不是空，在apache的lang3包里面
		if(StringUtils.isNotBlank(keyword)){
//			条件对象,创建两个是因为，一个的话，默认是使用and把两个链接起来的
			Criteria createCriteria = example.createCriteria();
			createCriteria.andNameLike("%"+keyword+"%");
		}

		List<Permission> selectByExample = permissionService.selectByExample(example);
		PageInfo<Permission> pageInfo = new PageInfo<Permission>(selectByExample);
		return pageInfo;
	}
//	删除数据，多个数据跟单个的
	@RequestMapping("/delect")
	@ResponseBody
	public InfoObject delect(@RequestParam(value="delList",required=false)Long permissionId,@RequestParam(value="delList[]",required=false)Long[] delList) {
//		共有的
		InfoObject infoObject = new InfoObject(0,"删除数据失败，请联系管理员");
		int row =0;
//		单个删除
		if(permissionId!=null) {
			row = permissionService.deleteByPrimaryKey(permissionId);
		}
//		多个删除
		if(delList!=null){
			for (Long delId : delList) {
				System.out.println(delId);
				row = permissionService.deleteByPrimaryKey(delId);
			}	
		}
		if(row==1) {
			 infoObject = new InfoObject(1,"删除数据成功");
		}
		return infoObject;
	}
//	添加页面
	@RequestMapping("/insert")
	@ResponseBody
	public InfoObject insert(Permission permission) {
		InfoObject infoObject = new InfoObject(0,"添加数据失败，请联系管理员");
		int row = permissionService.insert(permission);
		if(row==1) {
			 infoObject = new InfoObject(1,"添加数据成功");
		}
		return infoObject;
	}
	
//	修改页面
	@RequestMapping("/update")
	@ResponseBody
	public InfoObject update(Permission permission) {
		InfoObject infoObject = new InfoObject(0,"修改数据失败，请联系管理员");
//		在这里不用另一个修改方法的原因是，此时没有得到username的值，用另一个方法的话，会全部进行修改，那会变成空的
		int row = permissionService.updateByPrimaryKeySelective(permission);
		if(row==1) {
			 infoObject = new InfoObject(1,"修改数据成功");
		}
		return infoObject;
	}
//	编辑跳转
	@RequestMapping("/edit")
	public String edit(@RequestParam(value="permissionId",required = false)Long permissionId,Model m) {
//		这里是点击修改跳过来的
		if(permissionId!=null) {
			Permission permission = permissionService.selectByPrimaryKey(permissionId);
			m.addAttribute("permission",permission);
		}
		
//		这里等会需要找到，所有的角色分类
//		创建查询条件
		PermissionExample example=new PermissionExample();
		List<Permission> permissionList = permissionService.selectByExample(example);
		m.addAttribute("permissionList",permissionList);
		return "permissionEdit";
	}
	
	
//	检查是否重名字,修改或者添加的时候，异步验证用的
	@RequestMapping("/checkName")
	@ResponseBody
	public boolean checkPermissionName(String name) {
//		这里等会需要找到，所有的角色分类
//		创建查询条件
		PermissionExample example=new PermissionExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andNameEqualTo(name);
		List<Permission> userList = permissionService.selectByExample(example);
		return userList.size() > 0 ? false : true ;
	}
//	在角色表页面，取得所有的权限
	@RequestMapping("/getAllpermissions")
	@ResponseBody
	public List<Permission> getAllpermission() {
		PermissionExample example=new PermissionExample();
		List<Permission> permissionList = permissionService.selectByExample(example);
		return permissionList;
	}
}
