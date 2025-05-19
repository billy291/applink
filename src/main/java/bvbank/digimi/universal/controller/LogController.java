package bvbank.digimi.universal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/write-log")
public class LogController {

    @PostMapping
    public void logMessage(@RequestParam String message, @RequestParam(required = false) String level) {
        switch (level != null ? level.toLowerCase() : "info") {
            case "debug":
                log.debug(message);
                break;
            case "warn":
                log.warn(message);
                break;
            case "error":
                log.error(message);
                break;
            default:
                log.info(message);
                break;
        }
    }
}