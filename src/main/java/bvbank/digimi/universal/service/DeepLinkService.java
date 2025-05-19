package bvbank.digimi.universal.service;

import jakarta.servlet.http.HttpServletRequest;

public interface DeepLinkService {
    String getAppDeepLink(HttpServletRequest servletRequest);
    String getdigimiSchema();
}