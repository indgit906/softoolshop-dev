package com.softoolshop.adminpanel.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softoolshop.adminpanel.dto.EncryptedRequest;

@RestController
@RequestMapping("/api/secure")
public class SecureDemoController {

	private static final String SECRET_KEY = "mysupersecretkeymysupersecretkey"; // 32 chars

    @PostMapping
    public String receiveEncrypted(@RequestBody EncryptedRequest request) {
        try {
            String decrypted = AESUtil.decrypt(request.getCiphertext(), request.getIv(), SECRET_KEY);
            return "Decrypted payload: " + decrypted;
        } catch (Exception e) {
            e.printStackTrace();
            return "Decryption failed: " + e.getMessage();
        }
    }
}
