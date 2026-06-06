# Walidator Schematu JSON

Prosty walidator schematu JSON napisany w Javie, korzystający z biblioteki Jackson do przetwarzania formatu JSON.

## Jak to działa?

Ten projekt dostarcza klasę `JsonSchemaValidator`, która weryfikuje, czy dany obiekt JSON (`instance.json`) jest zgodny z określonym przez Ciebie schematem (`schema.json`). Obsługuje podstawowe typy (string, number, boolean, itp.) oraz waliduje struktury zagnieżdżone.

## Jak uruchomić?

Projekt korzysta z narzędzia Maven do zarządzania zależnościami.

### W środowisku IntelliJ IDEA:
1. Po otwarciu projektu, kliknij prawym przyciskiem myszy na plik `pom.xml` i wybierz opcję **"Add as Maven Project"**.
2. Poczekaj, aż IDE pobierze wymagane biblioteki (m.in. Jackson).
3. Plik `schema.json` zawiera definicję schematu, według którego chcesz weryfikować dane.
4. Plik `instance.json` zawiera dane JSON, które chcesz sprawdzić pod kątem zgodności ze schematem. Możesz swobodnie modyfikować zawartość obydwu tych plików.
5. Otwórz klasę `src/main/java/validator/Main.java` i kliknij zielony przycisk "Run" (play) obok metody `main`.

Wynik walidacji (sukces lub szczegółowa lista błędów) zostanie wypisany w konsoli.

## Jak uruchomić testy?

Testy jednostkowe sprawdzają poprawność działania walidatora na różnych przypadkach i wykorzystują bibliotekę JUnit 5.

1. Przejdź do pliku `src/test/java/validator/JsonSchemaValidatorTest.java`.
2. Kliknij prawym przyciskiem myszy na nazwę klasy `JsonSchemaValidatorTest` w kodzie lub w drzewie projektu.
3. Wybierz opcję "Run 'JsonSchemaValidatorTest'". Wyniki pojawią się w dedykowanym oknie narzędziowym testów w Twoim IDE.