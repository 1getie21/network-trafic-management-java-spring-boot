package com.insa.TeamOpsSystem;

import com.insa.TeamOpsSystem.Files.StorageProperties;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
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



