package com.softoolshop.adminpanel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softoolshop.adminpanel.entity.ProductDesc;

public interface ProductDescRepository extends JpaRepository<ProductDesc, Integer>  {

	@Query("SELECT d.description FROM ProductDesc d WHERE d.productId = :productId")
	String getProductDescById(@Param("productId") Integer productId);
}
