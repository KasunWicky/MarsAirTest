package runner;

import driver.Driver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class ScreenshotListener implements ITestListener {

    private static final String SCREENSHOT_DIR = "target/screenshots";
    private static final DateTimeFormatter TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = Driver.getDriver();
        if (driver == null) {
            log.error("[Screenshot] No driver available");
            return;
        }

        String fileName = result.getName() + "_" + LocalDateTime.now().format(TIMESTAMP) + ".png";

        try {
            Path dir = Paths.get(SCREENSHOT_DIR);
            Files.createDirectories(dir);

            Path dest = dir.resolve(fileName);
            byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(dest, bytes);

            log.info("[Screenshot] Saved: {}", dest.toAbsolutePath());

        } catch (IOException e) {
            log.error("[Screenshot] Failed: {}", e.getMessage());
        }
    }
}
