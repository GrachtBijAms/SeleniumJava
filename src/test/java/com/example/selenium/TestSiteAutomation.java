package com.example.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class TestSiteAutomation {

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


    @Test
    public void testSearchProducts() {
        // Locate search input
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(By.id("search")));
        WebElement searchButton = driver.findElement(By.xpath("//button[text()='Search']"));

        // Search for "Keyboard"
        searchField.sendKeys("Keyboard");
        searchButton.click();

        // Handle alert
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        // Wait for results
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='Keyboard']")));

        // Assert that Keyboard is in results
        WebElement product = driver.findElement(By.xpath("//td[text()='Keyboard']"));
        assertTrue(product.isDisplayed());
    }

    @Test
    public void testAddToCart() {
        // Find the first Add to Cart button
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add to Cart']")));

        // Click it
        addToCartButton.click();

        // Check cart count
        WebElement cartCount = driver.findElement(By.id("cart-count"));
        wait.until(ExpectedConditions.textToBePresentInElement(cartCount, "1"));

        assertTrue(cartCount.getText().equals("1"));
    }

    @Test
    public void testLoginWithPageObject() {
        // Use Page Object Model with PageFactory
        homePage.login("admin", "password123");

        // Verify login (similar to before)
        wait.until(ExpectedConditions.titleContains("Test Automation Practice Site"));
        assertTrue(driver.getTitle().contains("Test Automation Practice Site"));
    }

    @Test
    public void testSearchAndAddToCartWithPageObject() {
        // Search for product
        homePage.searchProduct("Keyboard");

        // Verify product is displayed
        assertTrue(homePage.isProductDisplayed());

        // Add to cart
        homePage.addToCart();

        // Verify cart count
        assertTrue(homePage.getCartCount().equals("1"));

    }
    @Test
    public void testOpenNewWindow() {
        // Click the button to open a new window
        homePage.clickOpenNewWindow(); 
        // Store parent
        String parent = driver.getWindowHandle();
        System.out.println("Parent Window Handle: " + parent);
        homePage.clickOpenNewWindow(); 
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(parent)) {
                driver.switchTo().window(handle);
                wait.until(ExpectedConditions.titleIs("Google"));
                // Interact here
                driver.close();
            }
        }
        driver.switchTo().window(parent); // Back to main
        wait.until(ExpectedConditions.titleContains("Test Automation Practice Site"));
        assertTrue(homePage.getWindowMessage().equals("New window opened successfully!"));

    }

    @Test
    public void testOpenSeleniumDevWindowWithHelper() {
        WindowHelper windowHs = new WindowHelper(driver);
        // Click the button to open Selenium.dev window
        homePage.clickOpenSeleniumDevWindow();
        // Store parent
        windowHs.switchToNewWindow();
        wait.until(ExpectedConditions.titleContains("Selenium"));
        // Interact here, e.g., check title
        assertTrue(driver.getTitle().contains("Selenium"));
        // Close new window and switch back
        windowHs.closeNewWindowAndSwitchBack();
        // Verify we're back on the main page
        assertTrue(driver.getTitle().contains("Test Automation Practice Site"));
    }

    @Test
    public void testiframeclick() {
        // Switch to iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("info-frame")));
        // Click the button inside iframe
        WebElement iframeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("frame-btn")));
        iframeButton.click();
        // Verify the message after clicking
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("frame-title")));
        assertTrue(message.getText().equals("Clicked inside iframe!"));
        // Switch back to main content
        driver.switchTo().defaultContent();
    }

}
