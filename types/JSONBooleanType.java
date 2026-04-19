package jsonreader.types;

public class JSONBooleanType extends JSONValueType {
    private final boolean value;
    public JSONBooleanType(boolean value) { this.value = value; }

    @Override
    public Object getAsJavaObject() { return value; }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}