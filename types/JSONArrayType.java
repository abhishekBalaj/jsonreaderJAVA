package jsonreader.types;

import java.util.ArrayList;
public class JSONArrayType extends JSONValueType {
    private final ArrayList<JSONValueType> elements;

    public JSONArrayType() {
        this.elements = new ArrayList<>();
    }

    public void addItem(JSONValueType value) {
        elements.add(value);
    }

    public ArrayList<JSONValueType> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    @Override
    public Object getAsJavaObject() {
        ArrayList<Object> objectList = new ArrayList<>();

        elements.forEach((value) -> objectList.add(value.getAsJavaObject()));

        return objectList;
    }
}
