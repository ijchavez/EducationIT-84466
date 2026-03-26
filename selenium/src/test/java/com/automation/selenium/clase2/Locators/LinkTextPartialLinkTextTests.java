package com.automation.selenium.clase2.Locators;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LinkTextPartialLinkTextTests {
	public WebDriver driver;
	@Test
	public void linkTextPartialLinkTextTest() {
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		//voy a la pagina principal
		driver.get("https://seleniumjavalocators.vercel.app/pages/linksutiles.html");
		
		WebElement jdkInstallationElement = driver.findElement(By.linkText("Instalacion JDK"));
		System.out.println(jdkInstallationElement.getText());
		
		WebElement pageObjectModelElement = driver.findElement(By.partialLinkText("Object"));
		System.out.println(pageObjectModelElement.getText());
		
		
		driver.quit();
		
	}
}
