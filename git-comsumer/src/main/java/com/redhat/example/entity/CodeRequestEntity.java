package com.redhat.example.entity;

import lombok.Data;

@Data
public class CodeRequestEntity {
    private String create_id;
    private String remote_name;
    private String repository_name;
    private String code_type;
    private String commit_message;
    private String input_data;
}
