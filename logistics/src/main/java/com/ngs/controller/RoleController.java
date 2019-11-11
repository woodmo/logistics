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
//	����ת����
	@RequestMapping("/rolePage")
	public String rolePage() {
		return "rolePage";
	}
//	��ѯ�������ҳ����
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo list(@RequestParam(defaultValue = "1")int pageNum,@RequestParam(defaultValue = "10")int pageSize,String keyword) {
		PageHelper.startPage(pageNum, pageSize);
//		��ѯ����
		RoleExample example=new RoleExample();
//		ʹ��StringUtils���������ж��ǲ��ǿգ���apache��lang3������
		if(StringUtils.isNotBlank(keyword)){
//		��������,������������Ϊ��һ���Ļ���Ĭ����ʹ��and����������������
			Criteria createCriteria = example.createCriteria();
			createCriteria.andRolenameLike("%"+keyword+"%");
		}
		List<Role> selectByExample = roleService.selectByExample(example);
		PageInfo<Role> pageInfo = new PageInfo<Role>(selectByExample);
		return pageInfo;
	}
//	ɾ�����ݣ�������ݸ�������
	@RequestMapping("/delect")
	@ResponseBody
	public InfoObject delect(@RequestParam(value="delList",required=false)Long roleId,@RequestParam(value="delList[]",required=false)Long[] delList) {
//		���е�
		InfoObject infoObject = new InfoObject(0,"ɾ������ʧ�ܣ�����ϵ����Ա");
		int row =0;
//		����ɾ��
		if(roleId!=null) {
			row = roleService.deleteByPrimaryKey(roleId);
		}
//		���ɾ��
		if(delList!=null){
			for (Long delId : delList) {
				System.out.println(delId);
				row = roleService.deleteByPrimaryKey(delId);
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
	public InfoObject insert(Role role) {
		InfoObject infoObject = new InfoObject(0,"�������ʧ�ܣ�����ϵ����Ա");
		int row = roleService.insert(role);
		if(row==1) {
			 infoObject = new InfoObject(1,"������ݳɹ�");
		}
		return infoObject;
	}
//	�޸�ҳ��
	@RequestMapping("/update")
	@ResponseBody
	public InfoObject update(Role role) {
		InfoObject infoObject = new InfoObject(0,"�޸�����ʧ�ܣ�����ϵ����Ա");
//		�����ﲻ����һ���޸ķ�����ԭ���ǣ���ʱû�еõ�rolename��ֵ������һ�������Ļ�����ȫ�������޸ģ��ǻ��ɿյ�
		int row = roleService.updateByPrimaryKeySelective(role);
		if(row==1) {
			 infoObject = new InfoObject(1,"�޸����ݳɹ�");
		}
		return infoObject;
	}
//	�༭��ת
	@RequestMapping("/edit")
	public String edit(@RequestParam(value="roleId",required = false)Long roleId,Model m) {
//		�����ǵ���޸���������
		if(roleId!=null) {
			Role role = roleService.selectByPrimaryKey(roleId);
			m.addAttribute("role",role);
		}
//		����Ȼ���Ҫ�ҵ������еĽ�ɫ����
//		������ѯ����
		RoleExample example=new RoleExample();
		List<Role> roleList = roleService.selectByExample(example);
		m.addAttribute("roleList",roleList);
		return "roleEdit";
	}	
//	����Ƿ�������
	@RequestMapping("/checkRolename")
	@ResponseBody
	public boolean checkRoleName(String rolename) {
//		����Ȼ���Ҫ�ҵ������еĽ�ɫ����
//		������ѯ����
		RoleExample example=new RoleExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andRolenameEqualTo(rolename);
		List<Role> roleList = roleService.selectByExample(example);
		return roleList.size() > 0 ? false : true ;
	}
}
