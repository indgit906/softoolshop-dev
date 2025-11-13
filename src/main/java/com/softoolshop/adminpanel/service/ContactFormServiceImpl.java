package com.softoolshop.adminpanel.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.softoolshop.adminpanel.dto.ContactFormDTO;
import com.softoolshop.adminpanel.entity.ContactForm;
import com.softoolshop.adminpanel.repository.ContactFormRepository;

@Service
public class ContactFormServiceImpl implements ContactFormService {

	@Autowired
	private ContactFormRepository cntctRepo;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private EmailService emailService;

	@Override
	public ContactFormDTO saveMessage(ContactFormDTO contactForm) {
		ContactForm entity = modelMapper.map(contactForm, ContactForm.class);
		entity.setCrtDate(LocalDateTime.now());
		ContactForm savedEntity = cntctRepo.save(entity);

		if (savedEntity != null && savedEntity.getEnqId() != null && savedEntity.getEnqId() > 0) {
			ContactFormDTO responseDto = modelMapper.map(savedEntity, ContactFormDTO.class);
			return responseDto;
		}

		return null;
	}

	@Override
	public List<ContactFormDTO> getAllMessages() {
		List<ContactForm> entities = cntctRepo.findAll();
		return Arrays.asList(modelMapper.map(entities, ContactFormDTO[].class));
	}

	@Override
	public Map<String, Object> replyToMessage(Long enqId, ContactFormDTO replyRequest) {
		Optional<ContactForm> optionalContact = cntctRepo.findById(enqId);
		if (optionalContact.isEmpty()) {
			return Map.of("message", "Contact message not found");
		}
		ContactForm contact = optionalContact.get();
		contact.setReplyMessage(replyRequest.getReplyMessage());
		contact.setReplyFlg((short) 1);
		contact.setRepliedAt(LocalDateTime.now());
		// Optional: send reply email
		try {
			emailService.sendSimpleEmail(contact.getEmail(), replyRequest.getSubject(), replyRequest.getReplyMessage());
		} catch (Exception e) {
			return Map.of("message", "Failed to send email: " + e.getMessage());
		}

		cntctRepo.save(contact);
		return Map.of("message", "Reply sent successfully");

	}

}
