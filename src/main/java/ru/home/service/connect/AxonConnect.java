package ru.home.service.connect;

import org.json.JSONArray;
import ru.home.Constants;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AxonConnect {

    List<Object> list = new ArrayList<>();

    private HttpURLConnection con;
    private String token;

    public AxonConnect() {
        init();
    }

    public HttpURLConnection getConnectToAxon() {
        String urlE = "http://d5dtpl-apc011ln.corp.dev.vtb:9999/unison/v1/facet/_search";
        try {
            URL url = new URL(urlE);
            token = Constants.tkn;
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException io) {
            io.printStackTrace();
        }
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + token);
        return con;
    }

    void init() {
        con = getConnectToAxon();
    }

    public HttpURLConnection getCon() {
        return con;
    }

    public void setCon(HttpURLConnection con) {
        this.con = con;
    }

    public HttpURLConnection makeRequest(HttpURLConnection con, String request, String requestMethod) {
        try {
            con.setRequestMethod(requestMethod);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = request.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return con;
    }

    public List<Object> readResponse(HttpURLConnection con) {
        JSONArray jsonArray;
        int responseCode = 0;
        try {
            responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        jsonArray = new JSONArray(responseLine);
                        list = jsonArray.toList();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


}
