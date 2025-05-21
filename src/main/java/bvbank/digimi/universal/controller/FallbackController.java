package bvbank.digimi.universal.controller;

import bvbank.digimi.universal.properties.FallbackProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FallbackController {

    private final FallbackProperties fallbackProperties;

    @GetMapping("/fallback/get-links")
    public Map<String, String> getAppLinks() {
        Map<String, String> links = new HashMap<>();
        links.put("ios", fallbackProperties.getIos());
        links.put("android", fallbackProperties.getAndroid());
        links.put("desktop", fallbackProperties.getDesktop());
        return links;
    }
}
