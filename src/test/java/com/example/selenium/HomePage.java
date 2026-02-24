package com.example.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;
        // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this); // Initialize elements
    }

    // Page elements using @FindBy
    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-btn")
    private WebElement loginButton;

    @FindBy(id = "search")
    private WebElement searchField;

    @FindBy(xpath = "//button[text()='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//td[text()='Keyboard']")
    private WebElement productResult;

    @FindBy(xpath = "//button[text()='Add to Cart']")
    private WebElement addToCartButton;

    @FindBy(id = "cart-count")
    private WebElement cartCount;

    @FindBy(id="open-new-window-btn")
    private WebElement openNewWindowButton;

    @FindBy(id="window-message")
    private WebElement windowMessage;

    @FindBy(xpath = "//button[.='Open Selenium.dev Window']")
    private WebElement openSeleniumDevWindowButton;



    // Methods to interact with elements
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.elementToBeClickable(usernameField));
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.elementToBeClickable(searchField));
        searchField.sendKeys(productName);
        searchButton.click();
        // Handle the alert that appears after search
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public boolean isProductDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(productResult));
        return productResult.isDisplayed();
    }

    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }

    public String getCartCount() {
        wait.until(ExpectedConditions.textToBePresentInElement(cartCount, "1"));
        return cartCount.getText();
    }


    public void clickOpenNewWindow( ) {
        wait.until(ExpectedConditions.elementToBeClickable(openNewWindowButton));
         openNewWindowButton.click();
    }

    public String getWindowMessage() {
        wait.until(ExpectedConditions.visibilityOf(windowMessage));
        return windowMessage.getText();
    }  
    
    public void clickOpenSeleniumDevWindow() {
        wait.until(ExpectedConditions.elementToBeClickable(openSeleniumDevWindowButton));
        openSeleniumDevWindowButton.click();
    }
}