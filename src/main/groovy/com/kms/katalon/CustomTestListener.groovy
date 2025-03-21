package com.kms.katalon

import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.testng.ITestContext
import org.testng.ITestListener
import org.testng.ITestResult

class CustomTestListener implements ITestListener {
    WebDriver driver

    @Override
    void onStart(ITestContext context) {
        println("Test Suite Started: " + context.getName())
    }

    @Override
    void onFinish(ITestContext context) {
        println("Test Suite Finished: " + context.getName())
    }

    @Override
    void onTestStart(ITestResult result) {
        println("Test Case Started: " + result.getName())
    }

    @Override
    void onTestSuccess(ITestResult result) {
        println("Test Case Passed: " + result.getName())
    }

    @Override
    void onTestFailure(ITestResult result) {
        println("Test Case Failed: " + result.getName())
        // Добавление скриншота при ошибке
        takeScreenshot(result)
    }

    @Override
    void onTestSkipped(ITestResult result) {
        println("Test Case Skipped: " + result.getName())
    }

    @Override
    void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Не используется
    }

    void setDriver(WebDriver driver) {
        this.driver = driver
    }

    public void takeScreenshot(ITestResult result) {
        if (driver instanceof TakesScreenshot) {
            try {
                // Capture the screenshot
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String screenshotPath = "build/screenshots/" + result.getName() + ".png";
                File destination = new File(screenshotPath);
                destination.mkdirs();
                FileUtils.copyFile(screenshot, destination);

                // Attach the screenshot to Allure
                WebUI.attachScreenshotToAllure(screenshot);

                System.out.println("Screenshot captured: " + screenshotPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}