package validator.strategy;

import validator.JsonSchemaValidator;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

/**
 * Interfejs bazowy dla wzorca Strategii określający sposób walidacji konkretnych typów JSON.
 */
public interface TypeValidator {

    /**
     * Waliduje pojedynczy węzeł JSON.
     *
     * @param schema   Węzeł schematu opisujący oczekiwany typ i strukturę.
     * @param instance Faktyczne dane JSON do zwalidowania.
     * @param path     Ścieżka do aktualnego węzła (np. "user.address.city").
     * @param errors   Lista, do której dopisywane są komunikaty o błędach.
     * @param context  Główny walidator, używany do wywołań rekurencyjnych (zagnieżdżone obiekty/tablice).
     */
    void validate(JsonNode schema, JsonNode instance, String path, List<String> errors, JsonSchemaValidator context);
}