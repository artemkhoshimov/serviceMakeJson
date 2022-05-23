package ru.home.entity.dto.systemEntity;

import org.json.JSONObject;

public class NameIdRisEntity {

     String systemName;
     String idRis;

    public NameIdRisEntity(String systemName, String idRis) {
        this.systemName = systemName;
        this.idRis = idRis;
    }
    JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("systemName", this.systemName);
        jsonObject.put("idRis", this.idRis);
        return jsonObject;
    }

}
