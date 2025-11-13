package com.softoolshop.adminpanel.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@lombok.Data
@JsonInclude(Include.NON_NULL)
public class ProductDTO {

	private Integer productId;
	private String title;
    private String productLink;
    private String oldPriceStr;
    private String priceStr;
    private String discount;
    private int numericPrice;
    private String imageUrl;
    private Integer inactive=0;
    private Integer categoryId;
    private String description;
    private MultipartFile prodImg;
    
    public ProductDTO() {
    	
    }
    
    public ProductDTO(Integer productId, String title) {
    	this.productId = productId;
    	this.title = title;
    }

	public ProductDTO(Integer productId, String title, String productLink, String oldPriceStr, String priceStr,
			int numericPrice, String imageUrl, Integer inactive, Integer categoryId) {
		super();
		this.productId = productId;
		this.title = title;
		this.productLink = productLink;
		this.oldPriceStr = oldPriceStr;
		this.priceStr = priceStr;
		this.numericPrice = numericPrice;
		this.imageUrl = imageUrl;
		this.inactive = inactive;
		this.categoryId = categoryId;
	}
    
    
}
