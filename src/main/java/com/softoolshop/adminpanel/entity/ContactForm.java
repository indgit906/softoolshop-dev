package com.softoolshop.adminpanel.entity;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@lombok.Data
@Entity
@Table(name = "contact_messages")
public class ContactForm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long enqId;

	private String name;
	private String email;
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String message;
	private short replyFlg;
	private String replyMessage;
    private LocalDateTime repliedAt;
	private LocalDateTime crtDate;
}
