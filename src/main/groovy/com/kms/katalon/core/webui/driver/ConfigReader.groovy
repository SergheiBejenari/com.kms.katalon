package com.kms.katalon.core.webui.driver

import com.kms.katalon.common.PropertyReader

class ConfigReader {
    private static final Properties props = PropertyReader.applicationProperties()

    static String get(String key, String defaultValue = "") {
        return props.getProperty(key, defaultValue)
    }

    static boolean getBoolean(String key, boolean defaultValue = false) {
        return props.getProperty(key, String.valueOf(defaultValue)).toBoolean()
    }

    static int getInt(String key, int defaultValue = 0) {
        return props.getProperty(key, String.valueOf(defaultValue)) as int
    }
}