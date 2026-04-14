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

        ChromeOptions options = new ChromeOptions();

        // Your SSL / security flags (keeping them since you insisted)
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--disable-web-security");

        // Required for Linux / pipeline
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Conditional headless setup
        String headless = System.getProperty("headless");

        if ("true".equals(headless)) {
            options.addArguments("--headless=new");
        }

        // User agent (fine, whatever makes you feel safe)
        options.addArguments("--user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/146.0.0.0 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://practicetestautomation.com/practice-test-login/");
            driver.manage().window().maximize();

            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page Title : " + driver.getTitle());

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // Username
            WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("username"))
            );
            usernameField.sendKeys("student");

            // Password
            WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("password"))
            );
            passwordField.sendKeys("Password123");

            // Click login
            WebElement submitBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("submit"))
            );
            submitBtn.click();

            // Validate success
            wait.until(ExpectedConditions.urlContains("logged-in-successfully"));
            System.out.println("Login Successful! URL: " + driver.getCurrentUrl());

        } catch (Exception e) {
            System.err.println("Test Failed: " + e.getMessage());

            String pageSource = driver.getPageSource();
            System.out.println("Page Source (partial): "
                    + pageSource.substring(0, Math.min(1000, pageSource.length())));

        } finally {
            driver.quit();
        }
    }
}
