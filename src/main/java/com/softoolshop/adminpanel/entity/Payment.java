package com.softoolshop.adminpanel.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

//    @Column(name = "order_id", nullable = false)
//    private Long orderId;

    @Column(name = "payment_method", length = 50, nullable = false)
    private String paymentMethod = "UPI";
    
    @Column(name = "transaction_ref", length = 50, nullable = false)
    private String transactionRef;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "verified_by_admin", nullable = false)
    private boolean verifiedByAdmin = false;

    @OneToOne
    @JoinColumn(name = "order_id")  // foreign key in Payment table
    private Order order;
    
    public enum PaymentStatus {
        PENDING,
        PAID
    }
}

