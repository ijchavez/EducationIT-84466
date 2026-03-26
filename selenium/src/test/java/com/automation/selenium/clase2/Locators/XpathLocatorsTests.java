package com.automation.selenium.clase2.Locators;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class XpathLocatorsTests {

	/*******************************************************
	*  XPATH                                               *
	* "//ElementoHTML[@propiedad='Valor']"                 *
	* "//ElementoHTML[contains(text(),'Texto a buscar')]"  *
	* "//ElementHTML[contains(@atributo,'Parte del Valor')]*
	* "//ElementoHTML[text()='Texto a Buscar']             *
	* //       : Posicion sobre el nodo actual.            *
	* Tagname  : Tagname del nodo.                         *
	* @        : Atributo a elegir.                        *
	* Attribute: Nombre del atributo del nodo.             *
	* Value    : Valor del atributo.                       *
	* https://www.guru99.com/xpath-selenium.html           *
	*******************************************************/
	public WebDriver driver;
	private static final String URL = "https://seleniumjavalocators.vercel.app/pages/locators-challenge.html";
	@Test
	public void xpathLocatorsTest() {
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.navigate().to(URL);
		
		//ElementoHTML[@propiedad='Valor'] 
		WebElement userInput = driver.findElement(By.xpath("//input[@class='field alpha-field']"));
		userInput.sendKeys("alberto gonzalez pires");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ElementoHTML[contains(text(),'Texto a buscar')]
		WebElement nestedStructuresZoneElement = driver.findElement(By.xpath("//h2[contains(text(),'Zona')]"));
		System.out.println(nestedStructuresZoneElement.getText());
		
		//ElementoHTML[text()='Texto a Buscar']
		WebElement noValidationsParagraph = driver.findElement(By.xpath("//p[text()='Sin validaciones.']"));
		System.out.println(noValidationsParagraph.getText());
		
		//*[contains(@data-testid,'stat')]
		WebElement stateChangerButton = driver.findElement(By.xpath("//*[contains(@data-testid,'stat')]"));
		System.out.println(stateChangerButton.getText());
		
		List<WebElement> orXpathElements = driver.findElements(By.xpath("//*[@class='link-like' or @role='status']"));
		
		for(final WebElement element : orXpathElements) {
			System.out.println(">>> " + element.getText());
			
		}
		
		//span[@class='status-label' and @data-state='initial']
		WebElement stateBadge = driver.findElement(By.xpath("//span[@class='status-label' and @data-state='initial']"));
		System.out.println("==== " + stateBadge.getText());
		
		//li[@data-hint='id']//code
		// Encontra el elemento del dom code, que este adentro de un elemento del dom li que tenga un data-hint = id
		WebElement userIdCodeElement = driver.findElement(By.xpath("//li[@data-hint='id']//code"));
		System.out.println("==== " + userIdCodeElement.getText() + " ====");
		
		driver.quit();
		
	}
	
}
