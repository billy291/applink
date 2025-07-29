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

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeepLinkServiceImpl implements DeepLinkService {
    private final DigimiProperties digimiProperties;
    private final FallbackProperties fallbackProperties;

    @Override
    public void redirectUnknown(HttpServletResponse response) throws IOException {
        response.sendRedirect(fallbackProperties.getDesktop());
    }

    @Override
    public ResponseEntity<Void> handleDigimi(String userAgent, String referer, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        String redirectUrl = fallbackProperties.getDesktop();
        try{
            String extensionQueryPath = getExtensionQueryPath(servletRequest);
            if (userAgent.toLowerCase().contains("android")) {
                // Android device
                redirectUrl = String.format("intent://%s#Intent;scheme=%s;package=%s;end", extensionQueryPath, digimiProperties.getUrlSchemaAndroid(), digimiProperties.getIdAndroid());

                String fallbackUrl = "https://play.google.com/store/apps/details?id=" + digimiProperties.getIdAndroid();

                redirectUrl = String.format(
                        "intent://%s#Intent;scheme=%s;package=%s;S.browser_fallback_url=%s;end",
                        extensionQueryPath,
                        digimiProperties.getUrlSchemaAndroid(),
                        digimiProperties.getIdAndroid(),
                        URLEncoder.encode(fallbackUrl, StandardCharsets.UTF_8)
                );
                log.info("Android intent with fallback - {}", redirectUrl);


            } else if (userAgent.toLowerCase().contains("iphone") || userAgent.toLowerCase().contains("ipad") || userAgent.toLowerCase().contains("ipod")) {
                // iOS device
                // Use universal link if your app is associated with the domain
//                servletResponse.setHeader("Location", String.format("%s://%s", digimiProperties.getUrlSchemaIos(),extensionQueryPath));
//                servletResponse.setStatus(302);
//                servletResponse.setHeader("apple-itunes-app", String.format("app-id=%s", digimiProperties.getIdIos()));
//                return ResponseEntity.status(302).build();

                servletResponse.setHeader("Location", fallbackProperties.getIos());
                servletResponse.setStatus(HttpStatus.FOUND.value());
                return ResponseEntity.status(HttpStatus.FOUND).build();
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
