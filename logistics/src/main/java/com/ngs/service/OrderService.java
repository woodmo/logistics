package com.ngs.service;

import java.util.List;

import com.ngs.pojo.Order;
import com.ngs.pojo.OrderDetail;
import com.ngs.pojo.OrderExample;

public interface OrderService {

    int insert(Order record);

    
    int deleteByPrimaryKey(Long orderId);
    
    int updateByPrimaryKeySelective(Order record);


    int updateByPrimaryKey(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Long orderId);


	List<OrderDetail> selectOrderDetailsByOrderId(Long orderId);


}
