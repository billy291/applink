package bvbank.digimi.universal.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.fallback")
public class FallbackProperties {
    private String ios;
    private String android;
    private String desktop;
}