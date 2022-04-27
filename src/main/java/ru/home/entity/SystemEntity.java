package ru.home.entity;

import java.util.List;

public class SystemEntity  extends BaseManyFilterEntity{


    private String idRIS;

    public SystemEntity(String id, String relTypeId, String name, String idRIS) {
        super(id, relTypeId, name);
        this.idRIS = idRIS;
    }

    public String getIdRIS() {
        return idRIS;
    }

    public void setIdRIS(String idRIS) {
        this.idRIS = idRIS;
    }
}
