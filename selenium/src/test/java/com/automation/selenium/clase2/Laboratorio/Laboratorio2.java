package com.automation.selenium.clase2.Laboratorio;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Laboratorio2 {
		public WebDriver driver;
		@Test
		public void lab2_E1() {
			final String LOGIN_URL = "https://automationexercise.com/login";
			driver = new ChromeDriver();		
			//voy a la pagina principal
			driver.get(LOGIN_URL);
			driver.manage().window().maximize();
			
			WebElement signupInput = driver.findElement(By.xpath("//input[@data-qa='signup-name']"));
			signupInput.sendKeys("gchavez");
			
			WebElement emailAddressInput = driver.findElement(By.xpath("//input[@data-qa='signup-email']"));
			emailAddressInput.sendKeys("gerardo.chavez133@email.com");
			
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
			System.out.println(userCreationSuccessElement.getText());
			
			driver.quit();
			
		}
}
