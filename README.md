# Selenium Java Automation Project

This project demonstrates Selenium WebDriver automation for the test site: https://grachtbijams.github.io/playwrightJS/res/testsite.html

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Chrome browser installed

## Setup

1. Clone or download the project.
2. Navigate to the project directory.
3. Run `mvn clean install` to download dependencies.

## Running Tests

Run the tests using Maven:

```bash
mvn test
```

This will execute the test methods in `TestSiteAutomation.java`, which include:
- Logging in with demo credentials
- Searching for products
- Adding items to cart

The tests use TestNG framework with Page Object Model and PageFactory.

## Dependencies

- Selenium WebDriver 4.15.0
- WebDriverManager 5.5.3 (for automatic ChromeDriver management)
- TestNG 7.8.0

## Notes

- WebDriverManager automatically downloads the correct ChromeDriver version.
- Tests are headless by default; modify the ChromeOptions if needed for visible browser.