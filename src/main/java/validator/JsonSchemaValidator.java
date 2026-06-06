package validator;

import validator.strategy.NumberTypeValidator;
import validator.strategy.ObjectTypeValidator;
import validator.strategy.StringTypeValidator;
import validator.strategy.TypeValidator;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Główny walidator przechodzący przez strukturę JSON rekurencyjnie,
 * delegujący sprawdzanie typów do odpowiednich strategii.
 */
public class JsonSchemaValidator {

    private final Map<String, TypeValidator> strategies;

    public JsonSchemaValidator() {
        this.strategies = new HashMap<>();
        // Rejestracja dostępnych strategii walidacji (Open/Closed Principle)
        strategies.put("string", new StringTypeValidator());
        strategies.put("number", new NumberTypeValidator());
        strategies.put("object", new ObjectTypeValidator());
        // Tutaj można dodać array, boolean w przyszłości
    }

    /**
     * Główna metoda walidująca JSON na podstawie schematu.
     *
     * @param schema   Schemat JSON
     * @param instance Instancja JSON do weryfikacji
     * @return ValidationResult zawierający status i ewentualne błędy
     */
    public ValidationResult validate(JsonNode schema, JsonNode instance) {
        List<String> errors = new ArrayList<>();
        validateNode(schema, instance, "", errors);

        if (errors.isEmpty()) {
            return ValidationResult.success();
        }
        return ValidationResult.failure(errors);
    }

    /**
     * Metoda używana wewnętrznie (oraz przez strategie do rekurencji)
     * do znalezienia odpowiedniej strategii i delegacji walidacji.
     */
    public void validateNode(JsonNode schema, JsonNode instance, String path, List<String> errors) {
        JsonNode typeNode = schema.get("type");

        if (typeNode == null || !typeNode.isTextual()) {
            errors.add(String.format("Nieprawidłowy schemat: brak określenia 'type' dla węzła '%s'.", path.isEmpty() ? "root" : path));
            return;
        }

        String expectedType = typeNode.asText();
        TypeValidator validator = strategies.get(expectedType);

        if (validator != null) {
            validator.validate(schema, instance, path, errors, this);
        } else {
            errors.add(String.format("Brak zaimplementowanej strategii dla typu: '%s' (ścieżka: '%s').", expectedType, path));
        }
    }
}