package com.redhat.example.type;

import lombok.Data;
import java.util.List;
import org.springframework.stereotype.Component;
import com.redhat.example.entity.ClassYamlClassSetEntity;

@Data
@Component
public class GitPushRequestType {
    private String create_id;
    private String remote_name;
    private List<ClassYamlClassSetEntity> code_list;
}
