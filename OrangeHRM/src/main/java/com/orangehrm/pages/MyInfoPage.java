package com.orangehrm.pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;


public class MyInfoPage {
    WebDriver driver;
    Wait<WebDriver> wait;
    WebElement text_title;
    WebElement textbox_ssn;
    WebElement textbox_employee_id;
    WebElement button_save;
    Actions actions;

    public MyInfoPage(@NotNull WebDriver driver, Wait<WebDriver> wait){
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(driver);
        initElements();
    }

    public void initElements(){
        text_title = driver.findElement(By.xpath("//h6[text()='Personal Details']"));
        textbox_ssn = wait.until(driver->this.driver.findElement(By.xpath("//label[text()='SSN Number']/../following-sibling::div/input")));
        textbox_employee_id = wait.until(driver->this.driver.findElement(By.xpath("//label[text()='Employee Id']/../following-sibling::div/input")));
        button_save = driver.findElement(By.xpath("//p[text()[contains(.,'Required')]]/following-sibling::button"));
    }
    public boolean isVisible(){
        initElements();
        return text_title.isDisplayed();
    }
    /*public String getSSN(){
        wait.until(ExpectedConditions.visibilityOf(textbox_ssn));

        // Check if the value attribute is empty
        String ssnValue = textbox_ssn.getAttribute("value");
        if (ssnValue != null && !ssnValue.isEmpty()) {
            return textbox_ssn.getAttribute("value").trim();
        }

        // Return an empty string or handle the case as needed
        return "";
    }*/
    public String getSSN(){
        wait.until(ExpectedConditions.visibilityOf(textbox_ssn));
        return textbox_ssn.getAttribute("value").trim();
    }

    public void updateSSN(String ssn){
        wait.until(ExpectedConditions.elementToBeClickable(textbox_ssn));
        String current_ssn = textbox_ssn.getAttribute("value").trim();
        while(!current_ssn.isEmpty()){
            textbox_ssn.sendKeys(Keys.BACK_SPACE);
            current_ssn = textbox_ssn.getAttribute("value").trim();
        }

//        textbox_ssn.clear();
//        wait.until(ExpectedConditions.elementToBeClickable(textbox_ssn));
        textbox_ssn.sendKeys(ssn);
        wait.until(ExpectedConditions.attributeToBe(textbox_ssn, "value", ssn));
        wait.until(ExpectedConditions.elementToBeClickable(button_save));
        actions.scrollToElement(button_save).perform();
        try {
            button_save.click();
        } catch (ElementClickInterceptedException eci){
            try {
                WebElement popup = driver.findElement(By.xpath("//p[text()='Successfully Updated']"));
                wait.until(ExpectedConditions.invisibilityOf(popup));
            } catch (NoSuchElementException nse){
                // popup not shown
            }
            button_save.click();

            // if save is clicked, wait until popup disappears for the SSN number to update
            try {
                WebElement popup = driver.findElement(By.xpath("//p[text()='Successfully Updated']"));
                wait.until(ExpectedConditions.invisibilityOf(popup));
            } catch (NoSuchElementException nse){
                // popup not shown
                nse.printStackTrace();
            }
        }
    }

    public void updateEmployeeId(String employee_id){
        String current_ssn = textbox_employee_id.getAttribute("value").trim();
        while(!current_ssn.isEmpty()){
            textbox_employee_id.sendKeys(Keys.BACK_SPACE);
            current_ssn = textbox_employee_id.getAttribute("value").trim();
        }

        textbox_employee_id.sendKeys(employee_id);
        wait.until(ExpectedConditions.elementToBeClickable(button_save));
        actions.scrollToElement(button_save).perform();

        try {
            button_save.click();
        } catch (ElementClickInterceptedException eci){
            try {
                WebElement popup = driver.findElement(By.xpath("//p[text()='Successfully Updated']"));
                wait.until(ExpectedConditions.invisibilityOf(popup));
            } catch (NoSuchElementException nse){
                // popup not shown
            }
            button_save.click();

            // if save is clicked, wait until popup disappears for the SSN number to update
            try {
                WebElement popup = driver.findElement(By.xpath("//p[text()='Successfully Updated']"));
                wait.until(ExpectedConditions.invisibilityOf(popup));
            } catch (NoSuchElementException nse){
                // popup not shown
                nse.printStackTrace();
            }
        }
    }

    public String getEmployeeId(){
        wait.until(ExpectedConditions.attributeToBeNotEmpty(textbox_employee_id, "value"));
        return textbox_employee_id.getAttribute("value").trim();
    }
}
