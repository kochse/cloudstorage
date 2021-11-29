package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialsTest {
    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();

        this.driver.get("http://localhost:" + this.port + "/signup");
        SignUpPage signup = new SignUpPage(this.driver);
        signup.fillAndSubmitForm("TestN", "TestN");
        this.driver.get("http://localhost:" + this.port + "/login");
        LoginPage login = new LoginPage(this.driver);
        login.loginAndSubmit("TestN", "TestN");

        this.driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", this.driver.getTitle());
    }

    // Credential Creation
    @Test
    public void testCredentials() {


        // View Credential
        HomePage home = new HomePage(driver);
        home.checkIfCredentialExists("Test", "Test", "Test");


        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void testCreateCredential() {
        // Create Credential
        HomePage home = new HomePage(driver);
        home.addCredential("Test", "Test", "Test");

        home = new HomePage(driver);
        String title = "Test Note";
        home.addNote(title, "Test");
        WebElement note = home.findNoteByTitle(title);
        Assertions.assertEquals(title, note.getText());
        home.deleteNoteIfExist();
    }
}
