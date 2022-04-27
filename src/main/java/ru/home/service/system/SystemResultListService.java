package ru.home.service.system;


import org.json.JSONArray;
import org.json.JSONObject;
import ru.home.entity.RelationshipField;
import ru.home.entity.SystemEntity;
import ru.home.entity.SystemField;

import java.util.ArrayList;
import java.util.List;


public class SystemResultListService {


//
//    public SystemResultListService() {
//    }
//
//    public List<SystemEntity> getSystemEntityList(JSONArray arrayFromCNST) {
//
//        List<SystemEntity> resultListSystems = new ArrayList<>();
//        List<RelationshipField> listRelationshipField;
//
//        for (Object item : arrayFromCNST) {
//            listRelationshipField = new ArrayList<>();
//            JSONObject jsonItem = (JSONObject) item;
//            String idSystem = (String) jsonItem.get("id");
//            JSONArray jsonValueOfSystem = jsonItem.getJSONArray("values");
//            String nameSystem = (String) jsonValueOfSystem.get(1);
//            String idRisSystem = (String) jsonValueOfSystem.get(25);
//            SystemField systemField = new SystemField(idSystem, nameSystem, idRisSystem);
//            if (jsonItem.has("relationships")) {
//                JSONArray jsonRelationships = (JSONArray) jsonItem.get("relationships");
//                for (Object relationship : jsonRelationships) {
//                    JSONObject jsonRelationship = (JSONObject) relationship;
//                    if (jsonRelationship.get("toFacet").equals("GLOSSARY")) {
//                        JSONArray jsonRels = (JSONArray) jsonRelationship.get("relationships");
//                        for (Object rel : jsonRels) {
//                            JSONObject jsonRel = (JSONObject) rel;
//                            String idRel = jsonRel.getString("id");
//                            String jsName = jsonRel.getString("relTypeName");
//                            Integer jsRelTypeId = jsonRel.getInt("relTypeId");
//                            RelationshipField fieldRel = new RelationshipField(idRel, jsName, jsRelTypeId);
//                            listRelationshipField.add(fieldRel);
//                        }
//                    }
//                }
//                resultListSystems.add(new SystemEntity(systemField, listRelationshipField));
//            }
//        }
//        return resultListSystems;
//    }


}
