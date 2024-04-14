package ca.uqtr.dmi.messagerielogic.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "inf1013.jwt")
//dans le fichier application.properties, toutes les propriétés qui commencent
//par inf1013.jwt seront injectées dans cet objet comme des propriétés de cet objet
@Getter
@Setter
@NoArgsConstructor
public class

JwtConfigBean {
    private String secret;
    private String tokenPrefix;
    private String tokenHeader;
    private int tokenExpiration;
    private String tokenIssuer;
}