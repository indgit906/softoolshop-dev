package com.softoolshop.adminpanel.dto;

@lombok.Data
public class ProductFilterDTO {
	
	private Double minPrice = 0.0;
	private Double maxPrice = 10000.0;
	private Integer categoryId;
	private String searchText;
	
}
