package com.automation.selenium.clase6;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

import com.automation.selenium.clase6.utilities.Utilities;

public class RelativeLocators {
	WebDriver driver;
	String url = "https://seleniumjavalocators.vercel.app/";

	@BeforeMethod
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");		

		driver = new ChromeDriver(options);

		driver.get(url);
		
	}
	
	@Test
	public void relativeLocators() {
		WebElement tituloPrincipal = driver.findElement(By.tagName("h1"));
		WebElement parrafoSuperior = driver.findElement(with(By.tagName("p")).above(tituloPrincipal));
		WebElement parrafoInferior = driver.findElement(with(By.tagName("p")).below(tituloPrincipal));
		
		System.out.println(tituloPrincipal.getText());
		System.out.println(parrafoSuperior.getText());
		System.out.println(parrafoInferior.getText());
		
	}
	
	@Test
	public void tabTest() {
		driver.switchTo().newWindow(WindowType.TAB);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.navigate().to("https://seleniumjavalocators.vercel.app/pages/login.html");
		System.out.println(driver.getTitle());
		
	}
	
	@Test
	public void windowTest() {
		driver.switchTo().newWindow(WindowType.WINDOW);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.navigate().to("https://seleniumjavalocators.vercel.app/pages/login.html");
		System.out.println(driver.getTitle());
		
	}
	
	@Test
	public void screenshotFromElementTest() throws IOException {
		WebElement stateTransitioningElement = driver.findElement(By.xpath("//a[@href='./pages/state-transitioning.html']"));
		
		File file=stateTransitioningElement.getScreenshotAs(OutputType.FILE);
		File destFile = new File("state-transitioning.png");
		FileUtils.copyFile(file,destFile);
	
	}
	
	@AfterMethod
	public void finTest(ITestContext context) throws IOException, InvalidFormatException {
		LocalDateTime datetime = LocalDateTime.now();
		String dateTestName = datetime.getNano() + "_" + context.getName();
		Path outputDirectory = Path.of(System.getProperty("user.dir")).toAbsolutePath();
		String imgPath = outputDirectory.resolve(dateTestName + "_image.png").toString();
		String docPath = outputDirectory.resolve("Documento_de_evidencias" + dateTestName + ".docx").toString();
		
		Utilities.takeScreenshot(driver, imgPath);
		Utilities.createDocxFile(driver, docPath, imgPath);
		
		driver.quit();
		
	}
}
