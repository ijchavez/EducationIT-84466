package com.automation.selenium.clase6;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumNetworkConditions;
import org.openqa.selenium.chromium.HasNetworkConditions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automation.selenium.clase6.utilities.Utilities;

public class NetworkConditionsTests {
	WebDriver driver;
	String url = "https://seleniumjavalocators.vercel.app/pages/login.html";
	
	@BeforeMethod
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
		
		driver = new ChromeDriver(options);
		driver.get(url);
		driver.manage().window().maximize();
	}
	
	@Test
	public void setCustomNetworkConditionsTest() {
		// Aplica una red simulada con conexion activa, 250 ms de latencia
		// y limites de descarga/subida para verificar la configuracion custom.
		Utilities.setNetworkConditions(driver, false, 250, 750, 250);
		
		// Recupera desde el driver las condiciones actualmente aplicadas
		// para validar que Selenium haya tomado los valores esperados.
		ChromiumNetworkConditions currentConditions =
				((HasNetworkConditions) driver).getNetworkConditions();
		
		// Verifica que la sesion siga online y que los parametros de throttling
		// coincidan con los enviados al helper.
		Assert.assertFalse(currentConditions.getOffline());
		Assert.assertEquals(currentConditions.getLatency().toMillis(), 250L);
		Assert.assertEquals(currentConditions.getDownloadThroughput(), 750);
		Assert.assertEquals(currentConditions.getUploadThroughput(), 250);
		
		// Limpia la configuracion custom para dejar el navegador sin emulacion
		// antes de probar la sobrecarga que fuerza el modo offline.
		Utilities.resetNetworkConditions(driver);

		// Aplica la configuracion base del helper: navegador offline con
		// 250 ms de latencia simulada.
		Utilities.setNetworkConditions(driver);

		// Consulta nuevamente el estado actual de la red para validar
		// que la sobrecarga simple haya dejado la sesion sin conectividad.
		ChromiumNetworkConditions offlineConditions =
				((HasNetworkConditions) driver).getNetworkConditions();
		
		// Comprueba que ahora el navegador este offline y conserve
		// la latencia definida por el helper base.
		Assert.assertTrue(offlineConditions.getOffline());
		Assert.assertEquals(offlineConditions.getLatency().toMillis(), 250L);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.navigate().refresh();
		// Restaura las condiciones normales para no dejar estado emulado
		// activo al finalizar el test.
		Utilities.resetNetworkConditions(driver);
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
		
		driver.close();
	}
}
