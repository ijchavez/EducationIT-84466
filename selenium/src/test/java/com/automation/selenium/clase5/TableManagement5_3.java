package com.automation.selenium.clase5;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TableManagement5_3 {
	WebDriver driver;
	String url = "https://demo.guru99.com/test/table.html";
	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		
	}
	
	@Test
	public void tableManagementTest() {
		String id = "8";
		
		List<WebElement> cellList = driver.findElements(By.xpath("//tbody//tr//td"));
		int contador = 0;
		
		
		for (WebElement element : cellList) {
			if(element.getText().equalsIgnoreCase(id)) {
				contador ++;

			}
		}
		Assert.assertTrue(contador > 0);
		
	}
	
	@AfterMethod(alwaysRun = true)
	public void finTest() throws IOException {
		if (driver != null) {
			driver.quit();
		}
	}	
		
}
