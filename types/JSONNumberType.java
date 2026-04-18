package jsonreader.types;

public class JSONNumberType extends JSONValueType {
    private final int value;

    public JSONNumberType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public Object getAsJavaObject() {
        return getValue();
    }
}
