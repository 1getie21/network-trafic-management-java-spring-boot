package com.insa.TeamOpsSystem;

import com.insa.TeamOpsSystem.Files.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class TeamOpsSystemApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TeamOpsSystemApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TeamOpsSystemApplication.class, args);
    }

}



