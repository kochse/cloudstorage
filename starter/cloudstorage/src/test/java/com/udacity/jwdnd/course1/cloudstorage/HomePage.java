package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

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

    public WebElement findNoteByTitle(String title) {
        this.notesTab.click();
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(this.addNoteButton));
        WebElement note = driver.findElement(By.xpath("//*[text()='" + title + "']"));
        return note;
    }

    public WebElement findCredentialByUrl(String url) {
        this.credentialsTab.click();
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(this.addCredentialButton));
        WebElement note = driver.findElement(By.xpath("//*[text()='" + url + "']"));
        return note;
    }

    public WebElement findCredentialPassword() {
        this.credentialsTab.click();
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(this.addCredentialButton));
        WebElement password = driver.findElement(By.xpath("//td[@class='password']"));
        return password;
    }

    public boolean checkIfNoteExists(String title) {
        this.notesTab.click();
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(this.addNoteButton));
        return !driver.findElements(By.xpath("//*[text()='" + title + "']")).isEmpty();
    }

    public boolean checkIfCredentialExists(String url) {
        this.credentialsTab.click();
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(this.addCredentialButton));
        return !driver.findElements(By.xpath("//*[text()='" + url + "']")).isEmpty();
    }

    public boolean checkIfNoteDeleteButtonExists() {
        this.notesTab.click();
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(this.addNoteButton));
        return !driver.findElements(By.xpath("//a[text()='Delete']")).isEmpty();
    }

    public boolean checkIfCredentialDeleteButtonExists() {
        this.credentialsTab.click();
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(this.addCredentialButton));
        return !driver.findElements(By.xpath("//a[text()='Delete']")).isEmpty();
    }

    public void updateFirstNote(String title, String newTitle) {
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='" + title + "']"))));
        WebElement editButton = driver.findElement(By.xpath("//button[text()='Edit']"));
        editButton.click();

        wait.until(ExpectedConditions.visibilityOf(this.noteTitle));
        this.noteTitle.clear();
        this.noteTitle.sendKeys(newTitle);
        this.noteForm.submit();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("noteTitle")));
        wait.until(ExpectedConditions.visibilityOf(this.addNoteButton));
    }

    public void updateFirstCredential(String url, String newUrl) {
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='" + url + "']"))));
        WebElement editButton = driver.findElement(By.xpath("//button[text()='Edit']"));
        editButton.click();

        wait.until(ExpectedConditions.visibilityOf(this.urlInput));
        this.urlInput.clear();
        this.urlInput.sendKeys(newUrl);
        this.credentialForm.submit();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("urlInput")));
        wait.until(ExpectedConditions.visibilityOf(this.addCredentialButton));
    }

    public void deleteNoteIfExist() {
        if (this.checkIfNoteDeleteButtonExists()) {
            WebElement deleteButton = driver.findElement(By.xpath("//a[text()='Delete']"));
            deleteButton.click();
            WebDriverWait wait = new WebDriverWait(this.driver,30);
            wait.until(ExpectedConditions.visibilityOf(this.addNoteButton));
        }
    }

    public void deleteCredentialIfExist() {
        WebDriverWait wait2 = new WebDriverWait(this.driver,30);
        wait2.until(ExpectedConditions.visibilityOf(this.addCredentialButton));

        if (this.checkIfCredentialDeleteButtonExists()) {
            WebElement deleteButton = driver.findElement(By.xpath("//a[text()='Delete']"));
            deleteButton.click();
            WebDriverWait wait = new WebDriverWait(this.driver,30);
            wait.until(ExpectedConditions.visibilityOf(this.addCredentialButton));
        }
    }

    public void deleteNote(String title) {
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='" + title + "']"))));
        this.deleteNoteIfExist();
    }

    public void addCredential(String url, String username, String password) {
        this.credentialsTab.click();
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(this.addCredentialButton));
        this.addCredentialButton.click();
        wait.until(ExpectedConditions.visibilityOf(this.urlInput));
        this.urlInput.sendKeys(url);
        this.userNameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);
        this.credentialForm.submit();
    }



    public void deleteCredential(String url) {
        WebDriverWait wait = new WebDriverWait(this.driver,30);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='" + url + "']"))));
        this.deleteCredentialIfExist();
    }


}
