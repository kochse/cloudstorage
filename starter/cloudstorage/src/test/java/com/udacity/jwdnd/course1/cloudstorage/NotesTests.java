package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesTests {
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
    public void testDeleteNote() {
        HomePage home = new HomePage(driver);
        String title = "Test Note";
        home.addNote(title, "Test");
        WebElement note = home.findNoteByTitle(title);
        Assertions.assertEquals(title, note.getText());
        home.deleteNote(title);
        Assertions.assertEquals(home.checkIfNoteExists(title), false);
        home.deleteNoteIfExist();
    }

    @Test
    public void testEditNote() {
        HomePage home = new HomePage(driver);
        String title = "Test Note";
        home.addNote(title, "Test");
        WebElement note = home.findNoteByTitle(title);
        Assertions.assertEquals(title, note.getText());

        String newTitle = "TestNote2";
        home.updateFirstNote(title, newTitle);

        Assertions.assertEquals(home.checkIfNoteExists(title), false);

        note = home.findNoteByTitle(newTitle);
        Assertions.assertEquals(newTitle, note.getText());

        home.deleteNoteIfExist();
    }

    @Test
    public void testCreateNote() {
        HomePage home = new HomePage(driver);
        String title = "Test Note";
        home.addNote(title, "Test");
        WebElement note = home.findNoteByTitle(title);
        Assertions.assertEquals(title, note.getText());
        home.deleteNoteIfExist();
    }




}
