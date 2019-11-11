package com.ngs.service;

import java.util.List;

import com.ngs.pojo.BaseData;
import com.ngs.pojo.BaseDataExample;

public interface BaseDataService {

    int insert(BaseData record);

    
    int deleteByPrimaryKey(Long baseDataId);
    
    int updateByPrimaryKeySelective(BaseData record);


    int updateByPrimaryKey(BaseData record);

    List<BaseData> selectByExample(BaseDataExample example);

    BaseData selectByPrimaryKey(Long baseDataId);


	List<BaseData> selectByParentName(String string);


}
