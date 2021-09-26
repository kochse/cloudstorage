package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

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

	// Sign Up, Login, Homepage
	@Test
	public void testSignUp() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		SignUpPage signup = new SignUpPage(driver);
		signup.fillAndSubmitForm();
		Assertions.assertEquals("You successfully signed up! Please continue to the login page.", signup.getAlertMessage());
	}

	// Note Creation
	@Test
	public void testNotes() {
		List<WebElement> results = driver.findElements(By.cssSelector("div.g a"));
		for (WebElement element : results) {
			String link = element.getAttribute("href");
		}
		//driver.get("http://localhost:" + this.port + "/login");
		//Assertions.assertEquals("Login", driver.getTitle());

		// driver.findElement(By.name("submit")).click();
		// driver.findElement(By.className("submit")).click();
		// driver.findElement(By.tagName("submit")).click();
		// driver.findElement(By.cssSelector("submit")).click();
		// driver.findElement(By.xpath("//input[@value='Visit me']")).click();
	}

	// Credential Creation
	@Test
	public void testCredentials() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

}
