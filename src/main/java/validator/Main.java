package validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // 1. Wczytaj schemat i instancję JSON z plików
        File schemaFile = new File("schema.json");
        File instanceFile = new File("instance.json");

        JsonNode schema = mapper.readTree(schemaFile);
        JsonNode instance = mapper.readTree(instanceFile);

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
