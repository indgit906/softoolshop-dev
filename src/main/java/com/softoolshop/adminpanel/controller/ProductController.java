package com.softoolshop.adminpanel.controller;

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

import com.softoolshop.adminpanel.dto.CreateOrderDTO;
import com.softoolshop.adminpanel.dto.ProductDTO;
import com.softoolshop.adminpanel.dto.ProductFilterDTO;
import com.softoolshop.adminpanel.entity.Order;
import com.softoolshop.adminpanel.entity.Product;
import com.softoolshop.adminpanel.entity.ProductDesc;
import com.softoolshop.adminpanel.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService prodService;
	
	@PostMapping
    public ResponseEntity<List<Product>> createProducts(@RequestBody List<ProductDTO> products) {
		List<Product> listProd = prodService.createProducts(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(listProd);
    }
	
	@GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
		List<ProductDTO> listProd = prodService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(listProd);
    }
//	@GetMapping("/{categoryId}")
//    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Integer categoryId) {
//		List<ProductDTO> listProd = prodService.getProductsByCategory(categoryId);
//        return ResponseEntity.status(HttpStatus.OK).body(listProd);
//    }
	
	@PostMapping("add")
    public ResponseEntity<Product> createOrder(@RequestBody ProductDTO product) {
		Product prod = prodService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(prod);
    }
	
	@PostMapping("upd")
    public ResponseEntity<Product> updateProd(@RequestBody ProductDTO product) {
		Product prod = prodService.updateProd(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(prod);
    }
	
	
	@PostMapping("add/description")
    public ResponseEntity<ProductDesc> addProdDescription(@RequestBody ProductDTO product) {
		ProductDesc prod = prodService.addProdDescription(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(prod);
    }
	
	@GetMapping("itm/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer productId) {
		//ProductDTO prodDto = prodService.getProductDescriptionById(productId);
		ProductDTO prodDto = prodService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(prodDto);
    }
	
	@PostMapping("/filter")
	public List<ProductDTO> filterProducts(@RequestBody ProductFilterDTO filterRequest) {
	    return prodService.getFilteredProducts(filterRequest);
	}
	
	@GetMapping("getdescription/{productId}")
    public ResponseEntity<ProductDTO> getProductDescriptionById(@PathVariable Integer productId) {
		ProductDTO prodDto = prodService.getProductDescriptionById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(prodDto);
    }

}
