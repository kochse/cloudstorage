package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

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
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void checkPublicAccessRights() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUnauthorizedAccess() {
		driver.get("http://localhost:" + this.port + "/");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	// Sign Up, Login, Homepage
	@Test
	public void testSignUpAndLogin() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		SignUpPage signup = new SignUpPage(driver);
		signup.fillAndSubmitForm("Test1", "Test1");
		Assertions.assertEquals("You successfully signed up! Please continue to the login page.", signup.getAlertMessage());

		signup.backToLogin();

		LoginPage login = new LoginPage(driver);
		login.loginAndSubmit("Test1", "Test1");

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage home = new HomePage(driver);
		home.logout();

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	// Credential Creation
	@Test
	public void testCredentials() {
		driver.get("http://localhost:" + this.port + "/signup");
		SignUpPage signup = new SignUpPage(driver);
		signup.fillAndSubmitForm("TestC", "TestC");
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage login = new LoginPage(driver);
		login.loginAndSubmit("TestC", "TestC");

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		// Create Credential
		HomePage home = new HomePage(driver);
		home.addCredential("Test", "Test", "Test");

		// View Credential
		home.checkIfCredentialExists("Test", "Test", "Test");


		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

}
