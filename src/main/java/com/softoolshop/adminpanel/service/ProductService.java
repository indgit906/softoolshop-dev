package com.softoolshop.adminpanel.service;

import java.util.List;

import com.softoolshop.adminpanel.dto.ProductDTO;
import com.softoolshop.adminpanel.dto.ProductFilterDTO;
import com.softoolshop.adminpanel.entity.Product;
import com.softoolshop.adminpanel.entity.ProductDesc;

public interface ProductService {

	List<Product> createProducts(List<ProductDTO> products);

	List<ProductDTO> getAllProducts();

	Product addProduct(ProductDTO product);

	ProductDTO getProductById(Integer productId);

	ProductDesc addProdDescription(ProductDTO product);

	//List<ProductDTO> getProductsByCategory(Integer categoryId);

	List<ProductDTO> getFilteredProducts(ProductFilterDTO filterRequest);

	ProductDTO getProductDescriptionById(Integer productId);

	Product updateProd(ProductDTO product);

}
