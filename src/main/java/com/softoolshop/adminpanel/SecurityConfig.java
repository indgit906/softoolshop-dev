package com.softoolshop.adminpanel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.softoolshop.adminpanel.service.CustomUserDetailsService;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;

	public SecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter)
			throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
				// Unsecured API's
				.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/products").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/orders/*").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/contact").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/client-info").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/images/qrcd/*").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/products/getdescription/*").permitAll()
				// Secured API's
				.requestMatchers(HttpMethod.POST, "/api/products/add", "/api/products/add/**", "/api/products/upd").authenticated()
				.requestMatchers(HttpMethod.GET, "/api/orders").authenticated()
				.requestMatchers(HttpMethod.POST, "/api/orders/complete").authenticated()
				.requestMatchers(HttpMethod.GET, "/api/contact").authenticated()
				.requestMatchers(HttpMethod.PUT, "/api/contact/*").authenticated()
				.anyRequest().permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}