package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    @FindBy(name = "firstName")
    private WebElement firstName;

    @FindBy(name = "lastName")
    private WebElement lastName;

    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(name = "alert")
    private WebElement alertMessage;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void fillAndSubmitForm() {
        firstName.sendKeys("Test");
        lastName.sendKeys("Test");
        username.sendKeys("Test");
        password.sendKeys("Test");

        firstName.submit();
    }

    public String getAlertMessage() {
        return this.alertMessage.getText();
    }

}
