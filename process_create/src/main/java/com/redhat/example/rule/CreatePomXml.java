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
import com.redhat.example.entity.CreateEnvSetEntity;

@Component
public class CreatePomXml {
    // Path
    private String FORMATPATH = "src/main/resources/appenv/";

    // Format
    private String pom_format;
    private String container_format;
    private String bootapp_format;

    // Create pom.xml
    public List<CreateEnvSetEntity> createPom(CreateRouteJsonEntity entity) {
        List<CreateEnvSetEntity> list = new ArrayList<CreateEnvSetEntity>();

        setFormat();
        String pom_str = pom_format.replace("--package_name--", entity.getPackage_name()).replace("--application_name--", entity.getService_name());
        String container_str = container_format.replace("--application_name--", entity.getService_name());
        String bootapp_str = bootapp_format.replace("--package_name--", entity.getPackage_name());

        CreateEnvSetEntity pom_ent = new CreateEnvSetEntity();
        pom_ent.setFilename("pom.xml");
        pom_ent.setContext(pom_str);

        CreateEnvSetEntity container_ent = new CreateEnvSetEntity();
        container_ent.setFilename("Containerfile");
        container_ent.setContext(container_str);

        CreateEnvSetEntity bootapp_ent = new CreateEnvSetEntity();
        bootapp_ent.setFilename("CamelBootApplication.java");
        bootapp_ent.setContext(bootapp_str);

        list.add(pom_ent);
        list.add(container_ent);

        return list;
    }

    /** Set Format */
    public void setFormat(){
        // Format Set
        File resource = null;
        try {
            Path path = Paths.get(FORMATPATH, "pom.txt");
            pom_format = Files.readString(path);
            path = Paths.get(FORMATPATH, "container.txt");
            container_format = Files.readString(path);
            path = Paths.get(FORMATPATH, "bootapp.txt");
            bootapp_format = Files.readString(path);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
