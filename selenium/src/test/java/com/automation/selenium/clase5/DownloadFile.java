package com.automation.selenium.clase5;

import java.io.IOException;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automation.selenium.clase5.utilities.Utilities;

public class DownloadFile {
	WebDriver driver;
	String url = "https://demo.guru99.com/test/yahoo.html";
	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
				
		driver.get(url);
		
	}
	
	@Test
	public void uploadTest() {
		WebElement messengerDownload = driver.findElement(By.id("messenger-download"));
		String messengerDownloadHref = messengerDownload.getAttribute("href");
		
		System.out.println(messengerDownloadHref);
		
		String[] wget_command = {
				Utilities.WGET_EXECUTABLE,
				"-P",
				Utilities.DOWNLOAD_LOCATION,
				"--no-check-certificate",
				messengerDownloadHref
		};
		
		Utilities.downloadFile(wget_command);
		
	}
	
	
	@AfterMethod
	public void finTest() throws IOException, InvalidFormatException {
		driver.close();
		
	}
}
