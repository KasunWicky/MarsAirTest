package org.pracTest;

import config.ConfigManager;
import org.testng.annotations.Test;

public class TestCofig {
    @Test
    public void testing1() {
        String browser = ConfigManager.get("browser");
        System.out.println(browser);
    }
}
