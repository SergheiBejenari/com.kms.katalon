package com.kms.katalon.core.webui.keyword

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import io.qameta.allure.Step
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.testng.asserts.Assertion

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class WebUiBuiltInKeywords {
    private static WebDriver driver = DriverFactory.getWebDriver();
    private static Assertion assertion = new Assertion();

    static void openBrowser(String url) {
        driver.get(url)
    }

    static def void closeBrowser() {
        driver.close();
    }

    static def void maximizeWindow() {
        driver.manage().window().maximize();
    }

    static def void navigateToUrl(String url) {
        driver.navigate().to(url);
    }

    static def void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    @Step
    static def boolean verifyEqual(String expected, String actual) {
        return assertion.assertEquals(actual, expected)
    }

    static boolean verifyNotEqual(String expected, String actual) {
        return assertion.assertEquals(actual, expected)
    }

    static def boolean verifyEqual(boolean expected, boolean actual) {
        return assertion.assertEquals(actual, expected)
    }

    //TODO: This method is created full page screenshot / Need to figure out how to fix filePath name how to get GlobalVariable.SuiteName (NAME of testScript -> good idea is used Package name) see Listeners in Katalon
    static def void takeScreenshot(String filePath) {
        Thread.sleep(1000)
        try {
            // Прокрутка страницы до конца
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            // Создание скриншота всей видимой области
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Проверка и создание директории, если она не существует
            Path directoryPath = Paths.get(filePath).getParent();
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Сохранение скриншота в файл
            File outputFile = new File(filePath); // Укажите путь и имя файла
            screenshotFile.renameTo(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static def void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    static void verifyEqual(String expected, String actual, FailureHandling failureHandling) {
        try {
            assert actual == expected: "Expected: " + expected + " but was: " + actual;
        } catch (AssertionError e) {
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            switch (failureHandling) {
                case FailureHandling.STOP_ON_FAILURE:
                    throw e
                case FailureHandling.CONTINUE_ON_FAILURE:
                    System.err.println("Expected: " + Arrays.toString(expected))
                    System.err.println("Actual: " + Arrays.toString(actual))
                    System.err.println(e.printStackTrace())
                    break;
                case FailureHandling.OPTIONAL:
                    System.err.println("INFO: Verification failed in method: " + methodName + ". Expected: " + expected + " but was: " + actual + " (optional check)");
                    break;
            }
        }
    }

    static def void verifyEqual(List<String> expected, List<String> actual, FailureHandling failureHandling) {
        try {
            assert expected.size() == actual.size(): "Expected size: ${expected.size()} but was: ${actual.size()}"
            for (int i = 0; i < expected.size(); i++) {
                assert expected[i] == actual[i]: "At index $i, actual: ${actual[i]} but was: ${expected[i]}"
            }
        } catch (AssertionError | MissingPropertyException e) {
            switch (failureHandling) {
                case FailureHandling.STOP_ON_FAILURE:
                    throw e
                case FailureHandling.CONTINUE_ON_FAILURE:
                    System.err.println("Expected: " + Arrays.toString(expected))
                    System.err.println("Actual: " + Arrays.toString(actual))
                    System.err.println(e.printStackTrace())
                    break
                case FailureHandling.OPTIONAL:
                    System.err.println("INFO: Expected: $expected but was: $actual (optional check)")
                    break
            }

        }
    }

    static def void callTestCase(String methodName, HashMap parameters, FailureHandling failureHandling) {
        try {
            TestCaseFactory.findTestCase(methodName).run(parameters)
        } catch (ClassNotFoundException e) {
            System.err.println "Script $methodName not found: ${e.message}"
            throw new RuntimeException("Error running script $methodName", e)
        } catch (Exception e) {
            System.err.println "Error running script $methodName: ${e.message}"
            throw new RuntimeException("Error running script $methodName", e)
        }
    }

    static def boolean verifyEqual(boolean expected, boolean actual, FailureHandling failureHandling) {
        try {
            assert actual == expected: "Expected: $expected but was: $actual"
            return true
        } catch (AssertionError e) {
            switch (failureHandling) {
                case FailureHandling.STOP_ON_FAILURE:
                    throw e
                case FailureHandling.CONTINUE_ON_FAILURE:
                    System.err.println(e.printStackTrace())
                    break
                case FailureHandling.OPTIONAL:
                    System.err.println("INFO: Expected: $expected but was: $actual (optional check)")
                    break
            }
            return false
        }
    }

    static def void verifyEqual(String[] expected, String[] actual, FailureHandling failureHandling) {
        try {
            assert expected.length == actual.length: "Expected length: ${expected.length} but was: ${actual.length}"
            for (int i = 0; i < expected.length; i++) {
                assert expected[i] == actual[i]: "At index $i, actual: ${actual[i]} but was: ${expected[i]}"
            }
        } catch (AssertionError e) {
            switch (failureHandling) {
                case FailureHandling.STOP_ON_FAILURE:
                    throw e
                case FailureHandling.CONTINUE_ON_FAILURE:
                    System.err.println(e.printStackTrace())
                    break
                case FailureHandling.OPTIONAL:
                    System.err.println("INFO: Expected: ${Arrays.toString(expected)} but was: ${Arrays.toString(actual)} (optional check)")
                    break
            }
        }
    }

//    static void verifyEqual(String[] expected, String[] actual, FailureHandling failureHandling) {
//        try {
//            List<String> expectedList = Arrays.asList(expected);
//            List<String> actualList = Arrays.asList(actual);
//
//            boolean listsMatch = expectedList.equals(actualList);
//
//            if (!listsMatch) {
//                switch (failureHandling) {
//                    case FailureHandling.STOP_ON_FAILURE:
//                        throw new AssertionError("Lists do not match. Expected: " + expectedList + " but found: " + actualList);
//                    case FailureHandling.CONTINUE_ON_FAILURE:
//                        System.err.println("Lists do not match. Expected: " + expectedList + " but found: " + actualList);
//                        break;
//                    case FailureHandling.OPTIONAL:
//                        System.err.println("INFO: Lists do not match. Expected: " + expectedList + " but found: " + actualList + " (optional check)");
//                        break;
//                }
//            }
//        } catch (AssertionError e) {
//            switch (failureHandling) {
//                case FailureHandling.STOP_ON_FAILURE:
//                    throw e;
//                case FailureHandling.CONTINUE_ON_FAILURE:
//                    print(e.printStackTrace());
//                    break;
//                case FailureHandling.OPTIONAL:
//                    System.err.println("INFO: " + e.getMessage() + " (optional check)");
//                    e.printStackTrace();
//                    break;
//            }
//        }
//    }

    static def void verifyMatch(String expected, String actual, boolean aBoolean, FailureHandling failureHandling) {
        try {
            if (aBoolean) {
                assert expected == actual: "Expected strings to match: '$expected' and '$actual' but they do not match"
            } else {
                assert expected != actual: "Expected strings not to match: '$expected' and '$actual' but they match"
            }
        } catch (AssertionError e) {
            switch (failureHandling) {
                case FailureHandling.STOP_ON_FAILURE:
                    throw e
                case FailureHandling.CONTINUE_ON_FAILURE:
                    System.err.println(e.printStackTrace())
                    break
                case FailureHandling.OPTIONAL:
                    System.err.println("INFO: Verification failed. Expected: '$expected' and '$actual' with condition $aBoolean (optional check)")
                    break
            }
        }
    }

    static def void verifyEqual(int expected, int actual, FailureHandling failureHandling) {
        try {
            assert actual == expected: "Expected: " + expected + " but was: " + actual;
        } catch (AssertionError e) {
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            switch (failureHandling) {
                case FailureHandling.STOP_ON_FAILURE:
                    throw e
                case FailureHandling.CONTINUE_ON_FAILURE:
                    System.err.println(e.printStackTrace())
                    break;
                case FailureHandling.OPTIONAL:
                    System.err.println("INFO: Verification failed in method: " + methodName + ". Expected: " + expected + " but was: " + actual + " (optional check)");
                    break;
            }
        }
    }

    static def void comment(String message) {
        println(message)
    }

//    @Attachment(value = "Screenshot", type = "image/png")
//    public byte[] attachScreenshotToAllure(File screenshot) {
//        try {
//            return FileUtils.readFileToByteArray(screenshot);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new byte[0];
//        }
//    }
}