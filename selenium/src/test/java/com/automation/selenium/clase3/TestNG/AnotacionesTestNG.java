package com.automation.selenium.clase3.TestNG;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AnotacionesTestNG {
	@BeforeSuite
	public void beforeSuite() {
		System.out.println("BEFORE SUITE");
	}
	@BeforeTest
	public void beforeTest() {
		System.out.println("BEFORE TEST");
	}
	@BeforeClass
	public void beforeClass() {
		System.out.println("BEFORE CLASS");
	}
	int beforeMethodI = 0;
	@BeforeMethod
	public void beforeMethod() {
		beforeMethodI++;
		System.out.println("BEFORE METHOD " + beforeMethodI);
	}

	@Test(priority = 2, description = "TEST NUMERO UNO")
	public void testUno() {
		System.out.println("TEST UNO");
	}
	@Test(priority = 3, description = "TEST NUMERO DOS")
	public void testDos() {
		System.out.println("TEST DOS");
	}
	@Test(description = "TEST NUMERO TRES")
	public void testTres() {
		System.out.println("TEST TRES");
	}
	int afterMethodI = 0;
	@AfterMethod
	public void afterMethod() {
		afterMethodI++;
		System.out.println("AFTER METHOD " + afterMethodI);
	}
	@AfterClass
	public void afterClass() {
		System.out.println("AFTER CLASS");
	}
	@AfterTest
	public void afterTest() {
		System.out.println("AFTER TEST");
	}
	@AfterSuite
	public void afterSuite() {
		System.out.println("AFTER SUITE");
	}
}
