package com.softoolshop.adminpanel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softoolshop.adminpanel.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query("SELECT u.custId FROM Customer u WHERE u.email = :email")
	Long getCustomerId(@Param("email") String email);
	
	

}
