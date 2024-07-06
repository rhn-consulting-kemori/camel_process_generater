package com.redhat.example.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProcessEntity {
    private String process_name;
    private String process_name_japanese;
    private String process_type;
    private String process_request_set_parameter;

    // Bean
    private String rule_class;
    private String rule_method;

    // API
    private String http_url;
    private String http_method;
    private String response_entity_class;
    private String business_data;
}
