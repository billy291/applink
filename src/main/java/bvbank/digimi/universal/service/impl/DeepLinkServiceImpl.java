package bvbank.digimi.universal.service.impl;

import bvbank.digimi.universal.service.DeepLinkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeepLinkServiceImpl implements DeepLinkService {

    @Value("${app.digimi.url-schema}")
    private String digimiUrlSchema;

    @Override
    public String getAppDeepLink(HttpServletRequest servletRequest) {
        String fullPath = servletRequest.getRequestURI();
        String queryString = servletRequest.getQueryString();
        String pathExtension = queryString != null ? fullPath + "?" + queryString: fullPath;
        if (pathExtension == null || pathExtension.isEmpty()) {
            return digimiUrlSchema + "://open";
        }else if(pathExtension.startsWith("/")){
            pathExtension = pathExtension.substring(1);
        }
        return digimiUrlSchema + "://" + pathExtension;
    }

    @Override
    public String getdigimiSchema() {
        return digimiUrlSchema;
    }
}