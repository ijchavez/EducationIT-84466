package com.automation.selenium.clase5;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class UploadFiles {
	WebDriver driver;
	String url = "https://seleniumjavalocators.vercel.app/pages/upload.html";
	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get(url);
		
	}
	
	@Test
	public void uploadTest() {
		WebElement uploadInput = driver.findElement(By.id("fileInput"));
		uploadInput.sendKeys("C:\\Viejo D\\EducationIT-84466\\selenium\\src\\test\\resources\\inicioSesionInvalido.xlsx");
		
		WebElement uploadMessage = driver.findElement(By.id("message"));
		System.out.println(uploadMessage.getText());
		
		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		
		System.out.println(uploadMessage.getText());
		
	}
	
	
	@AfterMethod
	public void finTest(ITestContext context) throws IOException, InvalidFormatException {
		driver.close();
		
	}
}
