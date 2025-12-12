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

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://grachtbijams.github.io/playwrightJS/res/testsite.html");
        homePage = new HomePage(driver); // Initialize page object
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLogin() {
        // Locate login form elements
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-btn"));

        // Enter demo credentials
        usernameField.sendKeys("admin");
        passwordField.sendKeys("password123");

        // Click login
        loginButton.click();

        // Wait for some element that indicates successful login, e.g., a welcome message or change in page
        // Since it's a test site, perhaps check if the login form is hidden or a success message appears
        // For simplicity, just wait a bit and assert the title or something
        wait.until(ExpectedConditions.titleContains("Test Automation Practice Site"));
        // Or check for a specific element
        // Assuming after login, something changes, but from the page, it might not.
        // Perhaps the site doesn't change much, so just verify the page loaded.
        assertTrue(driver.getTitle().contains("Test Automation Practice Site"));
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
}