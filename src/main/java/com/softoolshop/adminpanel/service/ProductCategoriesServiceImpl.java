package com.softoolshop.adminpanel.service;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softoolshop.adminpanel.dto.ProductCategoriesDTO;
import com.softoolshop.adminpanel.entity.ProductCategories;
import com.softoolshop.adminpanel.repository.ProductCategoriesRepository;

@Service
public class ProductCategoriesServiceImpl implements ProductCategoriesService {
	@Autowired
	private ProductCategoriesRepository prdCtgryRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ProductCategoriesDTO> getAllCategories() {
		List<ProductCategories> entities = prdCtgryRepo.findAll();
		return Arrays.asList(modelMapper.map(entities, ProductCategoriesDTO[].class));
	}

}
