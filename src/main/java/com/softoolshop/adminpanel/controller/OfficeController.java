package com.softoolshop.adminpanel.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softoolshop.adminpanel.entity.Office;
import com.softoolshop.adminpanel.service.OfficeService;

@RestController
@RequestMapping("/api/offices")
public class OfficeController {
	
	@Autowired
	private OfficeService officeService;

	@GetMapping("/{officeId}")
    public ResponseEntity<Map<String, Object>> getOfficeById(@PathVariable Integer officeId) {
		return ResponseEntity.ok(officeService.getOfficeById(officeId));
    }
}
