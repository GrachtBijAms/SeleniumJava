package com.example.selenium;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestAutomationLogin {
    

    private WebDriver driver;
    private WebDriverWait wait;
    private HomePage homePage;
    String url = "https://grachtbijams.github.io/playwrightJS/res/testsite.html";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(url);
        homePage = new HomePage(driver); // Initialize page object
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
            {"admin", "password123", true}, // Valid credentials
            {"admin", "wrongpassword", false}, // Invalid password
            {"wronguser", "password123", false}, // Invalid username
            {"", "", false} // Empty credentials
        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, boolean expected) {
        // Locate login form elements
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-btn"));

        // Enter demo credentials
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        // Click login
        loginButton.click();

        // Wait for some element that indicates successful login, e.g., a welcome message or change in page
        // Since it's a test site, perhaps check if the login form is hidden or a success message appears
        // For simplicity, just wait a bit and assert the title or something
        if (expected) {
            wait.until(ExpectedConditions.textToBe(By.id("login-message"), "Login successful!"));
            assertTrue(driver.getTitle().contains("Test Automation Practice Site"));
        } else {
            // For invalid login, maybe check for an error message or that the login form is still visible
            WebElement loginMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-message")));
            assertTrue(loginMessage.getText().contains("Invalid credentials") || loginMessage.getText().isEmpty());
        }

    }


}
