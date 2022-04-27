package ru.home.entity.dto.ownerStreamEntity;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.TreeMap;

public class ResultDTOOwnerStreamEntity {

    String id;
    String title;
    List<TextField> fields;
    String url;
    String source;
    String classType;
    String ownerStreamName;



    public ResultDTOOwnerStreamEntity(String id, String title, List<TextField> fields, String url, String source, String classType, String ownerStreamName) {
        this.id = id;
        this.title = title;
        this.fields = fields;
        this.url = url;
        this.source = source;
        this.classType = classType;
        this.ownerStreamName = ownerStreamName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TextField> getFields() {
        return fields;
    }

    public void setFields(List<TextField> fields) {
        this.fields = fields;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getOwnerStreamName() {
        return ownerStreamName;
    }

    public void setOwnerStreamName(String ownerStreamName) {
        this.ownerStreamName = ownerStreamName;
    }
}
