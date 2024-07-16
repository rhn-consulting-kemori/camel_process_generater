package com.redhat.example.rule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// Spring
import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;

// Business Object
import com.redhat.example.entity.CreateRouteJsonEntity;
import com.redhat.example.entity.ProcessEntity;

@Component
public class CreateProcessRule {
    
    // Test Path
    private String FORMATPATH = "src/main/resources/format/";

    // Create Process
    public String createProcess(CreateRouteJsonEntity entity) {
        
        // Format set
        Map<String, String> format_map = getFormat();

        // Create
        String process_camel_route = "";

        // Process Route
        process_camel_route = process_camel_route + getProcessRoute(entity, format_map);

        // Initial Process
        process_camel_route = process_camel_route + "\n" + getInitialProcess(entity, format_map);
        
        // Business Process
        process_camel_route = process_camel_route + getBusinessProcess(entity, format_map);

        // Finish Process
        process_camel_route = process_camel_route + "\n" + format_map.get("finish-process");

        // Return YAML
        return process_camel_route;

    }

    // Read Format
    public Map<String, String> getFormat() {

        // Map Initiating
        Map<String, String> format_map = new HashMap();

        // Set
        String formatList[] = {"process-route", "process-direct", "initial-process", "finish-process", "bean-route", "bean-end-route","api-route", "to-route"};
        File resource = null;

        for (String format: formatList){
            try {
                Path path = Paths.get(FORMATPATH, format + ".txt");
                format_map.put(format, Files.readString(path));
            } catch (IOException e1) {
                e1.printStackTrace();
            }            
        }

        return format_map;
    }

    // Process Route
    public String getProcessRoute(CreateRouteJsonEntity entity, Map<String, String> format_map) {

        // From
        String processRouteFormat = format_map.get("process-route");
        String processRouteString = 
            processRouteFormat.replace("#service_name#", entity.getService_name())
                .replace("#service_name_japanese#", entity.getService_name_japanese())
                .replace("#from_end_point_uri#", entity.getFrom_end_point_uri());

        // Start
        processRouteString = processRouteString + "\n" + getProcessDirect("initial-process", "開始業務", format_map.get("process-direct"));

        // Process List
        for (ProcessEntity processent: entity.getProcess_list()){
            processRouteString = processRouteString + "\n" + getProcessDirect(processent.getProcess_name(), processent.getProcess_name_japanese(), format_map.get("process-direct"));
        }

        // To
        processRouteFormat = format_map.get("to-route");
        String processToString = 
            processRouteFormat.replace("#service_name#", entity.getService_name())
                .replace("#service_name_japanese#", entity.getService_name_japanese())
                .replace("#to_end_point_uri#", entity.getTo_end_point_uri());
        processRouteString = processRouteString + "\n" + processToString;

        // End
        processRouteString = processRouteString + "\n" + getProcessDirect("finish-process", "完了業務", format_map.get("process-direct"));

        return processRouteString;
        
    }

    // Process Direct
    public String getProcessDirect(String process_name, String process_name_japanese, String processFormat) {

        // Replace
        String processString = 
            processFormat.replace("#process_name#", process_name)
                .replace("#process_name_japanese#", process_name_japanese);

        return processString;
    }

    // Initial Process
    public String getInitialProcess(CreateRouteJsonEntity entity, Map<String, String> format_map) {
        String processFormat = format_map.get("initial-process");
        String processString = 
            processFormat.replace("#recieve_message_entity_class#", entity.getRecieve_message_entity_class());
        return processString;
    }

    // Business Process
    public String getBusinessProcess(CreateRouteJsonEntity entity, Map<String, String> format_map) {

        // Format
        String processBeanFormat = format_map.get("bean-route");
        String processApiFormat = format_map.get("api-route");
        String processBeanEndFormat = format_map.get("bean-end-route");

        // For Loop
        String processString = "";

        for (ProcessEntity processent: entity.getProcess_list()){
            if(processent.getProcess_type().equals("bean")) {
                processString = processString + "\n" + getBeanProcess(processent, processBeanFormat);
            } else if(processent.getProcess_type().equals("api")) {
                processString = processString + "\n" + getApiProcess(processent, processApiFormat);
            } else if(processent.getProcess_type().equals("bean-end")) {
                processString = processString + "\n" + getBeanEndProcess(processent, processBeanEndFormat);
            } else {
                System.out.println("skip type: " + processent.getProcess_type());
            }
        }

        return processString;
    }
    
    // Bean Process
    public String getBeanProcess(ProcessEntity process_entity, String format) {
        String processString = 
            format.replace("#process_name#", process_entity.getProcess_name())
            .replace("#process_name_japanese#", process_entity.getProcess_name_japanese())
            .replace("#process_request_set_parameter#", "'" + process_entity.getProcess_request_set_parameter() + "'")
            .replace("#rule_class#", process_entity.getRule_class())
            .replace("#rule_method#", process_entity.getRule_method());

        return processString;
    }

    // API Process
    public String getApiProcess(ProcessEntity process_entity, String format) {
        String processString = 
            format.replace("#process_name#", process_entity.getProcess_name())
            .replace("#process_name_japanese#", process_entity.getProcess_name_japanese())
            .replace("#process_request_set_parameter#", "'" + process_entity.getProcess_request_set_parameter() + "'")
            .replace("#http_url#", process_entity.getHttp_url())
            .replace("#http_method#", process_entity.getHttp_method())
            .replace("#response_entity_class#", process_entity.getResponse_entity_class())
            .replace("#business_data#", process_entity.getBusiness_data()); 
        
        return processString;
    }

    // Bean End Process
    public String getBeanEndProcess(ProcessEntity process_entity, String format) {
        String processString = 
            format.replace("#process_name#", process_entity.getProcess_name())
            .replace("#process_name_japanese#", process_entity.getProcess_name_japanese())
            .replace("#process_request_set_parameter#", "'" + process_entity.getProcess_request_set_parameter() + "'")
            .replace("#process_request_set_parameter_error#", "'" + process_entity.getProcess_request_set_parameter_error() + "'")
            .replace("#rule_class#", process_entity.getRule_class())
            .replace("#rule_method#", process_entity.getRule_method())
            .replace("#response_entity_class#", process_entity.getResponse_entity_class());

        return processString;
    }
}
