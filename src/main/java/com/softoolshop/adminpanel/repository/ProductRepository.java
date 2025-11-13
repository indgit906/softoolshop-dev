package com.softoolshop.adminpanel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.softoolshop.adminpanel.dto.ProductDTO;
import com.softoolshop.adminpanel.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoryCustom  {

	@Query("SELECT new com.softoolshop.adminpanel.dto.ProductDTO(p.productId, p.title) from Product p")
	List<ProductDTO> productIdAndNameMap();
	
	List<Product> findByCategoryId(Integer categoryId);

	@Query("SELECT COALESCE(MAX(p.productId), 0) FROM Product p")
	Integer getNextProductIdSeq();

	@Query("SELECT  new com.softoolshop.adminpanel.dto.ProductDTO"
			+ " (p.productId,p.title,p.productLink,p.oldPriceStr,p.priceStr,p.numericPrice,p.imageUrl,p.inactive,p.categoryId)  "
			+ " FROM Product p where p.inactive = :inactive")
	List<ProductDTO> getActiveProducts(@Param("inactive") int inactive);
}
