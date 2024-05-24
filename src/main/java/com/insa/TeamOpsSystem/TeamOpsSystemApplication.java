package com.insa.TeamOpsSystem;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

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



