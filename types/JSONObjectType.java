package jsonreader.types;

import java.util.HashMap;

public class JSONObjectType extends JSONValueType {
    public HashMap<String, JSONValueType> map;

    public JSONObjectType() {
        map = new HashMap<>();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        map.forEach((key, value) -> {
            stringBuilder.append(key);
            stringBuilder.append(": ");
            stringBuilder.append(value);
            stringBuilder.append(", ");
        });
        if (!map.isEmpty()) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }

        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public void addItem(String key, JSONValueType value) {
        map.put(key, value);
    }

    @Override
    public Object getAsJavaObject() {
        HashMap<String, Object> objectHashMap = new HashMap<>();

        map.forEach((key, value) -> {
            objectHashMap.put(key, value.getAsJavaObject());
        });

        return objectHashMap;
    }
}
