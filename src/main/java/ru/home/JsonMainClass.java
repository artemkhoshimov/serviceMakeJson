package ru.home;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.home.entity.StreamOwnerProjectEntity;
import ru.home.entity.SystemEntity;
import ru.home.entity.dto.manyFilterResultEntity.ResultEntityDTOForManyFilter;
import ru.home.entity.dto.ownerStreamEntity.ResultDTOOwnerStreamEntity;
import ru.home.fileService.FileService;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class JsonMainClass {
    static List<StreamOwnerProjectEntity> streamOwnerProjectEntityList;
    static List<SystemEntity> systemEntityList;
    static List<ResultDTOOwnerStreamEntity> resultDTOOwnerStreamEntityList;
    static List<Object> list = new ArrayList<>();
    static int numberGlossary = 0;
    static List<ResultEntityDTOForManyFilter> resultDTOManyFilterList;


    public static void main(String[] args) throws IOException, ParseException {

        FileService fileService = new FileService();
        /* запрос с тестового файла  и получение листа от  рабочего сервера
        String request = fileService.getArrayFromFile("requestForManyFilter.json");
        AxonConnect axonConnect = new AxonConnect();
        HttpURLConnection con = axonConnect.getConnectToAxon();
        con = axonConnect.makeRequest(con,request,"POST");
        list =  axonConnect.readResponse(con);
        */

        /*тестовый ответ и его обработка*/
        String data = fileService.getArrayFromFile("responseFromManyFilters.json");
        JSONArray jsonArray = new JSONArray(data);
        makeStreamMap(jsonArray);
        unionGlossary(numberGlossary, jsonArray);
        System.out.println(jsonArray);
    }

    private static void makeStreamMap(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String facetID = (String) jsonObject.get("facetId");
            SortedMap<String, JSONObject> sortedMap = new TreeMap<>();
            sortedMap.put(facetID, jsonObject);

            if (facetID.equals("PROJECT")) {
                JSONArray itemArray = (JSONArray) jsonObject.get("items");
                streamOwnerProjectEntityList = new ArrayList<>();
                for (int j = 0; j < itemArray.length(); j++) {
                    JSONObject itemObject = (JSONObject) itemArray.get(j);
                    JSONArray values = (JSONArray) itemObject.get("values");
                    String nameProject = (String) values.get(2);
                    String idProject = (String) values.get(0);
                    streamOwnerProjectEntityList.add(new StreamOwnerProjectEntity(idProject, null, nameProject));
                }
            }
            if (facetID.equals("SYSTEM")) {
                JSONArray itemArray = (JSONArray) jsonObject.get("items");
                systemEntityList = new ArrayList<>();
                for (int j = 0; j < itemArray.length(); j++) {
                    JSONObject itemObject = (JSONObject) itemArray.get(j);
                    JSONArray values = (JSONArray) itemObject.get("values");
                    String nameSystem = (String) values.get(1);
                    String idSystem = (String) values.get(0);
                    String idRISSystem = (String) values.get(25);
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

        JSONObject jsonObject = (JSONObject) jsonArray.get(numberGlossary);
        JSONArray itemArray = (JSONArray) jsonObject.get("items");
        for (int j = 0; j < itemArray.length(); j++) {
            JSONObject itemObject = (JSONObject) itemArray.get(j);
            JSONArray values = (JSONArray) itemObject.get("values");
            JSONArray relationships = (JSONArray) itemObject.get("relationships");
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
                                    systemEntity.setRelTypeId(relTypeId);
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
                                    streamEntity.setRelTypeId(relTypeId);
                                }
                            }
                        }
                        streamOwnerProjectEntityList = streamOwnerProjectEntityList.stream().filter(streamE -> {
                                    return streamE.getRelTypeId().equals("1");
                                }
                        ).collect(Collectors.toList());
                        break;
                    }
                }
            }


//            resultDTOManyFilterList.add(",)
        }
    }


}