package com.ngs.controller;
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
import com.ngs.pojo.RoleExample.Criteria;
import com.ngs.service.RoleService;
@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
//	简单跳转而已
	@RequestMapping("/rolePage")
	public String rolePage() {
		return "rolePage";
	}
//	查询出来，填到页面上
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo list(@RequestParam(defaultValue = "1")int pageNum,@RequestParam(defaultValue = "10")int pageSize,String keyword) {
		PageHelper.startPage(pageNum, pageSize);
//		查询对象
		RoleExample example=new RoleExample();
//		使用StringUtils工具类来判断是不是空，在apache的lang3包里面
		if(StringUtils.isNotBlank(keyword)){
//		条件对象,创建两个是因为，一个的话，默认是使用and把两个链接起来的
			Criteria createCriteria = example.createCriteria();
			createCriteria.andRolenameLike("%"+keyword+"%");
		}
		List<Role> selectByExample = roleService.selectByExample(example);
		PageInfo<Role> pageInfo = new PageInfo<Role>(selectByExample);
		return pageInfo;
	}
//	删除数据，多个数据跟单个的
	@RequestMapping("/delect")
	@ResponseBody
	public InfoObject delect(@RequestParam(value="delList",required=false)Long roleId,@RequestParam(value="delList[]",required=false)Long[] delList) {
//		共有的
		InfoObject infoObject = new InfoObject(0,"删除数据失败，请联系管理员");
		int row =0;
//		单个删除
		if(roleId!=null) {
			row = roleService.deleteByPrimaryKey(roleId);
		}
//		多个删除
		if(delList!=null){
			for (Long delId : delList) {
				System.out.println(delId);
				row = roleService.deleteByPrimaryKey(delId);
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
	public InfoObject insert(Role role) {
		InfoObject infoObject = new InfoObject(0,"添加数据失败，请联系管理员");
		int row = roleService.insert(role);
		if(row==1) {
			 infoObject = new InfoObject(1,"添加数据成功");
		}
		return infoObject;
	}
//	修改页面
	@RequestMapping("/update")
	@ResponseBody
	public InfoObject update(Role role) {
		InfoObject infoObject = new InfoObject(0,"修改数据失败，请联系管理员");
//		在这里不用另一个修改方法的原因是，此时没有得到rolename的值，用另一个方法的话，会全部进行修改，那会变成空的
		int row = roleService.updateByPrimaryKeySelective(role);
		if(row==1) {
			 infoObject = new InfoObject(1,"修改数据成功");
		}
		return infoObject;
	}
//	编辑跳转
	@RequestMapping("/edit")
	public String edit(@RequestParam(value="roleId",required = false)Long roleId,Model m) {
//		这里是点击修改跳过来的
		if(roleId!=null) {
			Role role = roleService.selectByPrimaryKey(roleId);
			m.addAttribute("role",role);
		}
//		这里等会需要找到，所有的角色分类
//		创建查询条件
		RoleExample example=new RoleExample();
		List<Role> roleList = roleService.selectByExample(example);
		m.addAttribute("roleList",roleList);
		return "roleEdit";
	}	
//	检查是否重名字
	@RequestMapping("/checkRolename")
	@ResponseBody
	public boolean checkRoleName(String rolename) {
//		这里等会需要找到，所有的角色分类
//		创建查询条件
		RoleExample example=new RoleExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andRolenameEqualTo(rolename);
		List<Role> roleList = roleService.selectByExample(example);
		return roleList.size() > 0 ? false : true ;
	}
}
