package ru.home.entity;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

public class GlossaryEntity implements Serializable {

String id;
String title;
List<TextFieldForGlossary> fields;
TreeMap<String,String> url;
String source;
String classType;

    public GlossaryEntity(String id, String title, List<TextFieldForGlossary> fields, TreeMap<String, String> url, String source, String classType) {
        this.id = id;
        this.title = title;
        this.fields = fields;
        this.url = url;
        this.source = source;
        this.classType = classType;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<TextFieldForGlossary> getFields() {
        return fields;
    }

    public TreeMap<String, String> getUrl() {
        return url;
    }

    public String getSource() {
        return source;
    }

    public String getClassType() {
        return classType;
    }
}

