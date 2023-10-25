package com.orangehrm.pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;

public class DashboardPage {
    WebDriver driver;
    Wait<WebDriver> wait;

    WebElement text_title;
    WebElement link_my_info;
    WebElement link_header_options;
    WebElement link_logout;

    public DashboardPage(@NotNull WebDriver driver, Wait<WebDriver> wait){
        this.driver = driver;
        this.wait = wait;
        //initElements();
    }

    public void initElements(){
        text_title = wait.until(driver -> this.driver.findElement(By.xpath("//h6[text()='Dashboard']")));
        link_my_info = wait.until(driver -> this.driver.findElement(By.xpath("//span[text()='My Info']/parent::a")));
    }

    public boolean isVisible(){
        initElements();
        return text_title.isDisplayed();
    }

    public MyInfoPage goToMyInfo(){
        initElements();
        link_my_info.click();
        return new MyInfoPage(driver, wait);
    }

    public LoginPage logout(){
        link_header_options = wait.until(driver -> this.driver.findElement(By.xpath("//header/div/div[2]/ul/li[1]/span[1]")));
        link_header_options.click();
        link_logout = wait.until(driver -> this.driver.findElement(By.xpath("//a[text()='Logout']/..")));
        link_logout.click();
        return new LoginPage(driver, wait);
    }
}
