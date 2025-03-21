package com.kms.katalon.core.testcase

class TestCaseFactory {
    static def findTestCase(String methodName) {
        // Загружаем скрипт по его имени
        Class scriptClass = Class.forName(methodName)
        // Создаем экземпляр скрипта
        Script scriptInstance = scriptClass.newInstance()
        return scriptInstance;
    }
}
