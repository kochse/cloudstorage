package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "password")
    private WebElement password;

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void loginAndSubmit(String user, String pass) {
        username.sendKeys(user);
        password.sendKeys(pass);
        username.submit();
    }

    public void waitForLoginPage() {
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(this.username));
    }
}
