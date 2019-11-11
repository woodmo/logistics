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
//	����ת����
	@RequestMapping("/baseDataPage")
	public String baseDataPage() {
		return "baseDataPage";
	}
//	��ѯ�������ҳ����
	@RequestMapping("/list")
	@ResponseBody
	public PageInfo list(@RequestParam(defaultValue = "1")int pageNum,@RequestParam(defaultValue = "10")int pageSize,String keyword) {
		PageHelper.startPage(pageNum, pageSize);
//		��ѯ����
		BaseDataExample example=new BaseDataExample();
//		ʹ��StringUtils���������ж��ǲ��ǿգ���apache��lang3������
		if(StringUtils.isNotBlank(keyword)){
//		��������,������������Ϊ��һ���Ļ���Ĭ����ʹ��and����������������
			Criteria createCriteria = example.createCriteria();
			createCriteria.andBaseNameLike("%"+keyword+"%");
		}
		List<BaseData> selectByExample = baseDataService.selectByExample(example);
		PageInfo<BaseData> pageInfo = new PageInfo<BaseData>(selectByExample);
		return pageInfo;
	}
//	ɾ�����ݣ�������ݸ�������
	@RequestMapping("/delect")
	@ResponseBody
	public InfoObject delect(@RequestParam(value="delList",required=false)Long baseDataId,@RequestParam(value="delList[]",required=false)Long[] delList) {
//		���е�
		InfoObject infoObject = new InfoObject(0,"ɾ������ʧ�ܣ�����ϵ����Ա");
		int row =0;
//		����ɾ��
		if(baseDataId!=null) {
			row = baseDataService.deleteByPrimaryKey(baseDataId);
		}
//		���ɾ��
		if(delList!=null){
			for (Long delId : delList) {
				System.out.println(delId);
				row = baseDataService.deleteByPrimaryKey(delId);
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
	public InfoObject insert(BaseData baseData) {
		InfoObject infoObject = new InfoObject(0,"�������ʧ�ܣ�����ϵ����Ա");
		int row = baseDataService.insert(baseData);
		if(row==1) {
			 infoObject = new InfoObject(1,"������ݳɹ�");
		}
		return infoObject;
	}
//	�޸�ҳ��
	@RequestMapping("/update")
	@ResponseBody
	public InfoObject update(BaseData baseData) {
		InfoObject infoObject = new InfoObject(0,"�޸�����ʧ�ܣ�����ϵ����Ա");
//		�����ﲻ����һ���޸ķ�����ԭ���ǣ���ʱû�еõ�baseDataname��ֵ������һ�������Ļ�����ȫ�������޸ģ��ǻ��ɿյ�
		int row = baseDataService.updateByPrimaryKeySelective(baseData);
		if(row==1) {
			 infoObject = new InfoObject(1,"�޸����ݳɹ�");
		}
		return infoObject;
	}
//	�༭��ת
	@RequestMapping("/edit")
	public String edit(@RequestParam(value="baseId",required = false)Long baseDataId,Model m) {
//		�����ǵ���޸���������
		if(baseDataId!=null) {
			BaseData baseData = baseDataService.selectByPrimaryKey(baseDataId);
			m.addAttribute("baseData",baseData);
			System.out.println("sdfsdff"+baseData);
		}
//		����Ȼ���Ҫ�ҵ������еĽ�ɫ����
//		������ѯ����
		BaseDataExample example=new BaseDataExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdIsNull();
		List<BaseData> baseDataList = baseDataService.selectByExample(example);
		m.addAttribute("baseDataList",baseDataList);
		return "baseDataEdit";
	}	
//	����Ƿ�������
	@RequestMapping("/checkBaseDataname")
	@ResponseBody
	public boolean checkBaseDataName(String baseName) {
//		����Ȼ���Ҫ�ҵ������еĽ�ɫ����
//		������ѯ����
		BaseDataExample example=new BaseDataExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andBaseNameEqualTo(baseName);
		List<BaseData> baseDataList = baseDataService.selectByExample(example);
		return baseDataList.size() > 0 ? false : true ;
	}
}
