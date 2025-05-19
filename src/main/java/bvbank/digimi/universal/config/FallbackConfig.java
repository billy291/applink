package bvbank.digimi.universal.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.fallback")
public class FallbackConfig {
    private String ios;
    private String android;
    private String desktop;
}