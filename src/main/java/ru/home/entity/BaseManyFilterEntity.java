package ru.home.entity;

public class BaseManyFilterEntity {
    private String id;
    private String relTypeId;
    private String name;

    public BaseManyFilterEntity(String id, String relTypeId, String name) {
        this.id = id;
        this.relTypeId = relTypeId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelTypeId() {
        return relTypeId;
    }

    public void setRelTypeId(String relTypeId) {
        this.relTypeId = relTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
