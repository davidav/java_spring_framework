package com.example.declarativeclient.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EntityModel {
    private Long id;
    private String name;
    private Integer age;
    private List<EntityModel> models = new ArrayList<>();
}
