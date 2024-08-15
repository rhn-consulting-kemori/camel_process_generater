package com.redhat.example.entity;

import lombok.Data;

@Data
public class TestRouteDataUnitEntity {
    private String process_name;
    private String process_json_name;
    private String process_request_name;
    private String process_request_class;
    private String process_request_import;
    private String process_response_name;
    private String process_response_class;
    private String process_response_import;
    private String process_response_businessdata;

    // Constructor
    public TestRouteDataUnitEntity(
        String process_name, String process_json_name, 
        String process_request_name, String process_request_class, String process_request_import,
        String process_response_name, String process_response_class, String process_response_import, String process_response_businessdata) {
            this.process_name = process_name;
            this.process_json_name = process_json_name;
            this.process_request_name = process_request_name;
            this.process_request_class = process_request_class;
            this.process_request_import = process_request_import;
            this.process_response_name = process_response_name;
            this.process_response_class = process_response_class;
            this.process_response_import = process_response_import;
            this.process_response_businessdata = process_response_businessdata;
    }
}
