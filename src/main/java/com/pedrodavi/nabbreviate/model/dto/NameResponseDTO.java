package com.pedrodavi.nabbreviate.model.dto;

public class NameResponseDTO {

    private String name;

    public NameResponseDTO(String name) {
        this.name = name;
    }

    public NameResponseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NameResponseDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
