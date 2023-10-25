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
import org.testng.annotations.AfterTest;
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
    String employee_id = "E12734";

    SoftAssert softAssert;

    @BeforeTest
    public void setup(){
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();
        //options.addArguments("start-maximized");
        options.addArguments("--remote-allow-origins=*");
       // options.addArguments("--headless");
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

    @Test(testName = "My Info page is visible", priority = 0)
    public void testMyInfoPageVisibility(){
        Assert.assertTrue(myInfoPage.isVisible());
    }

    @Test(testName = "Update SSN", priority = 1, dependsOnMethods = {"testMyInfoPageVisibility"})
    public void testUpdateSSN(){
        softAssert = new SoftAssert();
        String initial_ssn = myInfoPage.getSSN();
        System.out.println("Initial" + initial_ssn);
        myInfoPage.updateSSN(ssn);
        String current_ssn = myInfoPage.getSSN();
        System.out.println("Current" + current_ssn);
        softAssert.assertTrue(current_ssn.equals(ssn));
        myInfoPage.updateSSN(initial_ssn);
        softAssert.assertAll();
    }

    @Test(testName = "Update Employee ID", priority = 2, dependsOnMethods = {"testMyInfoPageVisibility"})
    public void testUpdateEmployeeID() {
        softAssert = new SoftAssert();

        String initial_employee_id = myInfoPage.getEmployeeId();
        System.out.println("Initial: "+initial_employee_id);

        // Update the Employee ID to a new value
        myInfoPage.updateEmployeeId(employee_id);

        // Get the current employee ID after the update
        String current_employee_id = myInfoPage.getEmployeeId();
        System.out.println("Current: "+current_employee_id);

        // Verify that the current employee ID matches the updated value
        softAssert.assertTrue(current_employee_id.equals(employee_id), "Employee ID was updated correctly");
        myInfoPage.updateEmployeeId(initial_employee_id);

        // Assert all soft assertions
        softAssert.assertAll();
    }

    @AfterTest
    public void tearDown(){
        //driver.close();
       // driver.quit();
    }

}
