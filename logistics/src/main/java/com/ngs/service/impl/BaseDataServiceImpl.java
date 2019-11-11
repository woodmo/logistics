package com.ngs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngs.mapper.BaseDataMapper;
import com.ngs.pojo.BaseData;
import com.ngs.pojo.BaseDataExample;
import com.ngs.service.BaseDataService;
@Service
public class BaseDataServiceImpl implements BaseDataService{
	@Autowired
	private BaseDataMapper baseDataMapper;
	
//	Ôö
	@Override
	public int insert(BaseData record) {
		return baseDataMapper.insert(record);
	}
//	É¾

	@Override
	public int deleteByPrimaryKey(Long baseDataId) {
		return baseDataMapper.deleteByPrimaryKey(baseDataId);
	}

//	¸Ä
	@Override
	public int updateByPrimaryKey(BaseData record) {
		return baseDataMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(BaseData record) {
		return baseDataMapper.updateByPrimaryKeySelective(record);
	}
//	²é
	@Override
	public List<BaseData> selectByExample(BaseDataExample example) {
		return baseDataMapper.selectByExample(example);
	}
	@Override
	public BaseData selectByPrimaryKey(Long baseDataId) {
		return baseDataMapper.selectByPrimaryKey(baseDataId);
	}

	@Override
	public List<BaseData> selectByParentName(String string) {
		// TODO Auto-generated method stub
		return baseDataMapper.selectByParentName(string);
	}


}
