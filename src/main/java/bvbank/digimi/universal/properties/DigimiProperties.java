package bvbank.digimi.universal.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.digimi")
public class DigimiProperties {
    private String idIos;
    private String idAndroid;
    private String urlSchemaIos;
    private String urlSchemaAndroid;
}