package com.redhat.example.entity;

import lombok.Data;
import java.util.Map;
import java.util.List;

@Data
public class ClassYamlUnitEntity {
    private String name;
    private String description;
    private String packages;
    private List<String> import_list;
    private List<String> annotations;
    private List<ClassYamlPropertyEntity> properties;
}
