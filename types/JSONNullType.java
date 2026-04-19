package jsonreader.types;

public class JSONNullType extends JSONValueType {
    @Override
    public Object getAsJavaObject() { return null; }

    @Override
    public String toString() {
        return "null";
    }
}