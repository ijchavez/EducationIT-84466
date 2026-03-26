package com.automation.selenium.clase5.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

public class Utilities {
	
	public static final String RESOURCES_PATH = "C:\\Viejo D\\EducationIT-84466\\selenium\\src\\test\\resources";
	//https://eternallybored.org/misc/wget/
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
	
}
