package com.redhat.example.entity;

import lombok.Data;
import java.util.List;

@Data
public class CreateRouteJsonEntity {
    private String service_name;
    private String service_name_japanese;
    private String from_end_point_uri;
    private String recieve_message_entity_class;
    private String to_end_point_uri;
    private List<ProcessEntity> process_list;
}
