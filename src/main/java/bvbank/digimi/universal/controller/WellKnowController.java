package bvbank.digimi.universal.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class WellKnowController {
    @GetMapping(value = "/.well-known/apple-app-site-association", produces = "application/json")
    @Cacheable(value = "wellKnownFiles", key = "'apple-app-site-association'")
    public String getAppleAppSiteAssociation() throws IOException {
        Resource resource = new ClassPathResource("static/.well-known/apple-app-site-association");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    @GetMapping(value = "/.well-known/assetlinks.json", produces = "application/json")
    @Cacheable(value = "wellKnownFiles", key = "'assetlinks.json'")
    public String getAssetLinks() throws IOException {
        Resource resource = new ClassPathResource("static/.well-known/assetlinks.json");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

//    @GetMapping(value = "/.well-known/apple-app-site-association", produces = "application/json")
//    @Cacheable(value = "wellKnownFiles", key = "'apple-app-site-association'")
//    public ResponseEntity<String> getAppleAppSiteAssociation() throws IOException {
//        log.info("Serving apple-app-site-association file");
//        Resource resource = new ClassPathResource("static/.well-known/apple-app-site-association");
//        String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
//        return ResponseEntity.ok()
//                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
//                .body(content);
//    }
//
//    @GetMapping(value = "/.well-known/assetlinks.json", produces = "application/json")
//    @Cacheable(value = "wellKnownFiles", key = "'assetlinks.json'")
//    public ResponseEntity<String> getAssetLinks() throws IOException {
//        log.info("Serving assetlinks.json file");
//        Resource resource = new ClassPathResource("static/.well-known/assetlinks.json");
//        String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
//        return ResponseEntity.ok()
//                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
//                .body(content);
//    }
}
