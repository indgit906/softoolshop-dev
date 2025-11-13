package com.softoolshop.adminpanel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_desc")
@lombok.Data
@lombok.NoArgsConstructor
public class ProductDesc {
	
	@Id
	private Integer productId;
	private String shortDesc;
	private String description;

}
