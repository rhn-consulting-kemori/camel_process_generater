package com.redhat.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app")
public class AppConfig {
    
    /** Config Parameter */
    private String format_root_path;
    private String input_topic_name;
    private String output_topic_name;
}
