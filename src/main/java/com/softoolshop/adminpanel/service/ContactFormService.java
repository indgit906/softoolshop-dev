package com.softoolshop.adminpanel.service;

import java.util.List;
import java.util.Map;

import com.softoolshop.adminpanel.dto.ContactFormDTO;

public interface ContactFormService {

	ContactFormDTO saveMessage(ContactFormDTO contactForm);

	List<ContactFormDTO> getAllMessages();

	Map<String, Object> replyToMessage(Long enqId, ContactFormDTO replyRequest);

}
