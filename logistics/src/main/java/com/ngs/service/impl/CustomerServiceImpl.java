package com.ngs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngs.mapper.CustomerMapper;
import com.ngs.pojo.Customer;
import com.ngs.pojo.CustomerExample;
import com.ngs.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerMapper customerMapper;
	
//	Ôö
	@Override
	public int insert(Customer record) {
		return customerMapper.insert(record);
	}
//	É¾

	@Override
	public int deleteByPrimaryKey(Long customerId) {
		return customerMapper.deleteByPrimaryKey(customerId);
	}

//	¸Ä
	@Override
	public int updateByPrimaryKey(Customer record) {
		return customerMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Customer record) {
		return customerMapper.updateByPrimaryKeySelective(record);
	}
//	²é
	@Override
	public List<Customer> selectByExample(CustomerExample example) {
		return customerMapper.selectByExample(example);
	}
	@Override
	public Customer selectByPrimaryKey(Long customerId) {
		return customerMapper.selectByPrimaryKey(customerId);
	}


}
