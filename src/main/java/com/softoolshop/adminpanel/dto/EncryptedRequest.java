package com.softoolshop.adminpanel.dto;

@lombok.Data
public class EncryptedRequest {

	private String iv;
    private String ciphertext;
}
