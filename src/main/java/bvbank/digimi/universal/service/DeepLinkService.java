package bvbank.digimi.universal.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface DeepLinkService {
    void redirectUnknown(HttpServletResponse response) throws IOException;
    ResponseEntity<Void> handleDigimi(String userAgent, String referer, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
}
