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
                log.info("Android - {}", redirectUrl);
            } else if (userAgent.toLowerCase().contains("iphone") || userAgent.toLowerCase().contains("ipad") || userAgent.toLowerCase().contains("ipod")) {
                // iOS device
                // Use universal link if your app is associated with the domain
                redirectUrl = String.format("%s://%s", digimiProperties.getUrlSchemaIos(),extensionQueryPath);
                log.info("iOS - {}", redirectUrl);
                servletResponse.setHeader("Location", redirectUrl);
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
    public ResponseEntity<Void> handleTest(String userAgent, String referer, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        String redirectUrl = fallbackProperties.getDesktop();
        try{
            String extensionQueryPath = getExtensionQueryPath(servletRequest);
            if (userAgent.toLowerCase().contains("android")) {
                // Android device
                redirectUrl = String.format("intent://%s#Intent;scheme=%s;package=%s.uat;end", extensionQueryPath, digimiProperties.getUrlSchemaAndroid(), digimiProperties.getIdAndroid());
                log.info("Android - {}", redirectUrl);
            } else if (userAgent.toLowerCase().contains("iphone") || userAgent.toLowerCase().contains("ipad") || userAgent.toLowerCase().contains("ipod")) {
                // iOS device
                // Use universal link if your app is associated with the domain
                redirectUrl = String.format("%s://%s", digimiProperties.getUrlSchemaAndroid(),extensionQueryPath);
                log.info("iOS - {}", redirectUrl);
                servletResponse.setHeader("Location", redirectUrl);
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