package com.kms.katalon.common

class PropertyReader {
    static Properties applicationProperties() {
        Properties props = new Properties()
        def stream = Thread.currentThread().contextClassLoader.getResourceAsStream("application.properties")
        if (stream == null) {
            throw new RuntimeException("application.properties not found in classpath")
        }
        props.load(stream)
        return props
    }
}