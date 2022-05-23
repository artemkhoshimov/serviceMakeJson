package ru.home.entity.dto.systemEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MasterFieldSystem {

    Integer masterSystemRelTypeId;
    List<NameIdRisEntity> systemNameAndIdRis;

    public MasterFieldSystem(Integer masterSystemRelTypeId, List<NameIdRisEntity> systemNameAndIdRis) {
        this.masterSystemRelTypeId = masterSystemRelTypeId;
        this.systemNameAndIdRis = systemNameAndIdRis;
    }

    public Integer getMasterSystemRelTypeId() {
        return masterSystemRelTypeId;
    }

    public List<NameIdRisEntity> getSystemNameAndIdRis() {
        return systemNameAndIdRis;
    }
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("masterSystemRelTypeId", this.masterSystemRelTypeId == 1 ? "Мастер-система целевая" : "Мастер-система текущая");

        JSONArray listNameAndRisEntity = new JSONArray();
        jsonObject.put("systemNameAndIdRis", listNameAndRisEntity);
        for (NameIdRisEntity nameIdRisEntity : this.systemNameAndIdRis) {
            listNameAndRisEntity.put(nameIdRisEntity.toJSON());
        }
        return jsonObject;
    }
}
