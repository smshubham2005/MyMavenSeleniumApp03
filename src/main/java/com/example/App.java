package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class App {
    public static void main(String[] args) {

        // Set ChromeOptions to ignore SSL errors
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--disable-web-security");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // Spoof user-agent to avoid bot detection / rate limiting
        options.addArguments("--user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/146.0.0.0 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://practicetestautomation.com/practice-test-login/");
            driver.manage().window().maximize();

            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page Title : " + driver.getTitle());

            // Wait 3 seconds to avoid rate limiting (429)
            Thread.sleep(3000);

            // Wait up to 30s for elements to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("username"))
            );
            usernameField.sendKeys("student");

            WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("password"))
            );
            passwordField.sendKeys("Password123");

            WebElement submitBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("submit"))
            );
            submitBtn.click();

            // Wait for successful login - verify post-login page
            wait.until(ExpectedConditions.urlContains("logged-in-successfully"));
            System.out.println("Login Successful! Current URL: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.err.println("Test Failed: " + e.getMessage());
            String pageSource = driver.getPageSource();
            System.out.println("Page Source: "
                + pageSource.substring(0, Math.min(1000, pageSource.length())));
        } finally {
            // Always quit the driver
            driver.quit();
        }
    }
}
