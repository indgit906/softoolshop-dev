package com.softoolshop.adminpanel;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.softoolshop.adminpanel.dto.OrderDetailDTO;
import com.softoolshop.adminpanel.entity.OrderDetail;

@SpringBootApplication
public class AdminpanelApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminpanelApplication.class, args);
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		return modelMapper;
	}
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
