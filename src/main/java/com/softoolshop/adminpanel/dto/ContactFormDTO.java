package com.softoolshop.adminpanel.dto;

import java.util.Date;

@lombok.Data
public class ContactFormDTO {

	private String name;
    private String email;
    private String subject;
    private String message;
    private Date crtDate;
    private short replyFlg; 
    private String replyMessage;
}
