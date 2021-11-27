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

    @FindBy(className = "alert-dark")
    private WebElement alertMessage;

    @FindBy(className = "login")
    private WebElement loginButton;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void fillAndSubmitForm(String user, String pass) {
        firstName.sendKeys("Test");
        lastName.sendKeys("Test");
        username.sendKeys(user);
        password.sendKeys(pass);

        firstName.submit();
    }

    public String getAlertMessage() {
        return this.alertMessage.getText();
    }

    public void backToLogin() {
        this.loginButton.click();
    }

}
