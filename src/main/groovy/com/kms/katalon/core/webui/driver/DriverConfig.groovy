package com.kms.katalon.core.webui.driver

class DriverConfig {
    final String browser
    final boolean headless
    final boolean incognito
    final boolean ignoreCertErrors
    final boolean disableLogging
    final String windowSize
    final int pageLoadTimeout

    DriverConfig() {
        browser = BrowserType.from(ConfigReader.get("browser", "chrome"))
        headless = ConfigReader.getBoolean("headless", false)
        incognito = ConfigReader.getBoolean("incognito", true)
        ignoreCertErrors = ConfigReader.getBoolean("ignore-certificate-errors", true)
        disableLogging = ConfigReader.getBoolean("disable-logging", true)
        windowSize = ConfigReader.get("window-size", "1920,1080")
        pageLoadTimeout = ConfigReader.getInt("pageLoadTimeout", 10)
    }
}