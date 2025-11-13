package com.softoolshop.adminpanel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.softoolshop.adminpanel.entity.Payment.PaymentStatus;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class PaymentDTO {
	

	private Long paymentId;
	private Long orderId;
	private String paymentMethod = "UPI";
	private BigDecimal amount;
	private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private boolean verifiedByAdmin = false;
    private String transactionRef;
    private String custName;
    
            
    public PaymentDTO(Long orderId, BigDecimal amount) {
    	this.orderId = orderId;
    	this.amount = amount;
    }
    
    public PaymentDTO(Long orderId, BigDecimal amount, String transactionRef) {
    	this.orderId = orderId;
    	this.amount = amount;
    	this.transactionRef = transactionRef;
    }
}
