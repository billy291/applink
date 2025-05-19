package bvbank.digimi.universal.controller;

import bvbank.digimi.universal.service.DeepLinkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Slf4j
@Controller
@RequiredArgsConstructor
public class DeepLinkController {
    private final DeepLinkService deepLinkService;

    @GetMapping("/{path}")
    public String handleDeepLink(HttpServletRequest servletRequest, Model model) {
        String deeplinkUrl = deepLinkService.getAppDeepLink(servletRequest);
        log.info("deeplinkUrl = {}", deeplinkUrl);
        model.addAttribute("deeplinkUrl", deeplinkUrl);
        return "index";
    }

    @GetMapping("/favicon.ico")
    @ResponseBody
    public void faviconBreak() {
        //log.info("favicon block");
    }

    @GetMapping(value = "/retrieve-id", produces = MediaType.TEXT_HTML_VALUE)
    public String retrieveInstallId(Model model) throws Exception {
        model.addAttribute("deeplinkSchema", deepLinkService.getdigimiSchema());
        return "retrieve";
    }
}