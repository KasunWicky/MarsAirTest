package utils;

import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class Utility {

    /**
     * Reads a CSV from the classpath and returns rows as a 2D array, skipping the header.
     * @param resourcePath classpath path to the CSV file
     * @return test data as Object[][]
     */
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

    /**
     * Checks format and check digit. Returns true only if both pass.
     * @param promoCode the promo code to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPromoCode(String promoCode) {
        if (!promoCode.matches("[A-Z]{2}[0-9]-[A-Z]{3}-[0-9]{3}")) {
            return false;
        }
        int d1 = Character.getNumericValue(promoCode.charAt(2));
        int d2 = Character.getNumericValue(promoCode.charAt(8));
        int d3 = Character.getNumericValue(promoCode.charAt(9));
        int checkDigit = Character.getNumericValue(promoCode.charAt(10));
        return (d1 + d2 + d3) % 10 == checkDigit;
    }

    /**
     * Builds the expected promo message based on whether the code is valid or not.
     * @param promoCode the promo code entered by the user
     * @return expected result message string
     */
    public static String buildExpectedPromoResult(String promoCode) {
        if (isValidPromoCode(promoCode)) {
            int discount = Character.getNumericValue(promoCode.charAt(2)) * 10;
            return "Promotional code " + promoCode + " used: " + discount + "% discount!";
        }
        return "Sorry, code " + promoCode + " is not valid";
    }

}
