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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class MyInfoPageTests {
    ChromeOptions options;
    WebDriver driver;
    Wait<WebDriver> wait;
    DashboardPage dashboardPage;
    LoginPage loginPage;
    MyInfoPage myInfoPage;

    String ssn = "SSN1234";
    String employee_id = "E1234";

    SoftAssert softAssert;

    @BeforeTest
    public void setup(){
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();
        //options.addArguments("start-maximized");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(1));
        loginPage = new LoginPage(driver, wait);

        String username = "Admin";
        String password = "admin123";
        dashboardPage = loginPage.login(username, password);
        myInfoPage = dashboardPage.goToMyInfo();
        myInfoPage.initElements();
    }

    @Test(testName = "My Info page is visible")
    public void testMyInfoPageVisibility(){
        Assert.assertTrue(myInfoPage.isVisible());
    }

    @Test(testName = "Update SSN")
    public void testUpdateSSN(){
        softAssert = new SoftAssert();
        String initial_ssn = myInfoPage.getSSN();
        myInfoPage.updateSSN(ssn);
        String current_ssn = myInfoPage.getSSN();
        myInfoPage.updateSSN(initial_ssn);
        softAssert.assertTrue(current_ssn.equals(ssn));
        softAssert.assertAll();
    }

    @Test(testName = "Update Employee ID")
    public void testUpdateEmployeeID(){
        softAssert = new SoftAssert();
        String initial_employee_id = myInfoPage.getEmployeeId();
        myInfoPage.updateEmployeeId(employee_id);
        String current_employee_id = myInfoPage.getEmployeeId();
        myInfoPage.updateSSN(initial_employee_id);
        softAssert.assertTrue(current_employee_id.equals(employee_id));
        softAssert.assertAll();
    }
}
