package com.automation.selenium.clase2.Locators;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CssSelectorsTest {
		/*
		 * ID -> siempre y cuanto sea estation 
		 * name
		 * Atributo Funcional ej: datatest-id, data-hint
		 * cssSelector / xpath
		 * className
		 * */		
		public WebDriver driver;
		@Test
		public void xpathLocatorsTest() {
			driver = new ChromeDriver();
			
			driver.manage().window().maximize();
			driver.navigate().to("https://seleniumjavalocators.vercel.app/pages/locators-challenge.html");
			
			//  ->  ElementoHTML[propiedad='Valor'] 
			WebElement userLabelElement = driver.findElement(By.cssSelector("label[for='user_id_input']"));
			System.out.println(userLabelElement.getText());
			
			//  -> #challengeSubmit				
			WebElement challengeSubmitButton = driver.findElement(By.cssSelector("#challengeSubmit"));
			System.out.println(challengeSubmitButton.getText());
			
			
			driver.quit();
			
		}
}
