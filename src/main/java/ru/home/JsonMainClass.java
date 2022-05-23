package ru.home;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.home.entity.StreamOwnerProjectEntity;
import ru.home.entity.SystemEntity;
import ru.home.entity.dto.manyFilterResultEntity.ResultEntityDTOForManyFilter;
import ru.home.fileService.FileService;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        makeJSON(mForMakeJSON, text);
    }

    private static JSONObject makeJSON(Map<Object, Object> mj, String text) {

        /* Инициализация фильтров начало*/
        List<Object> systemList = mj.containsKey("typeSystemObjects") ? (List<Object>) mj.get("typeSystemObjects") : new ArrayList<>();
        List<String> ownerStreamList = mj.containsKey("typeOwnerStreamObjects") ? (List<String>) mj.get("typeOwnerStreamObjects") : new ArrayList<>();
        List<Object> ownerBankList = mj.containsKey("typeOwnerBankObject") ? (List<Object>) mj.get("typeOwnerBankObject") : new ArrayList<>();
        List<Object> typeOfObjectsList = mj.containsKey("typeOfObjects") ? (List<Object>) mj.get("typeOfObjects") : new ArrayList<>();
        String ownerStreamFormatArray = ownerStreamList.toString().replaceAll("^\\[|\\]$", "");
        String systemFormatArray = systemList.toString().replaceAll("^\\[|\\]$", "");
        String ownerBankFormatArray = ownerBankList.toString().replaceAll("^\\[|\\]$", "");
        String typeOfObjectsFormatArray = typeOfObjectsList.toString().replaceAll("^\\[|\\]$", "");
        /* Инициализация фильтров конец */

        /*шаблоны полей  начало*/


        String operator = "operator";
        String start = "START";
        String active = "active";
        String trueField = "true";
        String facetId = "facetId";
        String glossary = "GLOSSARY";
        String type = "type";
        String options = "OPTIONS";
        String and = "AND";
        String field = "field";
        String value = "value";
        /*шаблоны полей конец*/

        /*Создаем основной объект*/
        JSONObject resultJsonObject = new JSONObject();
        JSONArray searchGroupsJSONArray = new JSONArray();
        JSONArray searchScopesJSONArray = new JSONArray();

        resultJsonObject.put("mainFacet", glossary);
        resultJsonObject.put("searchGroups", searchGroupsJSONArray);
        resultJsonObject.put("searchScopes", searchScopesJSONArray);

        /*создаем первую   и единственную группу поиска*/
        JSONObject firstGroupObject = new JSONObject();
        JSONArray searchesJSONArray = new JSONArray();
        firstGroupObject.put(operator, start);
        firstGroupObject.put(active, trueField);
        firstGroupObject.put("searches", searchesJSONArray);


        /*добавляем группу поиска */
        searchGroupsJSONArray.put(firstGroupObject);

        /*создаем подгруппу Глоссария*/
        JSONObject glossaryUnderGroup = new JSONObject();
        JSONArray filterGroupsGlossaryArray = new JSONArray();
        glossaryUnderGroup.put(operator, start);
        glossaryUnderGroup.put(active, trueField);
        glossaryUnderGroup.put(facetId, glossary);
        glossaryUnderGroup.put("filterGroups", filterGroupsGlossaryArray);

        if (ownerStreamList.size() > 0)
            /*создаем подгруппу Project*/ {
            JSONObject projectUnderGroup = new JSONObject();
            JSONArray filterGroupsProjectArray = new JSONArray();
            projectUnderGroup.put(operator, and);
            projectUnderGroup.put(active, trueField);
            projectUnderGroup.put(facetId, "PROJECT");
            projectUnderGroup.put("filterGroups", filterGroupsProjectArray);
            searchesJSONArray.put(projectUnderGroup);

            //*создаем фильтр группу для стрима
            JSONObject filterGroupStreamObject = new JSONObject();
            JSONArray filterArray = new JSONArray();
            filterGroupStreamObject.put(operator, start);
            filterGroupStreamObject.put("filters", filterArray);

            /*добавляем фильтр группу для Стрима*/
            filterGroupsProjectArray.put(filterGroupStreamObject);


            /*создаем поле #1 для стрима*/
            JSONObject fieldSearch = new JSONObject();
            JSONObject fieldNameObject = new JSONObject();

            fieldSearch.put(operator, start);
            fieldSearch.put(type, options);

            fieldNameObject.put(field, "ID");
            fieldNameObject.put(value, ownerStreamFormatArray);
            fieldSearch.put("properties", fieldNameObject);

            /*добавляем поле проекта к стриму*/
            filterArray.put(fieldSearch);
            //_____________

        }

        if (systemList.size() > 0)
            /*создаем подгруппу System*/ {
            JSONObject systemUnderGroup = new JSONObject();
            JSONArray filterGroupsSystemArray = new JSONArray();
            systemUnderGroup.put(operator, and);
            systemUnderGroup.put(active, trueField);
            systemUnderGroup.put(facetId, "SYSTEM");
            systemUnderGroup.put("filterGroups", filterGroupsSystemArray);
            searchesJSONArray.put(systemUnderGroup);

            //*создаем фильтр группу для системы
            JSONObject filterGroupSystemObject = new JSONObject();
            JSONArray filterArray = new JSONArray();
            filterGroupSystemObject.put(operator, start);
            filterGroupSystemObject.put("filters", filterArray);

            /*добавляем фильтр группу для системы*/
            filterGroupsSystemArray.put(filterGroupSystemObject);


            /*создаем поле #1 для системы*/
            JSONObject fieldSearch = new JSONObject();
            JSONObject fieldNameObject = new JSONObject();

            fieldSearch.put(operator, start);
            fieldSearch.put(type, options);

            fieldNameObject.put(field, "ID");
            fieldNameObject.put(value, systemFormatArray);
            fieldSearch.put("properties", fieldNameObject);

            /*добавляем поле проекта к стриму*/
            filterArray.put(fieldSearch);
            //_____________

        }


        /*добавляем первую подгруппу Глоссария*/
        searchesJSONArray.put(glossaryUnderGroup);

        /*создаем первую   и единственную фильтр группу для подгруппы Глоссария*/
        JSONObject filterGroupObject = new JSONObject();
        JSONArray filterArray = new JSONArray();
        filterGroupObject.put(operator, start);
        filterGroupObject.put("filters", filterArray);

        /*добавляем фильтр группу для Глоссария*/
        filterGroupsGlossaryArray.put(filterGroupObject);


        /*создаем поле #1 для поиска*/
        JSONObject fieldSearch = new JSONObject();
        JSONObject fieldNameObject = new JSONObject();

        fieldSearch.put(operator, start);
        fieldSearch.put(type, "value");

        fieldNameObject.put(field, "name");
        fieldNameObject.put(value, text);
        fieldSearch.put("properties", fieldNameObject);

        /*добавляем поле поиска к фильтру*/
        filterArray.put(fieldSearch);
        //_____________

        /*создаем поле №2 для статуса аксона*/
        JSONObject fieldAxonStatus = new JSONObject();
        JSONObject fieldStatusObject = new JSONObject();

        fieldAxonStatus.put(operator, and);
        fieldAxonStatus.put(type, options);

        fieldStatusObject.put(field, "axonStatus");
        fieldStatusObject.put(value, "1,3");

        fieldAxonStatus.put("properties", fieldStatusObject);

        /*добавляем поле статуса к фильтру*/
        filterArray.put(fieldAxonStatus);
        //_____________


        /*создаем поле №3 для доступа */
        JSONObject fieldAccessControlObject = new JSONObject();
        JSONObject fieldAccessControl = new JSONObject();

        fieldAccessControlObject.put(operator, and);
        fieldAccessControlObject.put(type, options);

        fieldAccessControl.put(field, "accessControl");
        fieldAccessControl.put(value, "1");

        fieldAccessControlObject.put("properties", fieldAccessControl);

        /*добавляем поле доступа к фильтру*/
        filterArray.put(fieldAccessControlObject);
        //_____________

        /*создаем поле №4 для системы */
        if (typeOfObjectsList.size() > 0) {
            JSONObject fieldSystemObject = new JSONObject();
            JSONObject fieldSystem = new JSONObject();

            fieldSystemObject.put(operator, and);
            fieldSystemObject.put(type, options);

            fieldSystem.put(field, "type");
            fieldSystem.put(value, typeOfObjectsFormatArray);

            fieldSystemObject.put("properties", fieldSystem);

            /*добавляем поле системы к фильтру*/
            filterArray.put(fieldSystemObject);
        }
        //_____________

        /*создаем поле №5 для банка */
        if (ownerBankList.size() > 0) {
            JSONObject fieldOwnerBankObject = new JSONObject();
            JSONObject fieldOwnerBank = new JSONObject();

            fieldOwnerBankObject.put(operator, and);
            fieldOwnerBankObject.put(type, options);

            fieldOwnerBank.put(field, "Владелецданных");
            fieldOwnerBank.put(value, ownerBankFormatArray);

            fieldOwnerBankObject.put("properties", fieldOwnerBank);

            /*добавляем поле системы к фильтру*/
            filterArray.put(fieldOwnerBankObject);
        }
        //_____________

        return resultJsonObject;


    }

}