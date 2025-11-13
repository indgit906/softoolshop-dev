package com.softoolshop.adminpanel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softoolshop.adminpanel.dto.ProductCategoriesDTO;
import com.softoolshop.adminpanel.service.ProductCategoriesService;

@RestController
@RequestMapping("/api/prodcategory")
public class ProductCategoriesController {

	private final ProductCategoriesService prdCategoryservice;

	public ProductCategoriesController(ProductCategoriesService service) {
		this.prdCategoryservice = service;
	}

	@GetMapping
	public ResponseEntity<List<ProductCategoriesDTO>> getAllCategories() {
		List<ProductCategoriesDTO> list = prdCategoryservice.getAllCategories();
		return ResponseEntity.ok(list);
	}
}
