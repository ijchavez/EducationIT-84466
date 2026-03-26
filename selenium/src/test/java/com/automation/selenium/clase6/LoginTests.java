package com.automation.selenium.clase6;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automation.selenium.clase6.utilities.Utilities;

public class LoginTests {
	WebDriver driver;
	String url = "https://seleniumjavalocators.vercel.app/pages/login.html";
	
	@BeforeMethod
	public void setUp() {
		//https://peter.sh/experiments/chromium-command-line-switches/
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("start-maximized");
		//chromeOptions.addArguments("incognito");
		//chromeOptions.addArguments("headless");
		
		driver = new ChromeDriver(chromeOptions);
		driver.get(url);
		//driver.manage().window().maximize();
	}
	@Test
	public void succesfulLoginTest() {			
        WebElement userNameInput = driver.findElement(By.id("username_id"));
        userNameInput.sendKeys("usuario.demo");

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("Qa1234!");

        WebElement loginButton = driver.findElement(By.id("iniciarSesionBtn"));
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement loginMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("mensajeInicioSesion"))
        );

        WebElement loginMessageEyebrow = loginMessage.findElement(By.id("loginMessageEyebrow"));
        WebElement loginMessageTitle = loginMessage.findElement(By.id("loginMessageTitle"));
        WebElement loginMessageBody = loginMessage.findElement(By.id("loginMessageBody"));

        Assert.assertEquals(loginMessageEyebrow.getText(), "LOGIN CORRECTO");
        Assert.assertEquals(loginMessageTitle.getText(), "Inicio de sesion exitoso");
        Assert.assertEquals(
                loginMessageBody.getText(),
                "Bienvenido al laboratorio. El acceso fue concedido correctamente. Seras redirigido a una pagina mock."
        );
		
	}
	
	@AfterMethod
	public void finTest(ITestContext context) throws IOException, InvalidFormatException {
		LocalDateTime datetime = LocalDateTime.now();
		String dateTestName = datetime.getNano() + "_" + context.getName();
		Path outputDirectory = Path.of(System.getProperty("user.dir")).toAbsolutePath();
		String imgPath = outputDirectory.resolve(dateTestName + "_image.png").toString();
		String docPath = outputDirectory.resolve("Documento_de_evidencias" + dateTestName + ".docx").toString();
		
		Utilities.takeScreenshot(driver, imgPath);
		Utilities.createDocxFile(driver, docPath, imgPath);
		
		driver.close();
		
	}
}
