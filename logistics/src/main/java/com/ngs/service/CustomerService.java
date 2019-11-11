package com.ngs.service;

import java.util.List;

import com.ngs.pojo.Customer;
import com.ngs.pojo.CustomerExample;

public interface CustomerService {

    int insert(Customer record);

    
    int deleteByPrimaryKey(Long customerId);
    
    int updateByPrimaryKeySelective(Customer record);


    int updateByPrimaryKey(Customer record);

    List<Customer> selectByExample(CustomerExample example);

    Customer selectByPrimaryKey(Long customerId);


}
