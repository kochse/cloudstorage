package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
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

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }


    @Test
    public void testDeleteCredential() {
        HomePage home = new HomePage(driver);
        String url = "TestDELETE";
        home.addCredential(url, "TestUSERNAME", "TestPASSWORD");
        WebElement urlElement = home.findCredentialByUrl(url);
        Assertions.assertEquals(url, urlElement.getText());

        home.deleteCredential(url);
        Assertions.assertEquals(home.checkIfCredentialExists(url), false);
        home.deleteCredentialIfExist();
    }

    @Test
    public void testEditCredential() {
        HomePage home = new HomePage(driver);
        String url = "TestEDIT";
        home.addCredential(url, "TestUSERNAME2", "TestPASS2");

        WebElement credential = home.findCredentialByUrl(url);
        Assertions.assertEquals(url, credential.getText());

        String newUrl = "TestCredential2";
        home.updateFirstCredential(url, newUrl);

        Assertions.assertEquals(home.checkIfCredentialExists(url), false);

        credential = home.findCredentialByUrl(newUrl);
        Assertions.assertEquals(newUrl, credential.getText());

        home.deleteCredentialIfExist();
    }

    @Test
    public void testCreateCredential() {
        HomePage home = new HomePage(driver);
        String url = "Test";
        home.addCredential(url, "Test", "Test");

        WebElement credential = home.findCredentialByUrl(url);
        Assertions.assertEquals(url, credential.getText());

        WebElement credentialPassword = home.findCredentialPassword();
        Assertions.assertNotEquals("Test", credentialPassword.getText());

        home.deleteCredentialIfExist();
    }
}
