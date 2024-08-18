package com.redhat.example.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

// Spring
import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Autowired;

// Business Object
import com.redhat.example.entity.CreateRouteJsonEntity;
import com.redhat.example.entity.ClassYamlClassSetEntity;
import com.redhat.example.config.AppConfig;

@Component
public class CreateBootApp {
    // Config
    @Autowired
    private AppConfig appConfig;

    // Path
    private String FORMATPATH;

    // Format
    private String bootapp_format;

    // Create pom.xml
    public ClassYamlClassSetEntity createBoot(CreateRouteJsonEntity entity) {
        FORMATPATH = appConfig.getFormat_root_path() + "/appenv/";
        setFormat();
        String bootapp_str = bootapp_format.replace("--package_name--", entity.getPackage_name());

        ClassYamlClassSetEntity bootapp_ent = new ClassYamlClassSetEntity();
        bootapp_ent.setClassName("CamelBootApplication");
        bootapp_ent.setPackageName(getFolderName(entity.getPackage_name()));
        bootapp_ent.setJavaCode(bootapp_str);

        return bootapp_ent;
    }

    /** Set Format */
    public void setFormat(){
        // Format Set
        File resource = null;
        try {
            Path path = Paths.get(FORMATPATH, "bootapp.txt");
            bootapp_format = Files.readString(path);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /** Folder Name */
    public String getFolderName (String package_name) {
        String foldername = "";
        String[] packagelist = package_name.split(Pattern.quote("."));
        for (int i = 0; i < packagelist.length; i++) {
            foldername = foldername + "/" + packagelist[i];
        }
        return foldername;
    }
}
