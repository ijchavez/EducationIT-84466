package com.automation.selenium.clase2.Waits;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitsTests {
	@Test
	public void withNoImplicitWaitTests() {
		WebDriver driver = new ChromeDriver();
		driver.get("https://seleniumjavalocators.vercel.app/pages/waits.html");
		
		WebElement visibilityWaitButton = driver.findElement(By.id("startVisibilityWait"));
		visibilityWaitButton.click();
		
		WebElement delayedMessage = driver.findElement(By.id("delayedElement"));
		System.out.println(">>> " + delayedMessage.getText());
		
		driver.quit();
	}
	
	@Test
	public void withImplicitWaitTests() {
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://seleniumjavalocators.vercel.app/pages/waits.html");
		
		WebElement visibilityWaitButton = driver.findElement(By.id("startVisibilityWait"));
		visibilityWaitButton.click();
		
		WebElement delayedMessage = driver.findElement(By.xpath("//*[@id='delayedElement' and not(contains(@class,'hidden'))]"));
		System.out.println(">>> " + delayedMessage.getText());
		
		driver.quit();
	}
	
	@Test
	public void explicitWaitTests() {
		WebDriver driver = new ChromeDriver();
		driver.get("https://seleniumjavalocators.vercel.app/pages/waits.html");
		
		WebElement visibilityWaitButton = driver.findElement(By.id("startVisibilityWait"));
		visibilityWaitButton.click();
		
		By delayedElementBy = By.id("delayedElement");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		WebElement delayedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(delayedElementBy));
		System.out.println(">>> " + delayedMessage.getText());
		
		driver.quit();
	}
	
}
