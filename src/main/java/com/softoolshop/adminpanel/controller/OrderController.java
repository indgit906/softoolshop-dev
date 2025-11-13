package com.softoolshop.adminpanel.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.softoolshop.adminpanel.dto.CreateOrderDTO;
import com.softoolshop.adminpanel.dto.OrderSearchRequest;
import com.softoolshop.adminpanel.dto.PaymentDTO;
import com.softoolshop.adminpanel.entity.Order;
import com.softoolshop.adminpanel.service.EmailService;
import com.softoolshop.adminpanel.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private RestTemplate restTemplate;
	

	// Create order
    @PostMapping("/{transactionRef}")
    public ResponseEntity<CreateOrderDTO> createOrder(@RequestBody CreateOrderDTO order, @PathVariable("transactionRef") String transactionRef) {
    	order.setTransactionRef(transactionRef);
    	CreateOrderDTO response = orderService.createOrder(order);
    	if (response.getOrderId() != null && response.getOrderId() > 0) {
    		//sendPaymentRequest(response.getOrderId(), BigDecimal.valueOf(response.getTotalAmt()), order.getTransactionRef());
    		try {
    			//CreateOrderDTO obj = orderService.getOrderById(response.getOrderId());
    			emailService.sendOrdrConfermationMail(orderService.getOrderById(response.getOrderId()));
    		} catch (Exception e) {
    			System.out.println(e.getMessage());
    		}
    	}
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // List all orders
    @GetMapping
    public List<CreateOrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }
    
    @PostMapping("filter")
    public List<CreateOrderDTO> searchOrder(@RequestBody OrderSearchRequest searchReq) {
        return orderService.searchOrder(searchReq);
    }
    
    @PostMapping("complete")
    public ResponseEntity<CreateOrderDTO> completeOrder(@RequestBody CreateOrderDTO order) {
    	CreateOrderDTO response = orderService.completeOrder(order);
    	if (response.getOrderStatus() != null) {
    		try {
    			emailService.sendOrdrConfermationMail(response);
    		} catch (Exception e) {
    			System.out.println(e.getMessage());
    		}
    	}
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    /*
     * do not use this method
     */
//    private void sendPaymentRequest(Long orderId, BigDecimal amount, String trnsRef) {
//    	String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
//				//.scheme("https")
//			    .replacePath(null)
//				.build()
//				.toUriString();
//        String url = baseUrl+"/softools/api/payments/request";
//        // Prepare request body
//        PaymentDTO request = new PaymentDTO(orderId, amount, trnsRef);
//        // Send POST request
//        ResponseEntity<PaymentDTO> response = restTemplate.postForEntity(url, request, PaymentDTO.class);
//        // Check response
//        if (response.getStatusCode() == HttpStatus.OK) {
//            System.out.println("Response: " + response.getBody());
//        } else {
//            System.out.println("Error: " + response.getStatusCode());
//        }
//    }
}
