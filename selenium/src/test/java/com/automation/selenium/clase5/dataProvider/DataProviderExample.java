package com.automation.selenium.clase5.dataProvider;

import org.testng.annotations.DataProvider;

import com.automation.selenium.clase5.utilities.Utilities;

public class DataProviderExample {
	@DataProvider(name="usuariosInvalidos")
	public Object[][] usuariosInvalidos(){
		//email------------------password
		return new Object[][]{
			{"pepe", "1234"},
			{"usuario.demo", "Qa1234!!"},
			{"invalidEmail@", "pasdsda"},
			{" ", " "}
			
		};
	}
	@DataProvider(name="usuariosInvalidosExcel")
	public Object[][] usuariosInvalidosExcel() throws Exception{
		return Utilities.readFromExcelFile("C:\\Viejo D\\EducationIT-84466\\selenium\\src\\test\\resources\\inicioSesionInvalido.xlsx", "Hoja1");
	}
	
	@DataProvider(name="registroValido")
	public Object[][] registroValidosExcel() throws Exception{
		return Utilities.readFromExcelFile("C:\\Viejo D\\EducationIT-84466\\selenium\\src\\test\\resources\\registroValido.xlsx", "Hoja1");
	}
	
}
