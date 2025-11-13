package com.softoolshop.adminpanel;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all endpoints
                .allowedOrigins("*") // Supports wildcards in Spring Boot 2.4+
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}

//@Configuration
//public class WebConfig {
//
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
//		corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
//		corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
//		corsConfiguration.setAllowCredentials(true);
//
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", corsConfiguration);
//		return source;
//	}
//}
