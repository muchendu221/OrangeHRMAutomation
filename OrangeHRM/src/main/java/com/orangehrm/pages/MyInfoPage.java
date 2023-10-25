package com.orangehrm.pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;



public class MyInfoPage {
    WebDriver driver;
    Wait<WebDriver> wait;
    WebElement text_title;
    WebElement textbox_ssn;
    WebElement textbox_employee_id;
    WebElement button_save;

    public MyInfoPage(@NotNull WebDriver driver, Wait<WebDriver> wait){
        this.driver = driver;
        this.wait = wait;
        initElements();
    }

    public void initElements(){
        text_title = driver.findElement(By.xpath("//h6[text()='Personal Details']"));
        textbox_ssn = driver.findElement(By.xpath("//label[text()='SSN Number']/../following-sibling::div/input"));
        textbox_employee_id = driver.findElement(By.xpath("//label[text()='Employee Id']/../following-sibling::div/input"));
        button_save = driver.findElement(By.xpath("//p[text()[contains(.,'Required')]]/../button"));
    }
    public boolean isVisible(){
        initElements();
        return text_title.isDisplayed();
    }

    public String getSSN(){
        return textbox_ssn.getText();
    }

    public void updateSSN(String ssn){
        textbox_ssn.click();
        textbox_ssn.clear();
        textbox_ssn.sendKeys(ssn);
        button_save.click();
    }

    public void updateEmployeeId(String employee_id){
        textbox_employee_id.click();
        textbox_employee_id.clear();
        textbox_employee_id.sendKeys(employee_id);
        button_save.click();
    }

    public String getEmployeeId(){
        return textbox_employee_id.getText();
    }
}
