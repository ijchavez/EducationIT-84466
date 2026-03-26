package com.automation.selenium.clase1;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Laboratorio1 {
	/*
	 * - Crear una nueva clase estándar con
			nombre Laboratorio1 e importar librerías
			de Selenium y Junit manualmente
			○ import org.junit.*;
			○ import org.openqa.selenium.*;
			Testing Automation con Selenium
		- Crear un test ingresando el comando
			@Test, debajo de este crear un método
			llamado lab1_test().
		- Dentro del método imprimir un “¡Hola
			Mundo de Automatización!”
		- Ejecutar Test y verificar resultado de
			ejecución en solapa “Junit” y en solapa
			“Consola”
	 * 
	 * 
	 * */
	@Test
	public void lab1_test() {
		System.out.println("¡Hola Mundo de Automatización!");
		
	}
	/*
	 * Volver a la clase creada y crear un test
		ingresando el comando @Test. Debajo de
		este, crear un método llamado lab1_E1.
		4. Importar librerías de controlador Chrome
		import
		org.openqa.selenium.chrome.ChromeDriver;
		5. Codificar la configuración de propiedades
		de Chrome driver.
		6. Codificar dentro del método e Instanciar
		Chrome driver.
		7. Codificar ingreso a de prueba:
		http://automationpractice.pl
		8. Codificar cierre de navegador.
		9. Correr Test con Junit.
		10. Verificar resultados.
	 * 
	 * */
	
	@Test
	public void lab1_E1() {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		//voy a la pagina principal
		driver.get("https://seleniumjavalocators.vercel.app/pages/login.html");
		System.out.println("Titulo de la pagina: " + driver.getTitle());
		System.out.println("URL de la pagina: " + driver.getCurrentUrl());	
		driver.quit();
	}
	
	/*
		 * Volver a la clase creada en la etapa 1.2
		(Laboratorio1.java) y crear un nuevo test
		ingresando @Test y debajo de este, un
		método público llamado lab1_E2.
		2. Importar librerías de controlador Firefox
		import org.openqa.selenium.firefox.*;
		3. Codificar la configuración de propiedades
		de Firefox.
		4. Codificar dentro del método e Instanciar
		Firefox.
		5. Codificar ingreso a de prueba:
		http://automationpractice.pl
		6. Codificar cambio de tamaño del
		navegador para que maximice
		7. Codificar cierre de navegador.
		8. Correr Test con Junit.

	 * */
	@Test
	public void lab1_E2() {
		WebDriver driver = new FirefoxDriver();
		driver.get("https://seleniumjavalocators.vercel.app/pages/login.html");
		System.out.println("Titulo de la pagina: " + driver.getTitle());
		System.out.println("URL de la pagina: " + driver.getCurrentUrl());	
		driver.quit();
		
		
		
	}
	
	
		
	/*
	 * Volver a la clase creada en la etapa 1.3 del
		proyecto integrador (Laboratorio1.java) y
		crear un nuevo test ingresando @Test y
		debajo de este, un método llamado
		lab1_E3.

	 * Codificar la configuración de propiedades
		de Chrome driver.
		3. Codificar dentro del método e Instanciar
		Chrome driver.
		4. Codificar ingreso a de prueba:
		http://automationpractice.pl
		5. Codificar localización de elemento por id
		del campo de texto para hacer búsquedas y asignarlo a una variable de tipo
		WebElement.
		Testing Automation con Selenium
		6. Codificar envío de datos al elemento input
		con texto.
		7. Codificar envío de datos al elemento input
		con tecla rápida ENTER. (usar click en vez del enter)
		8. Codificar cierre de navegador.
		9. Correr Test con Junit.
	 * */
	@Test
	public void lab1_E3() {
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://automationexercise.com/products");
		
		//WebElement searchInput = driver.findElement(By.id("search_product"));
		//searchInput.sendKeys("Sleeves Printed Top - White");
		
		WebElement searchInput = driver.findElement(By.id("search_product"));
		searchInput.sendKeys("Sleeves Printed Top - White");
		
		WebElement searchButton = driver.findElement(By.id("submit_search"));
		searchButton.click();
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver.quit();
	}
}
