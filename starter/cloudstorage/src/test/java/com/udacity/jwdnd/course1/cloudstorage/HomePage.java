package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriver driver;

    @FindBy(xpath = "//form[@action='/logout']")
    private WebElement logoutForm;

    @FindBy(xpath = "//a[@id='nav-notes-tab']")
    private WebElement notesTab;

    @FindBy(xpath = "//a[@id='nav-credentials-tab']")
    private WebElement credentialsTab;

    @FindBy(xpath = "//button[@id='add-note']")
    private WebElement addNoteButton;

    @FindBy(xpath = "//button[@id='add-credential']")
    private WebElement addCredentialButton;

    @FindBy(name = "noteTitle")
    private WebElement noteTitle;

    @FindBy(name = "noteDescription")
    private WebElement noteDescription;

    @FindBy(xpath = "//form[@action='/saveNote']")
    private WebElement noteForm;

    @FindBy(name = "url")
    private WebElement urlInput;

    @FindBy(name = "userName")
    private WebElement userNameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//form[@action='/saveCredential']")
    private WebElement credentialForm;


    public HomePage(WebDriver driver) {

        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void logout() {
        this.logoutForm.submit();
    }

    public void addNote(String title, String description) {
        this.notesTab.click();
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(this.addNoteButton));
        this.addNoteButton.click();
        wait.until(ExpectedConditions.visibilityOf(this.noteTitle));
        this.noteTitle.sendKeys(title);
        this.noteDescription.sendKeys(description);
        this.noteForm.submit();
    }

    public void checkIfNoteExists(String title) {

    }

    public void updateNote(String title) {

    }

    public void deleteNote(String title) {

    }

    public void addCredential(String url, String username, String password) {
        this.credentialsTab.click();
        this.urlInput.sendKeys(url);
        this.userNameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);
        this.addCredentialButton.click();
    }

    public void checkIfCredentialExistsAndPasswordIsEncrypted(String url, String username, String password) {

    }

    public void deleteCredential(String url) {

    }


}
