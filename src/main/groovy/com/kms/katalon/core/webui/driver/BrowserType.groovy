package com.kms.katalon.core.webui.driver;

public enum BrowserType {
    CHROME,
    FIREFOX,
    EDGE;

    static BrowserType from(String name) {
        if (!name) throw new IllegalArgumentException("Browser name is null or empty")
        try {
            return BrowserType.valueOf(name.trim().toUpperCase())
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported browser type: $name", e)
        }
    }
}