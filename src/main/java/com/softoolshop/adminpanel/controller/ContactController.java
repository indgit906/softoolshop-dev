package com.softoolshop.adminpanel.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softoolshop.adminpanel.dto.ContactFormDTO;
import com.softoolshop.adminpanel.service.ContactFormService;
import com.softoolshop.adminpanel.service.EmailService;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
	
	@Autowired
	private ContactFormService cntctFormService;
	@Autowired
	private EmailService emailService;

	@PostMapping
    public ResponseEntity<ContactFormDTO> receiveMessage(@RequestBody ContactFormDTO contactForm) {
        
		ContactFormDTO response = cntctFormService.saveMessage(contactForm);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	@GetMapping
	public ResponseEntity<List<ContactFormDTO>> getAllMessages() {
		List<ContactFormDTO> response = cntctFormService.getAllMessages();
	    return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{enqId}")
    public ResponseEntity<?> replyToMessage(
            @PathVariable Long enqId,
            @RequestBody ContactFormDTO replyRequest) {
		Map<String, Object> response = cntctFormService.replyToMessage(enqId, replyRequest);
		return ResponseEntity.ok(response);
	}
	

}
