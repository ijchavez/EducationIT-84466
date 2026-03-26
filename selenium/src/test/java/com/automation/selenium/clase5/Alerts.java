package com.automation.selenium.clase5;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.*;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class Alerts {
	WebDriver driver;
	String url = "https://seleniumjavalocators.vercel.app/pages/alerts.html";
	Faker faker;
	
	@BeforeMethod
	public void setUp() {
		faker = new Faker();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get(url);
		
	}
	
	@Test
	public void alertTest() {
		WebElement alertButton = driver.findElement(By.id("alertButton"));
		alertButton.click();
		
		Alert alert = driver.switchTo().alert();
		
		Assert.assertEquals(alert.getText(), "You clicked a button");
		alert.accept();
	}
	
	@Test
	public void alertConfirmTest() {
		WebElement confirmButton = driver.findElement(By.id("confirmButton"));
		confirmButton.click();
		
		Alert alert = driver.switchTo().alert();
		
		alert.accept();
		
		WebElement confirmationMessage = driver.findElement(By.id("resultContainerConfirmButton"));
		Assert.assertEquals(confirmationMessage.getText(), "You selected OK");
		
	}
	
	@Test
	public void alertDismissTest() {
		WebElement confirmButton = driver.findElement(By.id("confirmButton"));
		confirmButton.click();
		
		Alert alert = driver.switchTo().alert();
		
		alert.dismiss();
		
		WebElement confirmationMessage = driver.findElement(By.id("resultContainerConfirmButton"));
		Assert.assertEquals(confirmationMessage.getText(), "You selected Cancel");
		
	}
	
	@Test
	public void alertPromptTest() {
		String name = faker.name().fullName();
		
		WebElement alertButton = driver.findElement(By.id("promptButton"));
		alertButton.click();
		
		Alert alert = driver.switchTo().alert();
		
		alert.sendKeys(name);
		alert.accept();
		
		WebElement result = driver.findElement(By.id("resultContainerPromptButton"));
		System.out.println(result.getText());
		
		Assert.assertEquals(result.getText(), "You entered '" + name + "'");
		
	}
	
	@Test
	public void alertWithWaitTest() {
		WebElement timerAlertButton = driver.findElement(By.id("timerAlertButton"));
		timerAlertButton.click();
		
		Wait<WebDriver> waitAlert = new WebDriverWait(driver, Duration.ofSeconds(6));
		waitAlert.until(ExpectedConditions.alertIsPresent());
		
		Alert alert = driver.switchTo().alert();
		
		System.out.println(alert.getText());
		
		Assert.assertEquals(alert.getText(), "This alert appeared after 5 seconds");
		alert.accept();
	}
	
	
	@AfterMethod
	public void finTest() throws IOException {
		driver.close();
		
	}
}
