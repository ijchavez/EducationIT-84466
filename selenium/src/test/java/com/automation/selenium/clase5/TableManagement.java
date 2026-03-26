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

public class TableManagement {
	WebDriver driver;
	String url = "https://seleniumjavalocators.vercel.app/pages/paginated-table.html";
	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		
	}

	@Test
	public void tableManagementTest() {
		String id = "2";
		
		List<WebElement> cellList = driver.findElements(By.xpath("//table[@id='notesTable']//tr//td"));
		WebElement row = null;
				
		for (WebElement element : cellList) {
			if(element.getText().equalsIgnoreCase(id)) {
				row = driver.findElement(By.xpath("//table[@id='notesTable']//tr[@data-row-id='" + id + "']"));
				//table[@id="notesTable"]//tr[@data-row-id='2']
				System.out.println(element.getText());
				System.out.println(row.getText());
			}
		}
		Assert.assertTrue(row.getText().contains("Checklist diario"));
		Assert.assertTrue(row.getText().contains("tareas"));
		
	}
	
	@AfterMethod(alwaysRun = true)
	public void finTest() throws IOException {
		if (driver != null) {
			driver.quit();
		}
	}	
		
}
