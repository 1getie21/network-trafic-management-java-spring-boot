package com.insa.TeamOpsSystem.jwt.until;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "application")
@Data
@Primary
public class ApplicationProps {
    private List<String> allowedOrigin;
}



