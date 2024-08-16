package com.redhat.example.processor;

import java.util.ArrayList;
import java.util.List;

// Camel
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
// Spring
import org.springframework.stereotype.Component;

// Business Object
import com.redhat.example.entity.ClassYamlClassSetEntity;
import com.redhat.example.entity.CreateEnvSetEntity;
import com.redhat.example.type.GitPushRequestType;

@Component
public class GitPushRequestProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        /** Object */
        GitPushRequestType result_message = new GitPushRequestType();
        List<ClassYamlClassSetEntity> code_list = new ArrayList<ClassYamlClassSetEntity>();

        /** Exchange Property */
        String create_id = exchange.getProperty("create_id", String.class);
        result_message.setCreate_id(create_id);
        
        String remote_name = exchange.getProperty("remote_name", String.class);
        result_message.setRemote_name(remote_name);

        // code_type
        String code_type = exchange.getProperty("code_type", String.class);

        // List
        if (code_type.equals("route")) {
            // route
            /** camel_create.json */
            String create_route_json = exchange.getProperty("create_route_json", String.class);
            code_list.add(setStringEntity("camel_create.json", "src/main/resources/json", create_route_json));

            /** result-route.camel.yaml */
            String camel_route = exchange.getProperty("camel_route", String.class);
            code_list.add(setStringEntity("result-route.camel.yaml", "src/main/resources/camel", camel_route));

            /** application.yaml */
            String application_yml = exchange.getProperty("application_yml", String.class);
            code_list.add(setStringEntity("application.yaml", "src/main/resources", application_yml));

            /** CamelBootApplication.java */
            ClassYamlClassSetEntity boot_app = exchange.getProperty("boot_app", ClassYamlClassSetEntity.class);
            boot_app.setClassName(boot_app.getClassName() + ".java");
            boot_app.setPackageName("src/main/java" + boot_app.getPackageName());
            code_list.add(boot_app);

            /** pom.xml, Containerfile */
            List<CreateEnvSetEntity> pom_xml_containerfile = exchange.getProperty("pom_xml", List.class);
            for(CreateEnvSetEntity value : pom_xml_containerfile) {
                ClassYamlClassSetEntity entity = new ClassYamlClassSetEntity();
                entity.setClassName(value.getFilename());
                entity.setPackageName("");
                entity.setJavaCode(value.getContext());
                code_list.add(entity);
            }

            /** route test class */
            List<ClassYamlClassSetEntity> route_test = exchange.getProperty("route_test", List.class);
            for(ClassYamlClassSetEntity route_test_unit : route_test) {
                ClassYamlClassSetEntity entity = new ClassYamlClassSetEntity();
                entity.setClassName(route_test_unit.getClassName() + ".java");
                entity.setPackageName("src/test/java" + route_test_unit.getPackageName());
                entity.setJavaCode(route_test_unit.getJavaCode());
                code_list.add(entity);
            }
            
        } else if(code_type.equals("object")) {
            // object
            /** object class */ 
            List<ClassYamlClassSetEntity> object_class = exchange.getProperty("object_class", List.class);
            for(ClassYamlClassSetEntity object_class_unit : object_class) {
                ClassYamlClassSetEntity entity = new ClassYamlClassSetEntity();
                entity.setClassName(object_class_unit.getClassName() + ".java");
                entity.setPackageName("src/main/java" + object_class_unit.getPackageName());
                entity.setJavaCode(object_class_unit.getJavaCode());
                code_list.add(entity);
            }

            /** object test class */ 
            List<ClassYamlClassSetEntity> object_test_class = exchange.getProperty("object_test_class", List.class);
            for(ClassYamlClassSetEntity object_test_class_unit : object_test_class) {
                ClassYamlClassSetEntity entity = new ClassYamlClassSetEntity();
                entity.setClassName(object_test_class_unit.getClassName() + ".java");
                entity.setPackageName("src/test/java" + object_test_class_unit.getPackageName());
                entity.setJavaCode(object_test_class_unit.getJavaCode());
                code_list.add(entity);
            }
        } else {
        }

        result_message.setCode_list(code_list);

        /** Exchange OUT */
        exchange.getMessage().setBody(result_message);
    }

    // Set String Entity
    public ClassYamlClassSetEntity setStringEntity(String className, String packageName, String JavaCode){
        ClassYamlClassSetEntity entity = new ClassYamlClassSetEntity();
        entity.setClassName(className);
        entity.setPackageName(packageName);
        entity.setJavaCode(JavaCode);
        return entity;
    }
}
