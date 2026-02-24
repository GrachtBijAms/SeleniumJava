package com.example.selenium;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.lang.reflect.Field;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        Object testInstance = result.getInstance();
        


        WebDriver driver = null;
        try {
            Class<?> clazz = testInstance.getClass();
            Field driverField = clazz.getDeclaredField("driver");  // Exact field name
            driverField.setAccessible(true);  // Bypass private/protected
            driver = (WebDriver) driverField.get(testInstance);
        } catch (NoSuchFieldException e) {
            System.out.println("❌ No 'driver' field found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (driver != null && driver.toString() != "null") {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            
            Path screenshotDir = Paths.get("screenshots");
            if (!Files.exists(screenshotDir)) {
                try {
                    Files.createDirectories(screenshotDir);  // Creates parents too
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date());  // Millis for unique
            String fileName = "screenshots/" + result.getName() + "_" + timestamp + ".png";
            
            try {
                Files.write(Paths.get(fileName), screenshot);
                System.out.println("✅ Screenshot saved: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("❌ Driver null/quit - Test: " + result.getName());
        }
    }
}
