package com.softoolshop.adminpanel.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
@lombok.Data
public class Order {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long custId;
    @Column(name = "full_name")
    private String name;
    private String phone;
    private String email;
    //@Enumerated(EnumType.STRING)
    //private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PLACED;
    private Timestamp createdAt;
    private Timestamp paidAt;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

	public void addOrderDetail(OrderDetail detail) {
        this.orderDetails.add(detail);
    }
}
