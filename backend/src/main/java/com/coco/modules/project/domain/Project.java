package com.coco.modules.project.domain;

import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
public class Project {

    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private String status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private OffsetDateTime archivaded_at;

}