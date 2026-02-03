package com.coco.modules.task.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskStatus {

    private String status;
    private String displayName;
    private String colorHex;
    private Short sortOrder;
    private Boolean isTerminal;

}
