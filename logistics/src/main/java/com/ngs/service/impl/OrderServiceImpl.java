package com.ngs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngs.mapper.OrderDetailMapper;
import com.ngs.mapper.OrderMapper;
import com.ngs.pojo.Order;
import com.ngs.pojo.OrderDetail;
import com.ngs.pojo.OrderDetailExample;
import com.ngs.pojo.OrderDetailExample.Criteria;
import com.ngs.pojo.OrderExample;
import com.ngs.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderDetailMapper orderDetailMapper;
//	Ôö
	@Override
	public int insert(Order record) {
		return orderMapper.insert(record);
	}
//	É¾

	@Override
	public int deleteByPrimaryKey(Long orderId) {
		return orderMapper.deleteByPrimaryKey(orderId);
	}

//	¸Ä
	@Override
	public int updateByPrimaryKey(Order record) {
		return orderMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Order record) {
		return orderMapper.updateByPrimaryKeySelective(record);
	}
//	²é
	@Override
	public List<Order> selectByExample(OrderExample example) {
		return orderMapper.selectByExample(example);
	}
	@Override
	public Order selectByPrimaryKey(Long orderId) {
		return orderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public List<OrderDetail> selectOrderDetailsByOrderId(Long orderId) {
		OrderDetailExample example=new OrderDetailExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(orderId);
		List<OrderDetail> orderDetails = orderDetailMapper.selectByExample(example);
		return orderDetails;
	}


}
