package bvbank.digimi.universal.controller;

import bvbank.digimi.universal.service.DeepLinkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URI;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DeepLinkController {
    private final DeepLinkService deepLinkService;

    // @GetMapping("/{path}")
    // public String handleDeepLink(HttpServletRequest servletRequest, Model model) {
    //     String deeplinkUrl = deepLinkService.getAppDeepLink(servletRequest);
    //     log.info("deeplinkUrl = {}", deeplinkUrl);
    //     model.addAttribute("deeplinkUrl", deeplinkUrl);
    //     return "index";
    // }

    // @GetMapping("/favicon.ico")
    // @ResponseBody
    // public void faviconBreak() {
    //     //log.info("favicon block");
    // }

    // @GetMapping(value = "/retrieve-id", produces = MediaType.TEXT_HTML_VALUE)
    // public String retrieveInstallId(HttpServletRequest servletRequest, Model model) throws Exception {
    //     model.addAttribute("deeplinkSchema", deepLinkService.getdigimiSchema(servletRequest));
    //     return "retrieve";
    // }

    private static final String ANDROID_PACKAGE = "vn.banvietbank.mobilebanking.uat";
    private static final String ANDROID_FALLBACK = "https://play.google.com/store/apps/details?id=" + ANDROID_PACKAGE;

    private static final String IOS_BUNDLE = "bvb.digimi";
    private static final String IOS_FALLBACK = "https://apps.apple.com/vn/app/id1526444697";

    private static final String WEB_URL = "https://bvbank.net.vn";

    @GetMapping("/smartlink")
    public ResponseEntity<Void> handleSmartLink(@RequestHeader(value = "User-Agent") String userAgent,
                                                @RequestHeader(value = "Referer", required = false) String referer,
                                                HttpServletResponse response) throws IOException {

        String redirectUrl;

        if (userAgent.toLowerCase().contains("android")) {
            // Android device
            redirectUrl = "intent://open/home#Intent;scheme=bvbankdigimi;package=" + ANDROID_PACKAGE + ";end";
        } else if (userAgent.toLowerCase().contains("iphone") || userAgent.toLowerCase().contains("ipad")) {
            // iOS device
            // Use universal link if your app is associated with the domain
            redirectUrl = WEB_URL;
            response.setHeader("Location", redirectUrl);
            response.setStatus(302);
            response.setHeader("apple-itunes-app", "app-id=1526444697");
            return ResponseEntity.status(302).build();
        } else {
            // Other platforms (desktop, unknown)
            redirectUrl = WEB_URL;
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }
}