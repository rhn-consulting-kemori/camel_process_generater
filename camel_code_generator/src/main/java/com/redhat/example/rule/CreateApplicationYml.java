package com.redhat.example.rule;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Spring
import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;

// Business Object
import com.redhat.example.entity.CreateRouteJsonEntity;
import com.redhat.example.entity.ProcessEntity;

@Component
public class CreateApplicationYml {
    // Path
    private String FORMATPATH = "src/main/resources/appenv/";

    // Format
    private String server_format;
    private String kafka_format;

    // List
    List<String> parameterList;

    // Create Application YML
    public String createAppYml(CreateRouteJsonEntity entity) {
        setFormat();

        // Server
        String appymlstr = server_format + "\n\n";

        // Kafka
        if(entity.getFrom_end_point_uri().contains("kafka") || entity.getTo_end_point_uri().contains("kafka")) {
            appymlstr = appymlstr + kafka_format + "\n\n";
        }

        // Application Env
        appymlstr = appymlstr + "app:\n";

        parameterList = new ArrayList<>();
        if(getParameter(entity.getFrom_end_point_uri()).length() > 0) {
            parameterList.add(getParameter(entity.getFrom_end_point_uri()));
        }
        if(getParameter(entity.getTo_end_point_uri()).length() > 0) {
            parameterList.add(getParameter(entity.getTo_end_point_uri()));
        }
        if(getParameter(entity.getRecieve_message_entity_class()).length() > 0) {
            parameterList.add(getParameter(entity.getRecieve_message_entity_class()));
        }
        
        for (ProcessEntity process_obj : entity.getProcess_list()) {
            setParameterList(process_obj);
        }
        
        for (String parameter_obj : parameterList) {
            appymlstr = appymlstr + "  " + parameter_obj + ": ${" + parameter_obj.replace("-", "_").toUpperCase() + "}\n";
        }

        return appymlstr;
    }

    /** Set Format */
    public void setFormat(){
        // Format Set
        File resource = null;
        try {
            Path path = Paths.get(FORMATPATH, "server.txt");
            server_format = Files.readString(path);
            path = Paths.get(FORMATPATH, "kafka.txt");
            kafka_format = Files.readString(path);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /** Serch Parameter */
    public void setParameterList(ProcessEntity entity) {
        
        List<String> targetList = new ArrayList<>();
        targetList.add(entity.getProcess_request_set_parameter());
        targetList.add(entity.getProcess_request_set_parameter_error());
        targetList.add(entity.getRule_class());
        targetList.add(entity.getRule_method());
        targetList.add(entity.getHttp_url());
        targetList.add(entity.getHttp_method());
        targetList.add(entity.getResponse_entity_class());
        targetList.add(entity.getBusiness_data());

        for(String target_str : targetList) {
            if(getParameter(target_str).length() > 0) {
                parameterList.add(getParameter(target_str));
            }
        }
    }

    /** Get Parameter */
    public String getParameter(String str) {
        if(str == null) {
            return "";
        } else {
            String str_pre[] = str.split("app.");
            if (str_pre.length > 1) {
                String str_after[] = str_pre[1].split("}}");
                return str_after[0];
            } else {
                return "";
            }
        }
    }

}
