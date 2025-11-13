package com.softoolshop.adminpanel.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/client-info")
public class ClientInfoController {

	@GetMapping
    public Map<String, String> getClientInfo(HttpServletRequest request) {
        Map<String, String> clientInfo = new HashMap<>();

        // Get client IP address
        String clientIp = extractClientIp(request);

        // Other client info from headers
        String userAgent = request.getHeader("User-Agent");
        String acceptLanguage = request.getHeader("Accept-Language");
        String requestMethod = request.getMethod();
        String requestUrl = request.getRequestURL().toString();

        try {
			String hostName = InetAddress.getByName(request.getRemoteAddr()).getHostName();
			String PC_NAME = InetAddress.getByName("127.0.0.1").getCanonicalHostName();

			clientInfo.put("PC_NAME", PC_NAME);
			clientInfo.put("Host-name", hostName);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        clientInfo.put("clientIp", clientIp);
        clientInfo.put("userAgent", userAgent != null ? userAgent : "unknown");
        clientInfo.put("acceptLanguage", acceptLanguage != null ? acceptLanguage : "unknown");
        clientInfo.put("requestMethod", requestMethod);
        clientInfo.put("requestUrl", requestUrl);

        return clientInfo;
    }

    // Helper method to extract client IP address correctly
    private String extractClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isEmpty()) {
            return xfHeader.split(",")[0]; // first IP in the list
        }
        return request.getRemoteAddr();
    }
	
}
