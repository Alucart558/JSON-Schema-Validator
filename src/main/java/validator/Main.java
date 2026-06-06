package validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // 1. Definicja schematu (uproszczona struktura JSON Schema)
        String schemaJson = """
            {
              "type": "object",
              "properties": {
                "user": {
                  "type": "object",
                  "properties": {
                    "name": { "type": "string" },
                    "age": { "type": "number" },
                    "role": { "type": "string" }
                  }
                }
              }
            }
            """;

        // 2. Instancja JSON z błędami (zły typ dla age, brak pola role)
        String instanceJson = """
            {
              "user": {
                "name": "Dawid",
                "age": "dwadzieścia"
              }
            }
            """;

        JsonNode schema = mapper.readTree(schemaJson);
        JsonNode instance = mapper.readTree(instanceJson);

        JsonSchemaValidator validator = new JsonSchemaValidator();
        ValidationResult result = validator.validate(schema, instance);

        if (result.isValid()) {
            System.out.println("JSON jest poprawny!");
        } else {
            System.out.println("Znaleziono błędy walidacji:");
            result.errors().forEach(error -> System.out.println(" - " + error));
        }
    }
}