package com.softoolshop.adminpanel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softoolshop.adminpanel.dto.PaymentDTO;
import com.softoolshop.adminpanel.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
    @PostMapping("/request")
    public ResponseEntity<PaymentDTO> createPaymentReq(@RequestBody PaymentDTO paymentDto) {
    	PaymentDTO responseDto = paymentService.createPaymentReq(paymentDto);
        return ResponseEntity.ok(responseDto);
    }
    
    @PutMapping("/mark-paid/{id}")
    public ResponseEntity<String> markAsPaid(@PathVariable Long id) {
    	paymentService.markAsPaid(id);
    	return ResponseEntity.ok("Payment marked as completed.");
    }

    @GetMapping("/list")
    public ResponseEntity<List<PaymentDTO>> getAllPaymentList() {
    	List<PaymentDTO> responseDtos = paymentService.getPayments();
        return ResponseEntity.ok(responseDtos);
    }

}
