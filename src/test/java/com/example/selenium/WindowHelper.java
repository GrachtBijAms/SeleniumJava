package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.Set;

public class WindowHelper {
    private WebDriver driver;
    private WebDriverWait wait;
    private String parentWindow;

    public WindowHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.parentWindow = driver.getWindowHandle();
    }

    public void switchToNewWindow() {
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(parentWindow)) {
                driver.switchTo().window(handle);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
                return;
            }
        }
        throw new RuntimeException("No new window found");
    }

    public void closeNewWindowAndSwitchBack() {
        driver.close();
        driver.switchTo().window(parentWindow);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
    }

    public String getParentHandle() {
        return parentWindow;
    }
}

