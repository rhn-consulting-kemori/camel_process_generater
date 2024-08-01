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

// Business Object
import com.redhat.example.entity.ClassYamlCompEntity;
import com.redhat.example.entity.ClassYamlUnitEntity;
import com.redhat.example.entity.ClassYamlPropertyEntity;
import com.redhat.example.entity.ClassYamlClassSetEntity;

@Component
public class CreatePojoTestClassRule {

    // Format
    private String FORMATPATH = "src/main/resources/testclass/";
    private String FORMATFILENAME = "pojo.txt";
    private String class_format;

    // Inport List
    private String[] defaultImportList = {
        "static org.junit.jupiter.api.Assertions.assertEquals",
        "org.junit.jupiter.api.Test",
        "org.springframework.util.StringUtils",
        "java.lang.reflect.Field",
        "java.lang.reflect.Method",
        "java.math.BigDecimal",
        "java.util.Date"
    };

    // todo: importの重複や不要なものを減らしたい
    // todo: Map<>のclass表記を改善

    /** Create PopJ Class */
    public List<ClassYamlClassSetEntity> createYamlClass(String entity_yaml) {

        // Format
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
        for (ClassYamlPropertyEntity property_entity : entity.getProperties()) {
            if(property_entity.getType().contains("<") || property_entity.getType().contains("[")) {
            } else {
                if(!property_type_list.contains(property_entity.getType())) {
                    add_property_type_list.add(property_entity.getType());
                    property_type_list.add(property_entity.getType());
                }
            }
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

        // Field check code
        String property_code = "";
        for (String propertytype : add_property_type_list) {
            property_code = property_code + "else if (dataType.isAssignableFrom(" + propertytype + ".class)) {\n";
            property_code = property_code + "                    valueToSet = new " + propertytype + "();\n";
            property_code = property_code + "                } ";
        }

        // Class
        String class_str = class_format.replace("#class_name#", entity.getName()).replace("#custom_class_conditions#", property_code);
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
        return property_type_list;
    }
}
