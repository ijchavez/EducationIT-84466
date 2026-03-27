package com.automation.selenium.clase6;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automation.selenium.clase6.utilities.Utilities;

public class CambiosSelenium4GeoLocation {
	WebDriver driver;
	String url = "https://webbrowsertools.com/geolocation/";
	//mvn test -Ptestng-direct "-Dtest=com.automation.selenium.clase6.CambiosSelenium4GeoLocation#geoLocationTest"
	
	double latitude = 59.889458;
	double longitude = 12.84584;
	
	@BeforeMethod
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");		

		driver = new ChromeDriver(options);
		
		Utilities.setCoordinates(driver, latitude, longitude, url); 
		driver.get(url);
		
	}
	@Test
	public void geoLocationTest() throws InterruptedException {
		WebElement latitudeElement = driver.findElement(By.id("latitude"));
		WebElement longitudeElement = driver.findElement(By.id("longitude"));
		WebElement countryCodeElement = driver.findElement(By.id("country-code"));		
		WebElement countryNameElement = driver.findElement(By.id("country-name"));	
		
		System.out.println(latitudeElement.getText());
		System.out.println(longitudeElement.getText());
		System.out.println(countryCodeElement.getText());
		System.out.println(countryNameElement.getText());
		
		Assert.assertEquals(latitudeElement.getText(), String.valueOf(latitude).concat(" Degrees"));
		Assert.assertEquals(longitudeElement.getText(), String.valueOf(longitude).concat(" Degrees"));
		Assert.assertEquals(countryCodeElement.getText(), "SE");
		Assert.assertEquals(countryNameElement.getText(), "Sweden");
		
	}
	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
		
	}
}
