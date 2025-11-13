package com.softoolshop.adminpanel.service;

import java.util.List;

import com.softoolshop.adminpanel.dto.PaymentDTO;

public interface PaymentService {

	PaymentDTO createPaymentReq(PaymentDTO paymentDto);

	void markAsPaid(Long paymentId);

	List<PaymentDTO> getPayments();

}
