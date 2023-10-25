package com.orangehrm.tests;

import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.MyInfoPage;
import io.github.bonigarcia.wdm.WebDriverManager;
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

public class DashboardPageTests {
    ChromeOptions options;
    WebDriver driver;
    Wait<WebDriver> wait;
    DashboardPage dashboardPage;
    LoginPage loginPage;
    MyInfoPage myInfoPage;

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

        String username = "Admin";
        String password = "admin123";
        dashboardPage = loginPage.login(username, password);
        dashboardPage.initElements();
    }

    @Test(testName = "Dashboard page is visible", priority = 0)
    public void testDashboardPageVisibility(){
        Assert.assertTrue(dashboardPage.isVisible());
    }

    @Test(testName = "Go to My Info Page", priority = 1)
    public void testGoToMyInfo(){
        myInfoPage = dashboardPage.goToMyInfo();
        Assert.assertTrue(myInfoPage.isVisible());
    }

    @Test(testName = "Logout", priority = 2)
    public void testLogout(){
        loginPage = dashboardPage.logout();
        Assert.assertTrue(loginPage.isVisible());
    }

    @AfterTest
    public void tearDown(){
        driver.close();
        driver.quit();
    }
}
