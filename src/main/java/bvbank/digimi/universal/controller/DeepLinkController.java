package bvbank.digimi.universal.controller;

import bvbank.digimi.universal.properties.DigimiProperties;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URI;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DeepLinkController {
    private final DeepLinkService deepLinkService;

    @GetMapping("/digimi/{path}")
    public ResponseEntity<Void> HandleDeepLinkDigimi(@RequestHeader(value = "User-Agent") String userAgent,
                                                     @RequestHeader(value = "Referer", required = false) String referer,
                                                     @PathVariable String path,
                                                     HttpServletRequest servletRequest,
                                                     HttpServletResponse servletResponse) throws IOException {

        return deepLinkService.handleDigimi(userAgent, referer, servletRequest, servletResponse);
    }

    // @GetMapping("/test/{path}")
    // public ResponseEntity<Void> HandleDeepLinkDigimiTest(@RequestHeader(value = "User-Agent") String userAgent,
    //                                                  @RequestHeader(value = "Referer", required = false) String referer,
    //                                                  @PathVariable String path,
    //                                                  HttpServletRequest servletRequest,
    //                                                  HttpServletResponse servletResponse) throws IOException {

    //     return deepLinkService.handleTest(userAgent, referer, servletRequest, servletResponse);
    // }
}
