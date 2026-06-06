package validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonSchemaValidatorTest {

    private JsonSchemaValidator validator;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        validator = new JsonSchemaValidator();
        mapper = new ObjectMapper();
    }

    @Test
    void shouldReturnSuccessWhenJsonMatchesSchema() throws Exception {
        // given
        String schemaJson = """
            {
              "type": "object",
              "properties": {
                "username": { "type": "string" },
                "score": { "type": "number" }
              }
            }
            """;
        String instanceJson = """
            {
              "username": "player1",
              "score": 1500
            }
            """;
        JsonNode schema = mapper.readTree(schemaJson);
        JsonNode instance = mapper.readTree(instanceJson);

        // when
        ValidationResult result = validator.validate(schema, instance);

        // then
        assertTrue(result.isValid());
        assertTrue(result.errors().isEmpty());
    }

    @Test
    void shouldReturnErrorWhenFieldHasWrongType() throws Exception {
        // given
        String schemaJson = """
            {
              "type": "object",
              "properties": {
                "age": { "type": "number" }
              }
            }
            """;
        String instanceJson = """
            {
              "age": "twenty"
            }
            """;
        JsonNode schema = mapper.readTree(schemaJson);
        JsonNode instance = mapper.readTree(instanceJson);

        // when
        ValidationResult result = validator.validate(schema, instance);

        // then
        assertFalse(result.isValid());
        assertEquals(1, result.errors().size());
        assertEquals("Pole 'age' oczekiwało typu 'number', otrzymano 'string'.", result.errors().get(0));
    }

    @Test
    void shouldReturnErrorWhenRequiredFieldIsMissing() throws Exception {
        // given
        String schemaJson = """
            {
              "type": "object",
              "properties": {
                "nestedObject": {
                  "type": "object",
                  "properties": {
                    "id": { "type": "string" }
                  }
                }
              }
            }
            """;
        String instanceJson = """
            {
              "nestedObject": {}
            }
            """;
        JsonNode schema = mapper.readTree(schemaJson);
        JsonNode instance = mapper.readTree(instanceJson);

        // when
        ValidationResult result = validator.validate(schema, instance);

        // then
        assertFalse(result.isValid());
        assertEquals(1, result.errors().size());
        assertEquals("Brakuje wymaganego pola 'nestedObject.id'.", result.errors().get(0));
    }
}