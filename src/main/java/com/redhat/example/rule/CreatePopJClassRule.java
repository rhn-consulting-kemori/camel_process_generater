package com.redhat.example.rule;

import org.yaml.snakeyaml.Yaml;
import java.util.List;
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
public class CreatePopJClassRule {

    /** Create PopJ Class */
    public List<ClassYamlClassSetEntity> createYamlClass(String entity_yaml) {

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
        class_set.setClassName(entity.getName());
        class_set.setPackageName(getFolderName(entity.getPackages()));

        String javaCode = "";
        
        // Package
        javaCode = javaCode + "package " + entity.getPackages() + ";\n\n";

        // Import
        for (String import_str : entity.getImport_list()) {
            javaCode = javaCode + "import " + import_str + ";\n";
        }
        javaCode = javaCode + "\n";

        // Description
        javaCode = javaCode + "// " + entity.getDescription() + "\n";

        // Annotations
        for (String annotation_str : entity.getAnnotations()) {
            javaCode = javaCode + "@" + annotation_str + "\n";
        }

        // Class
        javaCode = javaCode + "public class " + entity.getName() + " {\n\n";

        // Properties
        for (ClassYamlPropertyEntity property_entity : entity.getProperties()) {
            if (property_entity.getDescription() != null && property_entity.getDescription().length() > 0) {
                javaCode = javaCode + "    /** " + property_entity.getDescription() + " */\n";
            }
            if (property_entity.getAnnotations() != null) {
                for (String annotation_str : property_entity.getAnnotations()) {
                    javaCode = javaCode + "    @" + annotation_str + "\n";
                }
            }
            javaCode = javaCode + "    private " + property_entity.getType() + " " + property_entity.getName() + ";\n\n";
        }

        // Class
        javaCode = javaCode + "}\n";

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
}
