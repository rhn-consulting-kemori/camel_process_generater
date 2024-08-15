package com.redhat.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app")
public class AppConfig {
    
    /** Config Parameter */
    private String repository_name;
    private String branch_name;
    private String remote_name;
    private String repository_folder;
    private String git_user;
    private String git_passwd;
}
