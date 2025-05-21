package bvbank.digimi.universal.service.impl;

import bvbank.digimi.universal.properties.FallbackProperties;
import bvbank.digimi.universal.properties.DigimiProperties;
import bvbank.digimi.universal.service.DeepLinkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeepLinkServiceImpl implements DeepLinkService {
    private final DigimiProperties digimiProperties;
    private final FallbackProperties fallbackProperties;

    @Override
    public ResponseEntity<Void> handleDigimi(String userAgent, String referer, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        String redirectUrl = fallbackProperties.getDesktop();
        try{
            String extensionQueryPath = getExtensionQueryPath(servletRequest);
            if (userAgent.toLowerCase().contains("android")) {
                // Android device
                redirectUrl = String.format("intent://%s#Intent;scheme=%s;package=%s;end", extensionQueryPath, digimiProperties.getUrlSchemaAndroid(), digimiProperties.getIdAndroid());
            } else if (userAgent.toLowerCase().contains("iphone") || userAgent.toLowerCase().contains("ipad") || userAgent.toLowerCase().contains("ipod")) {
                // iOS device
                // Use universal link if your app is associated with the domain
                servletResponse.setHeader("Location", String.format("%s://%s", digimiProperties.getUrlSchemaIos(),extensionQueryPath));
                servletResponse.setStatus(302);
                servletResponse.setHeader("apple-itunes-app", String.format("app-id=%s", digimiProperties.getIdIos()));
                return ResponseEntity.status(302).build();
            }
        }catch (Exception ex){
            log.error("handleDigimi exception {}", ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }

    @Override
    public ResponseEntity<Void> handTest(String userAgent, String referer, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        String redirectUrl = fallbackProperties.getDesktop();
        try{
            String extensionQueryPath = getExtensionQueryPath(servletRequest);
            if (userAgent.toLowerCase().contains("android")) {
                // Android device
                redirectUrl = String.format("intent://%s#Intent;scheme=%s;package=%s.uat;end", extensionQueryPath, digimiProperties.getUrlSchemaAndroid(), digimiProperties.getIdAndroid());
            } else if (userAgent.toLowerCase().contains("iphone") || userAgent.toLowerCase().contains("ipad") || userAgent.toLowerCase().contains("ipod")) {
                // iOS device
                // Use universal link if your app is associated with the domain
                servletResponse.setHeader("Location", String.format("%s://%s", digimiProperties.getUrlSchemaIos(),extensionQueryPath));
                servletResponse.setStatus(302);
                servletResponse.setHeader("apple-itunes-app", String.format("app-id=%s", digimiProperties.getIdIos()));
                return ResponseEntity.status(302).build();
            }
        }catch (Exception ex){
            log.error("handleDigimi exception {}", ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }

    private String getExtensionQueryPath(HttpServletRequest servletRequest) {
        String fullPath = servletRequest.getRequestURI();
        String queryString = servletRequest.getQueryString();
        String pathExtension = queryString != null ? fullPath + "?" + queryString: fullPath;
        if (pathExtension == null || pathExtension.isEmpty() || pathExtension.startsWith("/null")) {
            return "open";
        }else if(pathExtension.startsWith("/")){
            pathExtension = pathExtension.substring(1);
        }
        return pathExtension;
    }
}