package com.softoolshop.adminpanel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softoolshop.adminpanel.entity.Order;
import com.softoolshop.adminpanel.entity.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o JOIN FETCH o.orderDetails od JOIN FETCH od.product WHERE o.orderId = :orderId")
	Order getByOrderId(@Param("orderId") Long orderId);

	@Query("SELECT o FROM Order o " +
	           "WHERE (:searchKey IS NULL OR TRIM(:searchKey) = '' " +
	           "       OR LOWER(o.name)  LIKE LOWER(CONCAT('%', :searchKey, '%')) " +
	           "       OR LOWER(o.email) LIKE LOWER(CONCAT('%', :searchKey, '%')) " +
	           "       OR o.phone         LIKE CONCAT('%', :searchKey, '%')) " +
	           "  AND (:status IS NULL OR o.orderStatus = :status) ")
	List<Order> searchOrders(@Param("searchKey") String searchKey, @Param("status") OrderStatus status);

}
