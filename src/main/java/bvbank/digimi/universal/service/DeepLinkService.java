package bvbank.digimi.universal.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface DeepLinkService {
    ResponseEntity<Void> handleDigimi(String userAgent, String referer, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
    ResponseEntity<Void> handleTest(String userAgent, String referer, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
}