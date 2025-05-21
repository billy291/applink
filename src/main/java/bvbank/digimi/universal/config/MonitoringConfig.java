package bvbank.digimi.universal.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class MonitoringConfig {
    @Bean
    public HealthIndicator wellKnownFilesHealthIndicator() {
        return () -> {
            try {
                Resource appleResource = new ClassPathResource("static/.well-known/apple-app-site-association");
                Resource androidResource = new ClassPathResource("static/.well-known/assetlinks.json");
                
                if (!appleResource.exists() || !androidResource.exists()) {
                    return Health.down()
                            .withDetail("apple-app-site-association", appleResource.exists())
                            .withDetail("assetlinks.json", androidResource.exists())
                            .build();
                }
                
                return Health.up().build();
            } catch (Exception e) {
                return Health.down(e).build();
            }
        };
    }
} 