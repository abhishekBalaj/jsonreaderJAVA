package jsonreader.types;

public class JSONStringType extends JSONValueType {
    private final String string;

    public JSONStringType(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public Object getAsJavaObject() {
        return string;
    }
}
