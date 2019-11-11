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
import com.ngs.pojo.BaseData;
import com.ngs.pojo.BaseDataExample;
import com.ngs.pojo.BaseDataExample.Criteria;
import com.ngs.service.BaseDataService;
@Controller
@RequestMapping("/baseData")
public class BaseDataController {
	@Autowired
	private BaseDataService baseDataService;
//	简单跳转而已
	@RequestMapping("/baseDataPage")
	public String baseDataPage() {
		return "baseDataPage";
	}
//	查询出来，填到页面上
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo list(@RequestParam(defaultValue = "1")int pageNum,@RequestParam(defaultValue = "10")int pageSize,String keyword) {
		PageHelper.startPage(pageNum, pageSize);
//		查询对象
		BaseDataExample example=new BaseDataExample();
//		使用StringUtils工具类来判断是不是空，在apache的lang3包里面
		if(StringUtils.isNotBlank(keyword)){
//		条件对象,创建两个是因为，一个的话，默认是使用and把两个链接起来的
			Criteria createCriteria = example.createCriteria();
			createCriteria.andBaseNameLike("%"+keyword+"%");
		}
		List<BaseData> selectByExample = baseDataService.selectByExample(example);
		PageInfo<BaseData> pageInfo = new PageInfo<BaseData>(selectByExample);
		return pageInfo;
	}
//	删除数据，多个数据跟单个的
	@RequestMapping("/delect")
	@ResponseBody
	public InfoObject delect(@RequestParam(value="delList",required=false)Long baseDataId,@RequestParam(value="delList[]",required=false)Long[] delList) {
//		共有的
		InfoObject infoObject = new InfoObject(0,"删除数据失败，请联系管理员");
		int row =0;
//		单个删除
		if(baseDataId!=null) {
			row = baseDataService.deleteByPrimaryKey(baseDataId);
		}
//		多个删除
		if(delList!=null){
			for (Long delId : delList) {
				System.out.println(delId);
				row = baseDataService.deleteByPrimaryKey(delId);
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
	public InfoObject insert(BaseData baseData) {
		InfoObject infoObject = new InfoObject(0,"添加数据失败，请联系管理员");
		int row = baseDataService.insert(baseData);
		if(row==1) {
			 infoObject = new InfoObject(1,"添加数据成功");
		}
		return infoObject;
	}
//	修改页面
	@RequestMapping("/update")
	@ResponseBody
	public InfoObject update(BaseData baseData) {
		InfoObject infoObject = new InfoObject(0,"修改数据失败，请联系管理员");
//		在这里不用另一个修改方法的原因是，此时没有得到baseDataname的值，用另一个方法的话，会全部进行修改，那会变成空的
		int row = baseDataService.updateByPrimaryKeySelective(baseData);
		if(row==1) {
			 infoObject = new InfoObject(1,"修改数据成功");
		}
		return infoObject;
	}
//	编辑跳转
	@RequestMapping("/edit")
	public String edit(@RequestParam(value="baseId",required = false)Long baseDataId,Model m) {
//		这里是点击修改跳过来的
		if(baseDataId!=null) {
			BaseData baseData = baseDataService.selectByPrimaryKey(baseDataId);
			m.addAttribute("baseData",baseData);
			System.out.println("sdfsdff"+baseData);
		}
//		这里等会需要找到，所有的角色分类
//		创建查询条件
		BaseDataExample example=new BaseDataExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdIsNull();
		List<BaseData> baseDataList = baseDataService.selectByExample(example);
		m.addAttribute("baseDataList",baseDataList);
		return "baseDataEdit";
	}	
//	检查是否重名字
	@RequestMapping("/checkBaseDataname")
	@ResponseBody
	public boolean checkBaseDataName(String baseName) {
//		这里等会需要找到，所有的角色分类
//		创建查询条件
		BaseDataExample example=new BaseDataExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andBaseNameEqualTo(baseName);
		List<BaseData> baseDataList = baseDataService.selectByExample(example);
		return baseDataList.size() > 0 ? false : true ;
	}
}
