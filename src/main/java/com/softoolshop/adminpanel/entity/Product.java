package com.softoolshop.adminpanel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
@lombok.Data
@lombok.NoArgsConstructor
public class Product {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;
	private String title;
    private String productLink;
    private String oldPriceStr;
    private String priceStr;
    //private String discount;
    private int numericPrice;
    private String imageUrl;
    private Integer inactive=0;
    private Integer categoryId;
    private String description;
    
    public Product(Integer productId) {
    	this.productId = productId;
    }
}
