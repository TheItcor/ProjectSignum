import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TranslationManager {
    private static final String BASE_PATH = "/messages_";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, Object> loadTranslations(String language) {
        String resourcePath = BASE_PATH + language + ".json";
        try (InputStream inputStream = TranslationManager.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new RuntimeException("ERROR: No file with translations: " + resourcePath);
            }

            // Указываем кодировку UTF-8
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            return mapper.readValue(reader, HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException("ERROR: error with translation loading", e);
        }
    }
}