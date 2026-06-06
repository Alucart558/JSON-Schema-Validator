package validator.strategy;

import validator.JsonSchemaValidator;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class NumberTypeValidator implements TypeValidator {

    @Override
    public void validate(JsonNode schema, JsonNode instance, String path, List<String> errors, JsonSchemaValidator context) {
        if (!instance.isNumber()) {
            errors.add(String.format("Pole '%s' oczekiwało typu 'number', otrzymano '%s'.",
                    path, instance.getNodeType().toString().toLowerCase()));
        }
    }
}