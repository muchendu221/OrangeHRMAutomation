package com.orangehrm.pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    Wait<WebDriver> wait;

    WebElement textbox_username;
    WebElement textbox_password;
    WebElement button_login;
    WebElement text_login_error;

    public LoginPage(@NotNull WebDriver driver, Wait<WebDriver> wait){
        this.driver = driver;
        this.wait = wait;
        WebDriver.Timeouts timeouts=  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        initElements();
    }

    public void initElements(){
        textbox_username = driver.findElement(By.xpath("//input[@name='username']"));
        textbox_password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        button_login = driver.findElement(By.xpath("//button[text()[contains(.,'Login')]]"));
    }

    public boolean isVisible(){
        initElements();
        return button_login.isDisplayed();
    }

    public DashboardPage login(String username, String password){
        textbox_username.clear();
        textbox_username.sendKeys(username);
        textbox_password.clear();
        textbox_password.sendKeys(password);
        button_login.click();

        return new DashboardPage(driver, wait);
    }

    public boolean hasLoginError(){
        try {
            text_login_error = wait.until(driver -> this.driver.findElement(By.xpath("//p[text()[contains(.,'Invalid credentials')]]")));
            return text_login_error.isDisplayed();
        }catch (NoSuchElementException nse){
            return false;
        }
    }
}
