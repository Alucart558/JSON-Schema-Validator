package validator.strategy;

import validator.JsonSchemaValidator;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class ObjectTypeValidator implements TypeValidator {

    @Override
    public void validate(JsonNode schema, JsonNode instance, String path, List<String> errors, JsonSchemaValidator context) {
        if (!instance.isObject()) {
            errors.add(String.format("Pole '%s' oczekiwało typu 'object', otrzymano '%s'.",
                    path.isEmpty() ? "root" : path, instance.getNodeType().toString().toLowerCase()));
            return;
        }

        JsonNode properties = schema.get("properties");
        if (properties != null && properties.isObject()) {
            properties.fieldNames().forEachRemaining(fieldName -> {
                JsonNode propertySchema = properties.get(fieldName);
                JsonNode propertyInstance = instance.get(fieldName);

                String newPath = path.isEmpty() ? fieldName : path + "." + fieldName;

                if (propertyInstance == null || propertyInstance.isNull()) {
                    errors.add(String.format("Brakuje wymaganego pola '%s'.", newPath));
                } else {
                    // Wywołanie rekurencyjne przez główny walidator
                    context.validateNode(propertySchema, propertyInstance, newPath, errors);
                }
            });
        }
    }
}