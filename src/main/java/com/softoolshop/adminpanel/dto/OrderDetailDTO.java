package com.softoolshop.adminpanel.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@lombok.Data
@JsonInclude(Include.NON_NULL)
public class OrderDetailDTO {

    private Long orderDtlId;
	private Integer productId;
	private String title;
	private BigDecimal price;
	private int quantity;
	private Integer categoryId;
}
