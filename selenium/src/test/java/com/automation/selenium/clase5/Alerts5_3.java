package com.automation.selenium.clase5;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class Alerts5_3 {
	WebDriver driver;
	String url = "https://demoqa.com/alerts";
	Faker faker;
	
	@BeforeMethod
	public void setUp() {
		faker = new Faker();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get(url);
		
	}
	
	@Test
	public void alertPromptTest() {
		String name = faker.name().fullName();
		
		WebElement alertButton = driver.findElement(By.id("promtButton"));
		alertButton.click();
		
		Alert alert = driver.switchTo().alert();
		
		alert.sendKeys(name);
		alert.accept();
		
		WebElement result = driver.findElement(By.id("promptResult"));
		System.out.println(result.getText());
		//You entered 'Miss Ed Gutkowski'
		//You entered Miss Ed Gutkowski
		Assert.assertEquals(result.getText(), "You entered ".concat(name));
		
	}
	
	
	@AfterMethod
	public void finTest() throws IOException {
		driver.close();
		
	}
}
