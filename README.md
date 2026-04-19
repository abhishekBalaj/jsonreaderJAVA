# json-reader

A lightweight recursive-descent JSON parser written in Java from scratch. Supports the core JSON value types with strict syntax validation.

## Supported types

- Objects `{ "key": value }`
- Arrays `[ value, ... ]`
- Strings `"hello"`
- Integers `42`, `-7`
- Null `null`
- Boolean `true` or `false` literals

## Usage

```java
JSONReader reader = new JSONReader(new File("data.json"));
JSONValueType root = reader.readJSON();
```

Getting the value as a plain Java object:

```java
Object value = root.getAsJavaObject();

if (value instanceof Map<?, ?> map) {
    // it's a JSON object
}

if (value instanceof ArrayList<?> list) {
    list.forEach(System.out::println);
}
```

## Error handling

All parse errors throw `JSONParseException` (a `RuntimeException`) with a message including the character index where the error was detected.

```
JSONParseException: Expected ',' or '}' but got 'x' at index 42
JSONParseException: Trailing comma at index 17
JSONParseException: The bracket was never closed at index 88
```

## Limitations

- Integers only — floats and doubles are not supported
- No unicode escape sequences in strings (`\uXXXX`)
- File must be UTF-8 encoded
