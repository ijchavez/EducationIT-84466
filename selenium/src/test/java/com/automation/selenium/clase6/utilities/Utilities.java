package com.automation.selenium.clase6.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.chromium.ChromiumNetworkConditions;
import org.openqa.selenium.chromium.HasNetworkConditions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;

public class Utilities {
	
	public static final String RESOURCES_PATH = "C:\\Viejo D\\EducationIT-84466\\selenium\\src\\test\\resources";
	public static final String WGET_EXECUTABLE = RESOURCES_PATH.concat("\\wget.exe");
	public static final String DOWNLOAD_LOCATION = RESOURCES_PATH.concat("\\downloads");
	
 	public static void createDocxFile(WebDriver driver, String nombreArchivo, String rutaImagen) throws IOException, InvalidFormatException {
		File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File imageFile = new File(rutaImagen);
		FileUtils.copyFile(screen, imageFile);
		
		File fichero = new File(nombreArchivo);
		if (!fichero.exists()) {
			try (XWPFDocument docx = new XWPFDocument();
				 InputStream pic = new FileInputStream(rutaImagen);
				 FileOutputStream out = new FileOutputStream(nombreArchivo)) {
				XWPFParagraph paragraph = docx.createParagraph();
				XWPFRun run = paragraph.createRun();
				
				run.setText("Evidencia de pruebas");
				run.setFontSize(13);
				run.addPicture(pic, Document.PICTURE_TYPE_PNG, rutaImagen,
				Units.toEMU(500), Units.toEMU(200));
				
				docx.write(out);
				out.flush();
			}
			return;
		}
		
		try (FileInputStream ficheroStream = new FileInputStream(fichero);
			 XWPFDocument docx = new XWPFDocument(ficheroStream);
			 InputStream pic = new FileInputStream(rutaImagen);
			 FileOutputStream out = new FileOutputStream(nombreArchivo)) {
			XWPFParagraph paragraph = docx.createParagraph();
			XWPFRun run = paragraph.createRun();
			
			run.setText("Evidencia de pruebas");
			run.setFontSize(13);
			run.addPicture(pic, Document.PICTURE_TYPE_PNG, rutaImagen,
			Units.toEMU(500), Units.toEMU(200));
			
			docx.write(out);
			out.flush();
		}
 
    }
	public static void takeScreenshot(WebDriver driver, String imgPath) throws IOException, InvalidFormatException {
		File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screen, new File (imgPath));
		
	}
	public static Object[][] readFromExcelFile(String ruta, String nombreHoja) throws Exception {
		try (FileInputStream file = new FileInputStream(new File(ruta));
			 XSSFWorkbook excelFile = new XSSFWorkbook(file)) {
			//instancio una hoja en base al archivo excel y asignando el nombre de la hoja
			XSSFSheet sheet = excelFile.getSheet(nombreHoja);
			
			//instanciamos una fila
			XSSFRow row;
			
			//tomamos el numero total de filas
			int rows = sheet.getPhysicalNumberOfRows();
			//instanciamos las columnas
			int column = sheet.getRow(0).getPhysicalNumberOfCells();
			
			//instanciamos el objeto bidimensional que nos va a devolver esta funcion
			Object cellValue[][]=new Object[rows][column];
			
			for (int r = 0; r < rows; r++) {
				row = sheet.getRow(r);

				if (row == null){ 
					break; 
				}else{ 
					for (int c = 0; c < column; c++) {
						DataFormatter dataFormatter = new DataFormatter();
						cellValue[r][c] = dataFormatter.formatCellValue(sheet.getRow(r).getCell(c));
						
					} 
					
				}
				
		   }
		   return cellValue;
		}
	
    }
	public static void downloadFile(String[] wget_command) {
        try {
        	// Crea la carpeta de descarga si no existe.
        	// `mkdirs()` también crea carpetas intermedias si hicieran falta.
        	new File(DOWNLOAD_LOCATION).mkdirs();
        	
        	// Construye y ejecuta el proceso externo usando el comando recibido.
        	// En este caso, `wget_command` contiene `wget.exe` y sus parámetros.
			Process exec = new ProcessBuilder(wget_command)
					// Une la salida de error con la salida estándar para poder leer
					// todo desde un único flujo de entrada.
					.redirectErrorStream(true)
					// Inicia el proceso en el sistema operativo.
					.start();
			
			// Prepara un lector para leer, línea por línea, lo que imprime el proceso.
			BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
			
			// Variable auxiliar para ir guardando cada línea leída.
			String line;
			
			// Lee toda la salida del proceso mientras siga enviando texto.
			// Por ejemplo, acá aparecen los mensajes de `wget` sobre la descarga.
			while ((line = reader.readLine()) != null) {
				// Muestra cada línea en consola para poder ver qué está pasando.
				System.out.println(line);
			}
			
        	// Espera a que el proceso termine y guarda su código de salida.
        	// Normalmente `0` significa éxito y otro valor indica error.
        	int exitVal = exec.waitFor();
        	
        	// Imprime el código final del proceso.
        	System.out.println("Exit value: " + exitVal);
        
        } catch (Exception ex) {
        	// Si ocurre cualquier excepción al crear o ejecutar el proceso,
        	// la muestra en consola para facilitar el diagnóstico.
        	System.out.println(ex.toString());
        
        }
	}
	
	public static Connection getConnectionFromDataBase(String dbUrl, String username, String password) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(dbUrl,username,password);
		
	}
	/**
	 * Configura una ubicacion geográfica emulada para la sesión actual de Chrome.
	 *
	 * Este método hace dos cosas que, en conjunto, son necesarias para que un sitio
	 * web pueda leer la ubicación simulada desde {@code navigator.geolocation}:
	 *
	 * 1. Otorga el permiso de geolocalización al origin de la URL indicada.
	 *    Sin este paso, muchos sitios quedan bloqueados por el permiso del navegador
	 *    aunque las coordenadas emuladas estén correctamente definidas.
	 *
	 * 2. Sobrescribe la ubicación del navegador usando el comando CDP
	 *    {@code Emulation.setGeolocationOverride}.
	 *    A partir de ese momento, cuando la página consulte la ubicación, Chrome
	 *    responderá con la latitud y longitud emuladas en vez de intentar usar la
	 *    ubicación real del sistema operativo.
	 *
	 * El parámetro {@code url} no se usa para navegar: se usa para calcular el
	 * {@code origin} exacto sobre el cual Chrome debe conceder el permiso. Por
	 * ejemplo, de {@code https://my-location.org/} se obtiene el origin
	 * {@code https://my-location.org}.
	 *
	 * Orden importante:
	 * - primero se concede el permiso al sitio
	 * - después se aplican las coordenadas emuladas
	 * - recién entonces conviene navegar a la página
	 *
	 * Este método depende de Chrome/Chromium porque utiliza CDP a través de
	 * {@link ChromiumDriver#executeCdpCommand(String, Map)}.
	 *
	 * @param driver webdriver activo basado en Chromium
	 * @param latitude latitud a emular
	 * @param longitude longitud a emular
	 * @param url URL del sitio que va a consumir la geolocalización; se usa para
	 *            derivar el origin y conceder el permiso correspondiente
	 */
	public static void setCoordinates(WebDriver driver, double latitude, double longitude, String url) {
		ChromiumDriver chromiumDriver = (ChromiumDriver) driver;
		Map<String, Object> permisoGeolocalizacion = new HashMap<>();
		Map<String, Object> permiso = new HashMap<>();

		// Chrome necesita que el sitio tenga el permiso "geolocation" concedido
		// para que navigator.geolocation pueda devolver la ubicación emulada.
		permiso.put("name", "geolocation");
		permisoGeolocalizacion.put("permission", permiso);
		permisoGeolocalizacion.put("setting", "granted");
		permisoGeolocalizacion.put("origin", getOriginFromUrl(url));
		chromiumDriver.executeCdpCommand("Browser.setPermission", permisoGeolocalizacion);

		Map<String, Object> coordenadas = new HashMap<>();
		coordenadas.put("latitude", latitude);
		coordenadas.put("longitude", longitude);
		coordenadas.put("accuracy", 1);
		
		// Una vez otorgado el permiso, se reemplaza la ubicación del navegador con
		// las coordenadas indicadas para la sesión actual.
		chromiumDriver.executeCdpCommand("Emulation.setGeolocationOverride", coordenadas);
	}
	/**
	 * Convierte una URL completa en su origin.
	 *
	 * Ejemplos:
	 * - https://my-location.org/          -> https://my-location.org
	 * - https://oldnavy.gap.com/stores    -> https://oldnavy.gap.com
	 * - http://localhost:8080/app         -> http://localhost:8080
	 *
	 * Esto es necesario porque CDP espera el origin del sitio al configurar
	 * permisos, no la URL completa con path.
	 *
	 * @param url URL completa del sitio
	 * @return origin derivado de la URL
	 */
	private static String getOriginFromUrl(String url) {
		URI uri = URI.create(url);
		String origin = uri.getScheme() + "://" + uri.getHost();
		if (uri.getPort() != -1) {
			origin += ":" + uri.getPort();
		}
		return origin;
	}
	public static void devToolsCreateSession(WebDriver driver ) {
		DevTools devTools = ((HasDevTools) driver).getDevTools();
		devTools.createSession();
		
	}
	public static void setMobileMetrics(WebDriver driver, Object width, Object height) {
		Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", width);
        deviceMetrics.put("height", height);
        deviceMetrics.put("mobile", true);
        deviceMetrics.put("deviceScaleFactor", 50);
        
        ((ChromiumDriver) driver).executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);
	}

	/**
	 * Aplica una configuracion base de red para pruebas sobre navegadores Chromium.
	 *
	 * Este atajo deja la sesion en modo offline con una latencia simulada de
	 * 250 ms. Es util para laboratorios o ejemplos donde solo se quiere demostrar
	 * rapidamente que el navegador puede quedar sin conectividad sin tener que
	 * indicar todos los parametros de la emulacion.
	 *
	 * El driver debe implementar {@link HasNetworkConditions}, por lo que este
	 * metodo esta pensado para {@link ChromiumDriver} y sus derivados.
	 *
	 * @param driver webdriver activo basado en Chromium
	 */
	public static void setNetworkConditions(WebDriver driver) {
		setNetworkConditions(driver, true, 250, -1, -1);
	}
	/**
	 * Configura condiciones de red emuladas para la sesion actual del navegador.
	 *
	 * Permite definir si la sesion debe quedar offline, la latencia artificial
	 * y los limites de descarga y subida. Cuando {@code offline} es {@code true},
	 * los valores de throughput se ignoran porque el navegador queda sin acceso
	 * a la red.
	 *
	 * Esta API usa {@link ChromiumNetworkConditions}, que hoy es la alternativa
	 * recomendada en Selenium para escenarios comunes de throttling sobre Chrome
	 * y otros navegadores basados en Chromium.
	 *
	 * @param driver webdriver activo basado en Chromium
	 * @param offline {@code true} para simular falta de conectividad
	 * @param latencyMs latencia minima simulada en milisegundos
	 * @param downloadKbps velocidad maxima de descarga simulada en kbps
	 * @param uploadKbps velocidad maxima de subida simulada en kbps
	 */
	public static void setNetworkConditions(WebDriver driver,
            boolean offline,
            int latencyMs,
            int downloadKbps,
            int uploadKbps) {
		ChromiumNetworkConditions conditions = new ChromiumNetworkConditions();
		conditions.setOffline(offline);
		conditions.setLatency(Duration.ofMillis(latencyMs));
	
		if (!offline) {
			conditions.setDownloadThroughput(downloadKbps);
			conditions.setUploadThroughput(uploadKbps);
		}
	
		((HasNetworkConditions) driver).setNetworkConditions(conditions);
	}
	
	/**
	 * Elimina cualquier condicion de red emulada y restaura el comportamiento
	 * normal de la sesion del navegador.
	 *
	 * @param driver webdriver activo basado en Chromium
	 */
	public static void resetNetworkConditions(WebDriver driver) {
		((HasNetworkConditions) driver).deleteNetworkConditions();
	}
}
