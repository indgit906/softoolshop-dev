package com.softoolshop.adminpanel.repository;

import java.util.List;

import com.softoolshop.adminpanel.dto.ProductFilterDTO;
import com.softoolshop.adminpanel.entity.Product;

public interface ProductRepositoryCustom {
	List<Product> getFilteredProducts(ProductFilterDTO request);
}
