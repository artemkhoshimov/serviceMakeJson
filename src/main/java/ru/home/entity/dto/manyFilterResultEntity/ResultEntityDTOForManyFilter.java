package ru.home.entity.dto.manyFilterResultEntity;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.home.entity.StreamOwnerProjectEntity;
import ru.home.entity.SystemEntity;
import ru.home.entity.dto.ownerStreamEntity.TextField;
import ru.home.entity.dto.systemEntity.MasterFieldSystem;
import ru.home.entity.dto.systemEntity.NameIdRisEntity;

import java.util.ArrayList;
import java.util.List;

public class ResultEntityDTOForManyFilter {

    String id;
    String title;
    List<TextField> fields;
    String url;
    String source;
    String type;
    String description;
    String owner;
    List<SystemEntity> systemEntityList;
    List<StreamOwnerProjectEntity> streamOwnerProjectEntityList;

    public ResultEntityDTOForManyFilter(String id, String title, List<TextField> fields, String url, String source,
                                        String type, String description, String owner, List<SystemEntity> systemEntityList,
                                        List<StreamOwnerProjectEntity> streamOwnerProjectEntityList) {
        this.id = id;
        this.title = title;
        this.fields = fields;
        this.url = url;
        this.source = source;
        this.type = type;
        this.description = description;
        this.owner = owner;
        this.systemEntityList = systemEntityList;
        this.streamOwnerProjectEntityList = streamOwnerProjectEntityList;
    }

    public JSONObject toJSON() {
        List<MasterFieldSystem> masterSystemResultField = getMasterFieldSystem(systemEntityList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", this.title);
        jsonObject.put("classType", this.type);
        jsonObject.put("description", this.description);
        jsonObject.put("URL", this.url);
        JSONArray masterResultFields = new JSONArray();
        jsonObject.put("masterResultFields", masterResultFields);
        for (MasterFieldSystem masterFieldSystem : masterSystemResultField) {
            masterResultFields.put(masterFieldSystem.toJSON());
        }

        return jsonObject;
    }

    List<MasterFieldSystem> getMasterFieldSystem(List<SystemEntity> systemEntityList) {
        List<MasterFieldSystem> masterFieldSystemList = new ArrayList<>();
        for (SystemEntity systemEntity : systemEntityList) {
            Integer masterSystemRelTypeId = Integer.parseInt(systemEntity.getRelTypeId());
            List<NameIdRisEntity> systemNameAndIdRis = new ArrayList<>();
            NameIdRisEntity nameIdRisEntity = new NameIdRisEntity(systemEntity.getName(), systemEntity.getIdRIS());
            systemNameAndIdRis.add(nameIdRisEntity);
            masterFieldSystemList.add(new MasterFieldSystem(masterSystemRelTypeId, systemNameAndIdRis));
        }
        return masterFieldSystemList;
    }

}
