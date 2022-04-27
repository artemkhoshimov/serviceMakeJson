package ru.home.entity;

public class SystemField extends BaseEntityField {

    String idRis;


    public SystemField(String id, String name, String idRis) {
        super(id, name);
        this.idRis = idRis;
    }

    public String getIdRis() {
        return idRis;
    }
}
