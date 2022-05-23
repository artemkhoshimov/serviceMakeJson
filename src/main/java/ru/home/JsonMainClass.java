package ru.home;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.home.entity.StreamOwnerProjectEntity;
import ru.home.entity.SystemEntity;
import ru.home.entity.dto.manyFilterResultEntity.ResultEntityDTOForManyFilter;
import ru.home.entity.dto.ownerStreamEntity.ResultDTOOwnerStreamEntity;
import ru.home.entity.dto.ownerStreamEntity.TextField;
import ru.home.fileService.FileService;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class JsonMainClass {
    static List<StreamOwnerProjectEntity> streamOwnerProjectEntityList;
    static List<StreamOwnerProjectEntity> streamOwnerPrivateProjectEntityList;
    static List<SystemEntity> systemEntityList;
    static List<SystemEntity> systemPrivateEntityList;
    static int numberGlossary = 0;
    static List<ResultEntityDTOForManyFilter> resultDTOManyFilterList;
    static JSONArray resultArray = new JSONArray();


    public static void main(String[] args) throws IOException, ParseException {

        FileService fileService = new FileService();
        /* запрос с тестового файла  и получение листа от  рабочего сервера
        String request = fileService.getArrayFromFile("requestForManyFilter.json");
        AxonConnect axonConnect = new AxonConnect();
        HttpURLConnection con = axonConnect.getConnectToAxon();
        con = axonConnect.makeRequest(con,request,"POST");
        list =  axonConnect.readResponse(con);
        */
        /*обработка запроса с клиентской части для формирования запроса к аксон*/

        String data = fileService.getArrayFromFile("request/requestFromClient.json");
        JSONObject jsonObject = new JSONObject(data);
        makeRequest(jsonObject);


        /*тестовый ответ и его обработка*/
//        String data = fileService.getArrayFromFile("responseFromManyFilters#4.json");
//        JSONArray jsonArray = new JSONArray(data);
//        makeStreamMap(jsonArray);
//        unionGlossary(numberGlossary, jsonArray);
//        makeResultArray(resultDTOManyFilterList);
        System.out.println(jsonObject);
    }

    private static void makeResultArray(List<ResultEntityDTOForManyFilter> resultDTOManyFilterList) {
        for (ResultEntityDTOForManyFilter rs : resultDTOManyFilterList) {
            resultArray.put(rs.toJSON());
        }

    }

    private static void makeStreamMap(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String facetID = (String) jsonObject.get("facetId");

            if (facetID.equals("PROJECT")) {
                JSONArray itemArray = (JSONArray) jsonObject.get("items");
                streamOwnerProjectEntityList = new ArrayList<>();
                JSONArray projectFields = (JSONArray) jsonObject.get("fields");
                SortedMap<String, Integer> fieldsMap = makeMapFields(projectFields);
                for (int j = 0; j < itemArray.length(); j++) {
                    JSONObject itemObject = (JSONObject) itemArray.get(j);
                    JSONArray values = (JSONArray) itemObject.get("values");
                    String nameProject = (String) values.get(fieldsMap.get("name"));
                    String idProject = (String) values.get(fieldsMap.get("id"));
                    streamOwnerProjectEntityList.add(new StreamOwnerProjectEntity(idProject, null, nameProject));
                }
            }
            if (facetID.equals("SYSTEM")) {
                JSONArray itemArray = (JSONArray) jsonObject.get("items");
                JSONArray systemFields = (JSONArray) jsonObject.get("fields");
                SortedMap<String, Integer> fieldsMap = makeMapFields(systemFields);
                systemEntityList = new ArrayList<>();
                for (int j = 0; j < itemArray.length(); j++) {
                    JSONObject itemObject = (JSONObject) itemArray.get(j);
                    JSONArray values = (JSONArray) itemObject.get("values");
                    String nameSystem = (String) values.get(fieldsMap.get("name"));
                    String idSystem = (String) values.get(fieldsMap.get("id"));
                    String idRISSystem = (String) values.get(fieldsMap.get("IDRIS"));
                    systemEntityList.add(new SystemEntity(idSystem, null, nameSystem, idRISSystem));
                }
                System.out.println(systemEntityList);
            }

            if (facetID.equals("GLOSSARY")) {
                numberGlossary = i;
            }
        }
    }

    private static void unionGlossary(int numberGlossary, JSONArray jsonArray) {
        resultDTOManyFilterList = new ArrayList<>();
        JSONObject jsonObject = (JSONObject) jsonArray.get(numberGlossary);
        JSONArray itemArray = (JSONArray) jsonObject.get("items");
        JSONArray glossaryFields = (JSONArray) jsonObject.get("fields");
        SortedMap<String, Integer> fieldsMap = makeMapFields(glossaryFields);
        for (int j = 0; j < itemArray.length(); j++) {
            streamOwnerPrivateProjectEntityList = new ArrayList<>();
            systemPrivateEntityList = new ArrayList<>();
            JSONObject itemObject = (JSONObject) itemArray.get(j);
            JSONArray values = (JSONArray) itemObject.get("values");
            String title = (String) values.get(fieldsMap.get("name"));
            String id = (String) values.get(fieldsMap.get("id"));
            String description = (String) values.get(fieldsMap.get("description"));
            String owner = fieldsMap.containsKey("Владелецданных") ? (String) values.get(fieldsMap.get("Владелецданных")) : null;
            String type = fieldsMap.containsKey("type") ? (String) values.get(fieldsMap.get("type")) : null;
            List<TextField> fields = new ArrayList<>();
            String url = "url";
            String source = "source";
            JSONArray relationships = itemObject.has("relationships") ? (JSONArray) itemObject.get("relationships") : new JSONArray();
            for (int k = 0; k < relationships.length(); k++) {
                JSONObject relObject = (JSONObject) relationships.get(k);
                String toFacet = (String) relObject.get("toFacet");

                switch (toFacet) {

                    case "SYSTEM": {
                        JSONArray relSlaveArray = (JSONArray) relObject.get("relationships");
                        for (Object systemRelObject : relSlaveArray) {
                            JSONObject idSystemJSon = (JSONObject) systemRelObject;
                            String idRelSystem = idSystemJSon.getString("id");
                            String relTypeId = String.valueOf(idSystemJSon.getInt("relTypeId"));
                            for (SystemEntity systemEntity : systemEntityList) {
                                if (systemEntity.getId().equals(idRelSystem)) {
                                    systemPrivateEntityList.add(new SystemEntity(systemEntity.getId(), relTypeId, systemEntity.getName(), systemEntity.getIdRIS()));
                                }
                            }
                        }
                    }
                    break;

                    case "PROJECT": {
                        JSONArray relSlaveArray = (JSONArray) relObject.get("relationships");
                        for (Object systemRelObject : relSlaveArray) {
                            JSONObject idSystemJSon = (JSONObject) systemRelObject;
                            String idRelStream = idSystemJSon.getString("id");
                            String relTypeId = String.valueOf(idSystemJSon.getInt("relTypeId"));
                            for (StreamOwnerProjectEntity streamEntity : streamOwnerProjectEntityList) {
                                if (streamEntity.getId().equals(idRelStream)) {
                                    if (relTypeId.equals("1")) {
                                        streamEntity.setRelTypeId(relTypeId);
                                        streamOwnerPrivateProjectEntityList.add(streamEntity);
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            }
            ResultEntityDTOForManyFilter resultEntityDTOForManyFilter = new ResultEntityDTOForManyFilter(id, title, fields, url, source, type, description,
                    owner, systemPrivateEntityList, streamOwnerPrivateProjectEntityList);
            resultDTOManyFilterList.add(resultEntityDTOForManyFilter);
        }
    }

    private static SortedMap<String, Integer> makeMapFields(JSONArray fields) {
        SortedMap<String, Integer> mapFields = new TreeMap<>();
        for (int i = 0; i < fields.length(); i++) {
            mapFields.put(fields.getString(i), i);
        }
        return mapFields;
    }

    private static void makeRequest(JSONObject jsonObject) {
        JSONArray jsonArray = (JSONArray) jsonObject.get("filter");
        JSONObject textObj = (JSONObject) jsonObject.get("paramsText");
        String text = textObj.getString("text");
        JSONArray filterArray = jsonObject.getJSONArray("filter");
        List<Object> listFilter = filterArray.toList();
        Map<Object, Object> m = new HashMap<>();
        Map<Object, Object> mForMakeJSON = new HashMap<>();
        for (Object o : listFilter) {
            m = (Map<Object, Object>) o;
            mForMakeJSON.putAll(m);
        }
        makeJSON(mForMakeJSON);
    }

    private static JSONObject makeJSON(Map<Object, Object> mj) {

        /* Инициализация фильтров начало*/
        List<Object> systemList = mj.containsKey("typeSystemObjects") ? (List<Object>) mj.get("typeSystemObjects") : new ArrayList<>();
        List<Object> ownerStreamList = mj.containsKey("typeOwnerStreamObjects") ? (List<Object>) mj.get("typeOwnerStreamObjects") : new ArrayList<>();
        List<Object> ownerBankList = mj.containsKey("typeOwnerBankObject") ? (List<Object>) mj.get("typeOwnerBankObject") : new ArrayList<>();
        List<Object> typeOfObjectsList = mj.containsKey("typeOfObjects") ? (List<Object>) mj.get("typeOfObjects") : new ArrayList<>();
        /* Инициализация фильтров конец */

        /*шаблоны полей  начало*/
        JSONObject operatorStartJO = new JSONObject();
        operatorStartJO.put("operator", "START");
        JSONObject operatorAndJO = new JSONObject();
        operatorAndJO.put("operator", "AND");
        JSONObject activeTrueJO = new JSONObject();
        activeTrueJO.put("active", "true");
        JSONObject facetIdJOSystem = new JSONObject();
        facetIdJOSystem.put("facetId", "SYSTEM");
        JSONObject facetIdJOProject = new JSONObject();
        facetIdJOProject.put("facetId", "PROJECT");
        JSONObject facetIdJOGlossary = new JSONObject();
        facetIdJOGlossary.put("facetId", "GLOSSARY");
        JSONObject facetIdDataQuality = new JSONObject();
        facetIdDataQuality.put("facetId", "DATAQUALITY");
        /*шаблоны полей конец*/

        /*Создаем основной объект*/
        JSONObject resultJsonObject = new JSONObject();
        JSONArray searchGroupsJSONArray = new JSONArray();

        resultJsonObject.put("mainFacet", "Glossary");
        resultJsonObject.put("searchGroups", searchGroupsJSONArray);

        searchGroupsJSONArray.put(operatorStartJO);
        searchGroupsJSONArray.put(activeTrueJO);

        /*Создаем массив searches*/
        JSONObject searchesObject = new JSONObject();
        JSONArray searchesJSONArray = new JSONArray();
        searchesObject.put("searches",searchesJSONArray);

        /*заполняем массив searches */


        return resultJsonObject;


    }

}