package org.pracTest;

import config.ConfigManager;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
@Slf4j
public class TestCofig {
    @Test
    public void testing1() {
        log.info("Test");
        String browser = ConfigManager.get("browser");
        System.out.println(browser);
    }
}
