package ru.home.entity;

public abstract class BaseEntityField {
    private String id;
    private String name;

    public BaseEntityField(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
