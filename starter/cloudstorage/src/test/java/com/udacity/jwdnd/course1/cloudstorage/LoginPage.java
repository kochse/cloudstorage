package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "password")
    private WebElement password;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void fillAndSubmitForm() {
        username.sendKeys("Test");
        password.sendKeys("Test");

        username.submit();
    }
}
