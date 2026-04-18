package jsonreader;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import jsonreader.errors.JSONParseError;
import jsonreader.types.*;

/**
 * A recursive-descent parser for a subset of JSON.
 * Supports objects, arrays, strings, and integers.
 * <p>
 * Cursor convention: after any parse* method returns, the cursor
 * sits on the first character after the parsed token.
 * <p>
 * Usage:
 *   JSONReader reader = new JSONReader(new File("data.json"));
 *   JSONValueType root = reader.readJSON();
 */
public class JSONReader {
    private String source;

    private int index = -1;

    public JSONReader(File file) {
        try {
            parseFile(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        StringBuilder sourceBuilder = new StringBuilder();

        while (scanner.hasNext()) {
            String token = scanner.nextLine();
            sourceBuilder.append(token);
        }

        source = sourceBuilder
                .toString()
                .replace("\n", "");
    }

    private char currentChar() {
        if (index >= source.length()) return 0;
        return source.charAt(index);
    }

    private char nextChar() {
        index++;
        return currentChar();
    }

    private void clearSpaces() {
        while (currentChar() == ' ') nextChar();
    }

    private void accumulateObject(JSONObjectType object) {
        nextChar();
        clearSpaces();
        while (currentChar() != '}') {
            String key = parseString();
            nextChar();
            clearSpaces();
            if (currentChar() != ':') {
                throw new JSONParseError(
                        "Invalid JSON Syntax: Expected a colon but got " + currentChar() + " at index " + index
                );
            }
            nextChar();
            clearSpaces();
            object.addItem(key, getElement());

            handleComma('}');
        }
        nextChar(); // consume '}'
    }

    private JSONValueType getElement() {
        JSONValueType value = parse();
        clearSpaces();

        if (currentChar() == 0) {
            throw new JSONParseError("The bracket was never closed at index " + index);
        }
        return value;
    }

    private void accumulateArray(JSONArrayType array) {
        nextChar();
        clearSpaces();
        while (currentChar() != ']') {
            array.addItem(getElement());

            handleComma(']');
        }
        nextChar(); // consume ']'
    }

    /**
     * Consumes the comma between values. <p>
     * Passes closingChar ('}' or ']') so it can tell the difference<p>
     * between a missing comma and a legitimate end of collection.
     */
    private void handleComma(char closingChar) {
        if (currentChar() == ',') {
            nextChar();
            clearSpaces();
            if (currentChar() == closingChar) {
                throw new JSONParseError("Trailing comma at index " + index);
            }
            return;
        }
        if (currentChar() == closingChar) return;

        throw new JSONParseError(
                "Expected ',' or '" + closingChar + "' but got '" + currentChar() + "' at index " + index
        );
    }

    private String parseString() {
        StringBuilder stringBuilder = new StringBuilder();

        while (nextChar() != '"') {
            stringBuilder.append(currentChar());
        }
        return stringBuilder.toString();
    }

    public JSONValueType parse() {
        clearSpaces();
        if (currentChar() == '{') {
            JSONObjectType object = new JSONObjectType();
            accumulateObject(object);
            return object;
        }
        if (currentChar() == '[') {
            JSONArrayType array = new JSONArrayType();
            accumulateArray(array);
            return array;
        }
        if (currentChar() == '"') {
            return getStringType();
        }
        if (Character.isDigit(currentChar()) || currentChar() == '-') {
            return parseNumberType();
        }
        return null;
    }

    private JSONStringType getStringType() {
        String parsedString = parseString();
        nextChar();
        return new JSONStringType(
                parsedString
        );
    }

    private JSONNumberType parseNumberType() {
        StringBuilder unformattedNumber = new StringBuilder();

        do {
            unformattedNumber.append(currentChar());
            nextChar();
        } while (Character.isDigit(currentChar()));

        int num = Integer.parseInt(unformattedNumber.toString());

        return new JSONNumberType(num);
    }

    public JSONValueType readJSON() {
        nextChar();
        return parse();
    }
}
