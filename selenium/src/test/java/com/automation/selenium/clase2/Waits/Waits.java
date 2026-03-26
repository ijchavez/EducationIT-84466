package com.automation.selenium.clase2.Waits;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waits {
	@Test
	public void explicitWaitTests() {
		WebDriver driver = new ChromeDriver();
		driver.get("https://automationexercise.com/products");
		
		//WebElement searchInput = driver.findElement(By.id("search_product"));
		//searchInput.sendKeys("Sleeves Printed Top - White");
		
		By searchProduct = By.id("search_product");
		
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement searchInput = mywait.until(ExpectedConditions.elementToBeClickable(searchProduct));
		searchInput.sendKeys("Sleeves Printed Top - White");
		
		WebElement searchButton = driver.findElement(By.id("submit_search"));
		searchButton.click();
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver.quit();
	}
	
	@Test
	public void impliciWaitTests() {
		WebDriver driver = new ChromeDriver();
		//driver.manage().timeouts().implicitlyWait(DurationClass);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		driver.get("https://automationexercise.com/products");
		
		WebElement searchInput = driver.findElement(By.id("search_product"));
		searchInput.sendKeys("Sleeves Printed Top - White");
		
		WebElement searchButton = driver.findElement(By.id("submit_search"));
		searchButton.click();
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver.quit();
	}
	
}
