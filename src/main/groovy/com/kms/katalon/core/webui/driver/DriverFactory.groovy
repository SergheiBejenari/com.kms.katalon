package com.kms.katalon.core.webui.driver


import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

import java.time.Duration

class DriverFactory {

    private static WebDriver driver

    static synchronized WebDriver getWebDriver() {
        if (driver == null) {
            def config = new DriverConfig()
            driver = createDriver(config)
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.pageLoadTimeout))
        }
        return driver
    }

    private static WebDriver createDriver(DriverConfig config) {
        switch (config.browser) {
            case BrowserType.CHROME:
                WebDriverManager.chromedriver().setup()
                ChromeOptions chromeOptions = new ChromeOptions()
                if (config.headless) chromeOptions.addArguments("--headless=new")
                if (config.incognito) chromeOptions.addArguments("--incognito")
                if (config.ignoreCertErrors) chromeOptions.addArguments("--ignore-certificate-errors")
                if (config.disableLogging) chromeOptions.addArguments("--disable-logging")
                chromeOptions.addArguments("--window-size=${config.windowSize}")
                chromeOptions.setExperimentalOption("prefs", [
                        "profile.default_content_setting_values.javascript": 1
                ])
                return new ChromeDriver(chromeOptions)

            case BrowserType.FIREFOX:
                WebDriverManager.firefoxdriver().setup()
                FirefoxOptions firefoxOptions = new FirefoxOptions()
                if (config.headless) firefoxOptions.setHeadless(true)
                return new FirefoxDriver(firefoxOptions)

            case BrowserType.EDGE:
                WebDriverManager.edgedriver().setup()
                return new EdgeDriver()

            default:
                throw new IllegalArgumentException("Unsupported browser: ${config.browser}")
        }
    }
}