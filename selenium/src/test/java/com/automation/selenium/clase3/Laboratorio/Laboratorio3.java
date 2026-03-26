package com.automation.selenium.clase3.Laboratorio;

import java.io.IOException;
import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.automation.selenium.evidence.EvidenceService;

public class Laboratorio3 {
		public WebDriver driver;
		final String LOGIN_URL = "https://automationexercise.com/login";
	    private EvidenceService evidenceService;
	    
		@BeforeSuite
		public void setUp() {
			driver = new ChromeDriver();
		}
		
		@BeforeTest
		public void irUrl() {
			driver.get(LOGIN_URL);
			//voy a la pagina principal
			
			
		}
		
		@BeforeClass
		public void maxVentana() {
	        evidenceService = new EvidenceService();
			driver.manage().window().maximize();
		}
		
		@Test
		public void lab2_E1() {			
			WebElement signupInput = driver.findElement(By.xpath("//input[@data-qa='signup-name']"));
			signupInput.sendKeys("gchavez");
			
			WebElement emailAddressInput = driver.findElement(By.xpath("//input[@data-qa='signup-email']"));
			emailAddressInput.sendKeys("gerardo.kk2231eoaasd@email.com");
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			
			WebElement signupButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-qa='signup-button']")));
			signupButton.click();
			
			WebElement maleGenderRadiobutton = driver.findElement(By.id("id_gender1"));
			maleGenderRadiobutton.click();
			
			WebElement passwordInput = driver.findElement(By.id("password"));
			passwordInput.sendKeys("password");
			
			Select daySelect = new Select(driver.findElement(By.id("days")));
			daySelect.selectByVisibleText("10");			
			
			Select monthSelect = new Select(driver.findElement(By.id("months")));
			monthSelect.selectByValue("8");	
			
			Select yearSelect = new Select(driver.findElement(By.id("years")));
			yearSelect.selectByVisibleText("1975");	
			
			WebElement firstNameInput = driver.findElement(By.id("first_name"));
			firstNameInput.sendKeys("Gerardo");
			
			WebElement lastNameInput = driver.findElement(By.id("last_name"));
			lastNameInput.sendKeys("Chavez");
			
			WebElement address1Input = driver.findElement(By.name("address1"));
			address1Input.sendKeys("4309 Sunset Dr");
						
			Select countrySelect = new Select(driver.findElement(By.id("country")));
			countrySelect.selectByVisibleText("United States");	
			
			WebElement stateInput = driver.findElement(By.id("state"));
			stateInput.sendKeys("Florida");
			
			WebElement cityInput = driver.findElement(By.id("city"));
			cityInput.sendKeys("Miami");
			
			WebElement zipCodeInput = driver.findElement(By.id("zipcode"));
			zipCodeInput.sendKeys("99103");
			
			WebElement mobileNumberInput = driver.findElement(By.id("mobile_number"));
			mobileNumberInput.sendKeys("994356123");
			
			WebElement createAccountButton = driver.findElement(By.cssSelector("button[data-qa='create-account']"));
			createAccountButton.click();
			
			
			WebElement userCreationSuccessElement = driver.findElement(By.xpath("//div[@class='col-sm-9 col-sm-offset-1']"));
			
			Assert.assertTrue(true);
			
			Assert.assertEquals(userCreationSuccessElement.getText(), "ACCOUNT CREATED!\r\n"
					+ "Congratulations! Your new account has been successfully created!\r\n"
					+ "You can now take advantage of member privileges to enhance your online shopping experience with us.\r\n"
					+ "Continue",
					"El mensaje no es el correcto");
			
			System.out.println(userCreationSuccessElement.getText());
			
		}
	    @AfterMethod(alwaysRun = true)
	    public void tearDown(ITestResult testResult) throws IOException {
	        try {
	            if (driver != null) {
	                evidenceService.captureTestEvidence(driver, testResult.getMethod().getMethodName());
	            }
	        } finally {
	            if (driver != null) {
	                driver.quit();
	            }
	        }
	    }
}
