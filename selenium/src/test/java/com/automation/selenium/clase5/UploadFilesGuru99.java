package com.automation.selenium.clase5;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UploadFilesGuru99 {
	WebDriver driver;
	String url = "https://demo.guru99.com/test/upload/";
	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get(url);
		
	}
	
	@Test
	public void uploadTest() {
		WebElement uploadInput = driver.findElement(By.id("uploadfile_0"));
		uploadInput.sendKeys("C:\\Viejo D\\EducationIT-84466\\selenium\\src\\test\\resources\\inicioSesionInvalido.xlsx");
		
	
		WebElement submitButton = driver.findElement(By.id("submitbutton"));
		submitButton.click();
	
		Wait<WebDriver> uploadMessageWait = new WebDriverWait(driver, Duration.ofSeconds(6));
		
		WebElement uploadMessage = driver.findElement(By.id("res"));
		//String expectedMessage = "1 file\r\n"
		String expectedMessage = "1 file\n"
				+ "has been successfully uploaded.";
		
		//textToBePresentInElement es una funcion booleana(true o false) que nos pide un WebElement y el texto por el que tiene que esperar
		//durante el tiempo que le pidamos va a esperar el texto, si aparece se vuelve true, si no aparece se vuelve false.
		boolean uploadMessageWithWait = uploadMessageWait.until(ExpectedConditions.textToBePresentInElement(uploadMessage, expectedMessage));
		
		System.out.println(uploadMessageWithWait);
		Assert.assertTrue(uploadMessageWithWait);
		
	}
	
	
	@AfterMethod
	public void finTest(ITestContext context) throws IOException, InvalidFormatException {
		driver.close();
		
	}
	
}
