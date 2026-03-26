package com.automation.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Sample {
	public WebDriver driver;
	public static final String LOGIN_URL = "https://seleniumjavalocators.vercel.app/pages/login.html";
	@Test
	public void sampleTest() {
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		//voy a la pagina principal
		driver.get(LOGIN_URL);
		
		System.out.println("Titulo de la pagina: " + driver.getTitle());
		System.out.println("URL de la pagina: " + driver.getCurrentUrl());	
		
		driver.navigate().to(LOGIN_URL);
		
		System.out.println("=====================");
		
		System.out.println("Titulo de la pagina: " + driver.getTitle());
		System.out.println("URL de la pagina: " + driver.getCurrentUrl());	
		
		driver.navigate().back();
		
		System.out.println("=====================");
		
		System.out.println("Titulo de la pagina: " + driver.getTitle());
		System.out.println("URL de la pagina: " + driver.getCurrentUrl());	
		
		driver.navigate().forward();
		
		System.out.println("=====================");
		
		System.out.println("Titulo de la pagina: " + driver.getTitle());
		System.out.println("URL de la pagina: " + driver.getCurrentUrl());
		
		driver.navigate().refresh();
		
	
		driver.quit();
		
	}

	@Test
	public void titleTest() {
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		//voy a la pagina principal
		driver.get("https://seleniumjavalocators.vercel.app/pages/login.html");
		
		WebElement titleElement = driver.findElement(By.id("titulo"));
		System.out.println("Titulo de la pagina: " + titleElement.getText());
	
		driver.quit();
		
	}
	
	@Test
	public void loginTest() {
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		//voy a la pagina principal
		driver.get("https://seleniumjavalocators.vercel.app/pages/login.html");
		
		WebElement userNameInput = driver.findElement(By.id("username_id"));
		userNameInput.sendKeys("jperez");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WebElement passwordInput = driver.findElement(By.id("password"));
		passwordInput.sendKeys("banco123");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userNameInput = driver.findElement(By.id("username_id"));
		userNameInput.clear();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		userNameInput.sendKeys("elopez");
		
		
		WebElement loginButton = driver.findElement(By.id("iniciarSesionBtn"));
		loginButton.click();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WebElement loginMessage = driver.findElement(By.id("mensajeInicioSesion"));
		System.out.println(loginMessage.getText());
		
		driver.quit();
		
	}
	
	@Test
	public void loginTestWithSendKeysEnter() {
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		//voy a la pagina principal
		driver.get("https://seleniumjavalocators.vercel.app/pages/login.html");
		
		WebElement userNameInput = driver.findElement(By.id("username_id"));
		userNameInput.sendKeys("jperez");
				
		WebElement passwordInput = driver.findElement(By.id("password"));
		passwordInput.sendKeys("banco123");	
		
		WebElement loginMessage = driver.findElement(By.id("mensajeInicioSesion"));
		System.out.println(loginMessage.getText());
		
		driver.quit();
		
	}
	
	@Test
	public void sampleFirefoxTest() {
		driver = new FirefoxDriver();
		
		driver.manage().window().maximize();
		
		//voy a la pagina principal
		driver.get("https://seleniumjavalocators.vercel.app/pages/login.html");
		
		System.out.println("Titulo de la pagina: " + driver.getTitle());
		System.out.println("URL de la pagina: " + driver.getCurrentUrl());	
		
		driver.navigate().to("https://seleniumjavalocators.vercel.app/pages/login.html");
		
		System.out.println("=====================");
		
		System.out.println("Titulo de la pagina: " + driver.getTitle());
		System.out.println("URL de la pagina: " + driver.getCurrentUrl());	
		
		driver.navigate().back();
		
		System.out.println("=====================");
		
		System.out.println("Titulo de la pagina: " + driver.getTitle());
		System.out.println("URL de la pagina: " + driver.getCurrentUrl());	
		
		driver.navigate().forward();
		
		System.out.println("=====================");
		
		System.out.println("Titulo de la pagina: " + driver.getTitle());
		System.out.println("URL de la pagina: " + driver.getCurrentUrl());
		
		driver.navigate().refresh();
		
	
		driver.quit();
		
	}
	
}
