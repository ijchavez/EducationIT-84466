package com.automation.selenium.clase3.TestNG;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Assertions {
	public WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		final String LOGIN_URL = "https://seleniumjavalocators.vercel.app/pages/registro.html";
		driver = new ChromeDriver();
			
		//voy a la pagina principal
		driver.get(LOGIN_URL);
	}
	
	@Test(description="Registration Test")
	public void registrationTest() {
		
		WebElement nameElement = driver.findElement(By.name("name"));
		nameElement.sendKeys("Gonzalo");
		
		WebElement emailElement = driver.findElement(By.name("email"));
		emailElement.sendKeys("gfuentes@mail.com");
		
		WebElement usernameElement = driver.findElement(By.name("username"));
		usernameElement.sendKeys("gonzalito");
		
		WebElement passwordElement = driver.findElement(By.name("password"));
		passwordElement.sendKeys("banco123");
		
		WebElement confirmPasswordElement = driver.findElement(By.name("confirmPassword"));
		confirmPasswordElement.sendKeys("banco123");
		
		WebElement registrationButton = driver.findElement(By.className("primary-btn"));
		registrationButton.click();
		
		WebElement registrationMessage = driver.findElement(By.id("mensajeInicioSesion"));
		System.out.println(registrationMessage.getText());
		
		Assert.assertEquals(
				registrationMessage.getText(), 
				"Intentaste registrarte con nombre 'Gonzalo', usuario 'gonzalito' y correo 'gfuentes@mail.com'.",
				"El resultado no fue el esperado");
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
