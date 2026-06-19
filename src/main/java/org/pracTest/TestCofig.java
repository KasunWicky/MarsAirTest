package org.pracTest;

import config.EnvConfig;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
@Slf4j
public class TestCofig {
    @Test
    public void testing1() {
        log.info("Test");
        String browser = EnvConfig.BROWSER;
        String user = EnvConfig.USER;
        System.out.println(browser);
        System.out.println(user);
    }
}
