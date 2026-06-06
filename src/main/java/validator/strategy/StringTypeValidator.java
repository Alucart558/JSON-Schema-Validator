package validator.strategy;

import validator.JsonSchemaValidator;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class StringTypeValidator implements TypeValidator {

    @Override
    public void validate(JsonNode schema, JsonNode instance, String path, List<String> errors, JsonSchemaValidator context) {
        if (!instance.isTextual()) {
            errors.add(String.format("Pole '%s' oczekiwało typu 'string', otrzymano '%s'.",
                    path, instance.getNodeType().toString().toLowerCase()));
        }
    }
}