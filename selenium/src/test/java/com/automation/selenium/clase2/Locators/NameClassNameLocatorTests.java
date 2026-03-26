package com.automation.selenium.clase2.Locators;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class NameClassNameLocatorTests {
	public WebDriver driver;
	@Test
	public void nameClassNameTest() {
		final String LOGIN_URL = "https://seleniumjavalocators.vercel.app/pages/registro.html";
		driver = new ChromeDriver();
			
		//voy a la pagina principal
		driver.get(LOGIN_URL);
		
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
		
		
		driver.quit();
		
	}
}
