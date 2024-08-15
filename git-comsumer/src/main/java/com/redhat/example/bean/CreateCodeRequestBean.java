package com.redhat.example.bean;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Spring
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

// Config
import com.redhat.example.config.AppConfig;

// Entity
import com.redhat.example.entity.CodeRequestEntity;

@Component
public class CreateCodeRequestBean {

    @Autowired
    private AppConfig appConfig;
    
    // Bean Method
    public List<CodeRequestEntity> createRequest(String commit_message) {

        // Common items
        String activity_uml = getInputData("activity.uml");
        String business_object_yml = getInputData("business_object.yml");
        String create_id = getCreateId(commit_message);

        List<CodeRequestEntity> requestList = new ArrayList<CodeRequestEntity>();

        if(activity_uml.isEmpty()) {
        } else {
            CodeRequestEntity entity = new CodeRequestEntity();
            entity.setCreate_id(create_id);
            entity.setRemote_name(appConfig.getRemote_name());
            entity.setRepository_name(appConfig.getRepository_name());
            entity.setCode_type("route");
            entity.setInput_data(activity_uml);
            requestList.add(entity);
        }

        if(business_object_yml.isEmpty()) {
        } else {
            CodeRequestEntity entity = new CodeRequestEntity();
            entity.setCreate_id(create_id);
            entity.setRemote_name(appConfig.getRemote_name());
            entity.setRepository_name(appConfig.getRepository_name());
            entity.setCode_type("object");
            entity.setInput_data(business_object_yml);
            requestList.add(entity);
        }

        return requestList;

    }

    /** Get Input Data */
    public String getInputData(String filename){
        String FORMATPATH = appConfig.getRepository_folder() + "/" + appConfig.getRepository_name() + "/input/";
        File resource = null;
        try {
            Path path = Paths.get(FORMATPATH, filename);
            return Files.readString(path);
        } catch (IOException e1) {
            return "";
        }
    }

    /** Get CreateId */
    public String getCreateId(String commit_message) {
        LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formatNowDate = dtf3.format(nowDate);
        String create_id = "autocreate-" + appConfig.getRepository_name() + "-" + commit_message + "-" + formatNowDate;
        return create_id;
    }

}
