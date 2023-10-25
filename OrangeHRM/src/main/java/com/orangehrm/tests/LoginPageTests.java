package com.orangehrm.tests;

import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginPageTests {
    ChromeOptions options;
    WebDriver driver;
    Wait<WebDriver> wait;
    LoginPage loginPage;
    DashboardPage dashboardPage;

    @BeforeTest
    public void setup(){
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();
        //options.addArguments("start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(1));
        loginPage = new LoginPage(driver, wait);
        Reporter.log("Test setup done");
    }

    @Test(testName = "Login page is visible", priority = 0)
    public void testLoginPageVisibility(){
        Assert.assertTrue(loginPage.isVisible());
        //Assert.assertTrue(loginPage.hasLoginError());
    }

    @Test(testName = "Login using invalid username", priority = 1)
    public void testLoginInvalidUsername(){
        String username = "test";
        String password = "admin123";

        dashboardPage = loginPage.login(username, password);
        Assert.assertTrue(loginPage.isVisible());
        Assert.assertTrue(loginPage.hasLoginError());
    }

    @Test(testName = "Login using invalid password", priority = 2)
    public void testLoginInvalidPassword(){
        String username = "Admin";
        String password = "123admin";

        dashboardPage = loginPage.login(username, password);
        Assert.assertTrue(loginPage.isVisible());
        Assert.assertTrue(loginPage.hasLoginError());
    }

    @Test(testName = "Login using invalid username and password", priority = 3)
    public void testLoginInvalidCredentials(){
        String username = "nimdA";
        String password = "123admin";

        dashboardPage = loginPage.login(username, password);
        Assert.assertTrue(loginPage.isVisible());
        Assert.assertTrue(loginPage.hasLoginError());
    }

    @Test(testName = "Login using valid username and password", priority = 4)
    public void testLoginValidCredentials(){
        String username = "Admin";
        String password = "admin123";

        dashboardPage = loginPage.login(username, password);
        Assert.assertTrue(dashboardPage.isVisible());
    }

    @AfterTest
    public void tearDown(){
        driver.close();
        driver.quit();
    }
}
