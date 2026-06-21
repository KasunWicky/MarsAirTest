package utils;

import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class Utility {

    public static Object[][] readCsv(String resourcePath) {
        try (InputStreamReader streamReader = getInputStreamReader(resourcePath);
             CSVReader csvReader = new CSVReader(streamReader)) {

            List<String[]> rows = csvReader.readAll();
            rows.remove(0); // skip header row
            return rows.stream()
                    .map(row -> (Object[]) row)
                    .toArray(Object[][]::new);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load CSV: " + resourcePath, e);
        }
    }

    private static InputStreamReader getInputStreamReader(String resourcePath) {
        InputStream stream = Utility.class.getClassLoader().getResourceAsStream(resourcePath);
        Objects.requireNonNull(stream, "Test data file not found on classpath: " + resourcePath);
        return new InputStreamReader(stream);
    }
}
