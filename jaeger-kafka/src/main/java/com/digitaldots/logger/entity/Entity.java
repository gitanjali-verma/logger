package com.digitaldots.logger.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Generated;

@Data
public class Entity {
    @Id
    @Generated
    private String id;
    private String name;
    private String description;
}
