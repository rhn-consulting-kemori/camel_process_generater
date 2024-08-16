package com.redhat.example.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ClassYamlCompEntity {
    private List<ClassYamlUnitEntity> classes;
}
