package com.softoolshop.adminpanel.service;

import java.util.List;

import com.softoolshop.adminpanel.dto.CreateOrderDTO;
import com.softoolshop.adminpanel.dto.OrderSearchRequest;
import com.softoolshop.adminpanel.entity.Order;

public interface OrderService {

	CreateOrderDTO createOrder(CreateOrderDTO orderDto);

	List<CreateOrderDTO> getAllOrders();

	CreateOrderDTO completeOrder(CreateOrderDTO order);

	CreateOrderDTO getOrderById(Long orderId);

	List<CreateOrderDTO> searchOrder(OrderSearchRequest searchReq);

}
