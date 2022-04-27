package ru.home.entity;

public class RelationshipField extends BaseEntityField {

    private Integer relTypeId;




    public RelationshipField(String id, String name, Integer relTypeId) {
        super(id, name);
        this.relTypeId = relTypeId;
    }

    public Integer getRelTypeId() {
        return relTypeId;
    }
}
