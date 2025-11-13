package com.softoolshop.adminpanel.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softoolshop.adminpanel.dto.PaymentDTO;
import com.softoolshop.adminpanel.entity.Payment;
import com.softoolshop.adminpanel.entity.Payment.PaymentStatus;

public interface PaymentRepository  extends JpaRepository<Payment, Long> {

	@Query("Select new com.softoolshop.adminpanel.dto.PaymentDTO(p.paymentId, p.order.orderId, p.paymentMethod, p.amount, p.paymentStatus, p.createdAt, p.verifiedByAdmin, p.transactionRef, o.name) from  Payment p, Order o where o.orderId=p.order.orderId")
	List<PaymentDTO> getPayments();
	
	@Modifying
	@Query("UPDATE Payment p SET p.paymentStatus = :status, p.verifiedByAdmin = true WHERE p.order.orderId = :orderId")
	int updatePaymentStatusAndVerification(@Param("status") PaymentStatus status, @Param("orderId") Long orderId);


	
}
