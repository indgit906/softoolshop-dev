package com.softoolshop.adminpanel.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.softoolshop.adminpanel.entity.Payment;

@lombok.Data
@JsonInclude(Include.NON_NULL)
public class CreateOrderDTO {

	private Long orderId;
	private Long custId;
	private String firstName;
	private String lastName;
	private String name;
	private String phone;
	private String email;
	private Double totalAmt;
	private Timestamp createdAt;
    private Timestamp paidAt;
    private String orderStatus;
    private String transactionRef;
    //private PaymentDTO payment;
    private List<OrderDetailDTO> orderDetails;
    
}
