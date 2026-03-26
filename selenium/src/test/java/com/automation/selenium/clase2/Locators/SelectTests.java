package com.automation.selenium.clase2.Locators;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SelectTests {

	public WebDriver driver;
	@Test
	public void xpathLocatorsTest() {
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.navigate().to("https://seleniumjavalocators.vercel.app/pages/desitioning-table.html");
		
		/*
		 * <select>
		 * 	  <option value = 'regular' > Cliente regular </option>
		 * 	  <option value = 'pre' > Cliente premium </option> -> byVisibleText = Premium, byValue = 'pre', byIndex = 1
		 * </select>
		 * */
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Select membershipSelect = new Select(driver.findElement(By.name("membership")));
		membershipSelect.selectByVisibleText("Cliente premium");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		membershipSelect.selectByValue("regular");		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		membershipSelect.selectByIndex(1);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		membershipSelect.selectByContainsVisibleText("regu");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver.quit();
		
	}
}
