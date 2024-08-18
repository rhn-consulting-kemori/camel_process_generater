package com.redhat.example.rule;

import org.yaml.snakeyaml.Yaml;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

// Spring
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

// Business Object
import com.redhat.example.entity.ClassYamlCompEntity;
import com.redhat.example.entity.ClassYamlUnitEntity;
import com.redhat.example.entity.ClassYamlPropertyEntity;
import com.redhat.example.entity.ClassYamlClassSetEntity;
import com.redhat.example.config.AppConfig;

@Component
public class CreatePojoTestClassRule {
    // Config
    @Autowired
    private AppConfig appConfig;

    // Format
    private String FORMATPATH;
    private String FORMATFILENAME = "pojo.txt";
    private String class_format;

    // Inport List
    private String[] defaultImportList = {
        "static org.junit.jupiter.api.Assertions.assertEquals",
        "org.junit.jupiter.api.BeforeEach",
        "org.junit.jupiter.api.Test",
        "org.springframework.util.StringUtils",
        "java.lang.reflect.Field",
        "java.lang.reflect.Method",
        "java.math.BigDecimal",
        "java.util.ArrayList",
        "java.util.Date",
        "java.util.HashMap",
        "java.util.Map",
        "java.util.List"
    };

    /** Create PopJ Class */
    public List<ClassYamlClassSetEntity> createYamlClass(String entity_yaml) {

        // Format
        FORMATPATH = appConfig.getFormat_root_path() + "/testclass/";
        getClassFormat();

        // List
        List<ClassYamlClassSetEntity> class_set_list = new ArrayList<ClassYamlClassSetEntity>();

        // YAML Unmarshall
        Yaml yaml = new Yaml();
        ClassYamlCompEntity doc = yaml.loadAs(entity_yaml, ClassYamlCompEntity.class);

        for (ClassYamlUnitEntity unit_entity : doc.getClasses()) {
            class_set_list.add(getClassSet(unit_entity));
        }

        return class_set_list;
    }

    /** Class Unit */
    public ClassYamlClassSetEntity getClassSet(ClassYamlUnitEntity entity) {

        ClassYamlClassSetEntity class_set = new ClassYamlClassSetEntity();
        class_set.setClassName(entity.getName() + "Test");
        class_set.setPackageName(getFolderName(entity.getPackages()));
        // ----------------------------------------------------------------
        // Field,Type
        // ----------------------------------------------------------------
        List<String> property_type_list = getDefaultPropertyTypeList();
        List<String> add_property_type_list = new ArrayList<String>();
        List<String[]> property_list = new ArrayList<String[]>();

        for (ClassYamlPropertyEntity property_entity : entity.getProperties()) {

            // Property Name
            String property_type_name;
            if(property_entity.getType().contains("<")) {
                property_type_name = property_entity.getType().split("<")[0];
            } else if(property_entity.getType().contains("[")) {
                property_type_name = property_entity.getType().split("[")[0];
            } else {
                property_type_name = property_entity.getType();
            }

            // Add Type
            if(!property_type_list.contains(property_type_name)) {
                add_property_type_list.add(property_type_name);
                property_type_list.add(property_type_name);
            }

            // Property List
            String[] property_list_unit = {property_entity.getName(), property_type_name};
            property_list.add(property_list_unit);

        }
        

        // ----------------------------------------------------------------
        // Java code
        // ----------------------------------------------------------------
        String javaCode = "";
        
        // Package
        javaCode = javaCode + "package " + entity.getPackages() + ";\n\n";

        // Import
        /** Default */
        for (String default_import_str : defaultImportList) {
            javaCode = javaCode + "import " + default_import_str + ";\n";
        }
        /** Custom */
        for (String import_str : entity.getImport_list()) {
            for (String type_str : add_property_type_list) {
                if (import_str.contains(type_str)){
                    javaCode = javaCode + "import " + import_str + ";\n";
                }
            }
        }
        javaCode = javaCode + "\n";

        // Description
        javaCode = javaCode + "// Test POJO class:" + entity.getDescription() + "\n";

        // BeforeEach Property -> #expected_type_map_set#
        String beforeEach_code = "";
        for (String[] property_unit : property_list) {
            beforeEach_code = beforeEach_code + "\n        expected_type_map.put(\"" + property_unit[0] + "\", \"" + property_unit[1] + "\");";
        }

        // Field check code -> #custom_class_conditions#
        String property_code = "";
        for (String propertytype : add_property_type_list) {
            property_code = property_code + "else if (dataType.isAssignableFrom(" + propertytype + ".class)) {\n";
            property_code = property_code + "                    valueToSet = new " + propertytype + "();\n";
            property_code = property_code + "                } ";
        }

        // Class
        String class_str = class_format.replace("#class_name#", entity.getName()).replace("#expected_type_map_set#", beforeEach_code).replace("#custom_class_conditions#", property_code);
        javaCode = javaCode + class_str;

        class_set.setJavaCode(javaCode);
        return class_set;

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

    /** Get Class Format */
    public void getClassFormat() {
        File resource = null;
        try {
            Path path = Paths.get(FORMATPATH, FORMATFILENAME);
            class_format = Files.readString(path);
        } catch (IOException e1) {
            e1.printStackTrace();
        }            
    }

    /** Default Property Type List */
    public List<String> getDefaultPropertyTypeList() {
        List<String> property_type_list = new ArrayList<String>();
        property_type_list.add("String");
        property_type_list.add("Long");
        property_type_list.add("Integer");
        property_type_list.add("Double");
        property_type_list.add("Date");
        property_type_list.add("Boolean");
        property_type_list.add("string");
        property_type_list.add("long");
        property_type_list.add("int");
        property_type_list.add("double");
        property_type_list.add("date");
        property_type_list.add("boolean");
        property_type_list.add("BigDecimal");
        property_type_list.add("Map");
        property_type_list.add("List");

        return property_type_list;
    }
}
