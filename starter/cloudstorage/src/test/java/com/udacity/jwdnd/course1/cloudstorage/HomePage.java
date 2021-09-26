package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(xpath = "//form[@action='/logout']")
    private WebElement logoutForm;

    @FindBy(xpath = "//button[text()='+ Add a New Note']")
    private WebElement addNoteButton;

    @FindBy(xpath = "//button[text()='+ Add a New Credential']")
    private WebElement addCredentialButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        this.logoutForm.submit();
    }

    public void addNote() {
        this.addNoteButton.submit();
    }

    public void checkIfNoteExists(String title) {

    }

    public void updateNote(String title) {

    }

    public void deleteNote(String title) {

    }

    public void addCredential(String url, String username, String password) {
        this.addCredentialButton.submit();
    }

    public void checkIfCredentialExistsAndPasswordIsEncrypted(String url, String username, String password) {

    }

    public void deleteCredential(String url) {

    }


}
