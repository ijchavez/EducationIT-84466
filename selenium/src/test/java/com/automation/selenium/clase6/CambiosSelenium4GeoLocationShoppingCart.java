package com.automation.selenium.clase6;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automation.selenium.clase6.utilities.Utilities;

import java.time.Duration;
import java.util.*;

public class CambiosSelenium4GeoLocationShoppingCart {
	WebDriver driver;
	String url = "https://oldnavy.gap.com/stores";
	
	@BeforeMethod
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");		

		driver = new ChromeDriver(options);
		
		Utilities.setCoordinates(driver, 31.263891, -98.545612, url);
		driver.get(url);
		
	}
	@Test
	public void geoLocationTest() throws InterruptedException {
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		List<WebElement> addressList = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@class='address']"))));

		Thread.sleep(3000);
		Assert.assertTrue(addressList.size() > 0);
		
		for(WebElement address : addressList) {
			System.out.println(address.getText());
			System.out.println("================");
			
			Assert.assertTrue(address.getText().contains(", TX"));
			
		}
		
	}
	@AfterMethod
	public void tearDown() {
		driver.close();
		
	}
}
