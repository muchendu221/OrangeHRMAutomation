package com.orangehrm.pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Wait;

public class LoginPage {
    WebDriver driver;
    Wait<WebDriver> wait;

    WebElement textbox_username;
    WebElement textbox_password;
    WebElement button_login;

    public LoginPage(@NotNull WebDriver driver, Wait<WebDriver> wait){
        this.driver = driver;
        this.wait = wait;
        initElements();
    }

    public void initElements(){
        textbox_username = driver.findElement(By.xpath("//input[@name='username']"));
        textbox_password = driver.findElement(By.xpath("//input[@name='password']"));
        button_login = driver.findElement(By.xpath("//button[text()[contains(.,'Login')]]"));
    }

    public boolean isVisible(){
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
            WebElement text_login_error = wait.until(driver -> this.driver.findElement(By.xpath("//p[text()[contains(.,'Invalid credentials')]]")));
            return text_login_error.isDisplayed();
        }catch (NoSuchElementException nse){
            // invalid login error not shown
            return false;
        }
    }
}
