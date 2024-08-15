package com.redhat.example.entity;

import lombok.Data;
import java.util.List;

@Data
public class ClassYamlPropertyEntity {
    private String name;
    private String type;
    private String description;
    private List<String> annotations;
}
