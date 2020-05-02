package com.enreqad.requirer.kafka.consumer.json;

import java.util.HashMap;

public class SerializableObject {

    private String message;
    private HashMap<String, String> dataMap;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, String> getDataMap() {
        return dataMap;
    }

    public void setDataMap(HashMap<String, String> dataMap) {
        this.dataMap = dataMap;
    }
}
