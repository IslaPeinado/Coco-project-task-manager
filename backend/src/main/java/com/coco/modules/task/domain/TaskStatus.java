package com.coco.modules.task.domain;


import jakarta.persistence.Entity;


@Entity
public class TaskStatus {

    private String status;
    
    private String display_name;
    
    private String color_hex;
    
    private Short sort_order;
    
    private Boolean is_terminal;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getColor_hex() {
        return color_hex;
    }

    public void setColor_hex(String color_hex) {
        this.color_hex = color_hex;
    }

    public Short getSort_order() {
        return sort_order;
    }

    public void setSort_order(Short sort_order) {
        this.sort_order = sort_order;
    }

    public Boolean getIs_terminal() {
        return is_terminal;
    }

    public void setIs_terminal(Boolean is_terminal) {
        this.is_terminal = is_terminal;
    }

    
}