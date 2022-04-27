package ru.home.entity.dto.ownerStreamEntity;

import org.json.JSONObject;

public class TextField {
    public  String field;
    public  String value;


    public TextField(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public TextField(String value) {
        this(null, value);
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        if (this.field != null)
            json.put("field", this.field);

        json.put("value", this.value);

        return json;
    }

    public String toString() {
        String string = "";

        if (this.field != null)
            string += "field=\\" + this.field + "\"";

        string += "value=\\" + this.value + "\"";

        return string;
    }
}
