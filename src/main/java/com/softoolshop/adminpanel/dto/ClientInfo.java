package com.softoolshop.adminpanel.dto;

@lombok.Data
public class ClientInfo {

	private String clientIp;
	private String userAgent;
	private String acceptLanguage;
	private String requestMethod;
	private String requestUrl;
}
