package com.softoolshop.adminpanel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softoolshop.adminpanel.dto.PaymentDTO;
import com.softoolshop.adminpanel.entity.Payment;
import com.softoolshop.adminpanel.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentRepository pymntRepo;

	@Override
	public PaymentDTO createPaymentReq(PaymentDTO paymentDto) {
		Payment entity = new Payment();
		//entity.setOrderId(paymentDto.getOrderId());
		entity.setAmount(paymentDto.getAmount());
		entity.setPaymentMethod(paymentDto.getPaymentMethod());
		entity.setPaymentStatus(paymentDto.getPaymentStatus());
		entity.setTransactionRef(paymentDto.getTransactionRef());
		Payment frmDb = pymntRepo.save(entity);
		PaymentDTO resObj = new PaymentDTO(frmDb.getPaymentId(), frmDb.getOrder().getOrderId(), 
				frmDb.getPaymentMethod(),frmDb.getAmount(), frmDb.getPaymentStatus(), 
				frmDb.getCreatedAt(), frmDb.isVerifiedByAdmin(), frmDb.getTransactionRef(), null);
		return resObj;
	}

	@Override
	public void markAsPaid(Long paymentId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PaymentDTO> getPayments() {
		return pymntRepo.getPayments();
	}

}
