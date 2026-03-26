package com.automation.selenium.clase4.PageObjectModel.base;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.automation.selenium.clase4.PageObjectModel.pages.DashboardPage;
import com.automation.selenium.clase4.PageObjectModel.pages.LandingPage;
import com.automation.selenium.clase4.PageObjectModel.pages.LoginPage;
import com.automation.selenium.clase4.PageObjectModel.pages.RegistrationPage;
import com.automation.selenium.evidence.EvidenceService;

public class BaseTest {
	   private static final String LANDING_URL =
	            "https://seleniumjavalocators.vercel.app";

	    private WebDriver driver;

	    private EvidenceService evidenceService;
	    protected LoginPage loginPage;
	    protected LandingPage landingPage;
	    protected RegistrationPage registrationPage;
	    protected DashboardPage dashboardPage;
	    
	    @BeforeClass
	    public void setupEvidenceService() {
	        evidenceService = new EvidenceService();
	    }

	    /*
	     * org.testng.TestNGException: 
		 *	Parameter 'browser' is required by BeforeMethod on method setup but has not been marked @Optional or defined
	     *
	     * Tenemos que definir un Optional dentro de el parametro String browser
	     * */
	    @Parameters("browser")
	    @BeforeMethod
	    public void setup(@Optional("chrome")String browser) {
			if(browser.equalsIgnoreCase("chrome")) {
				System.out.println("Inicio de pruebas en Chrome");
				driver = new ChromeDriver();
				
			}
			if(browser.equalsIgnoreCase("firefox")) {
				System.out.println("Inicio de pruebas en Firefox");
				driver = new FirefoxDriver();
				
			}
			if(browser.equalsIgnoreCase("edge")) {
				System.out.println("Inicio de pruebas en Edge");
				driver = new EdgeDriver();
				
			}
		    driver.manage().window().maximize();
	        driver.get(LANDING_URL);
	        
	        landingPage = new LandingPage(driver);
	        loginPage = new LoginPage(driver);
	        registrationPage = new RegistrationPage(driver);
	        dashboardPage = new DashboardPage(driver);
	        
	    }
	    
	    @AfterMethod(alwaysRun = true)
	    public void tearDown(ITestResult testResult) throws IOException {
	        try {
	            if (driver != null) {
	                evidenceService.captureTestEvidence(driver, testResult.getMethod().getMethodName());
	            }
	        } finally {
	            if (driver != null) {
	                driver.quit();
	            }
	        }
	    }
	    
	    @AfterSuite
	    public void afterSuite() {
	    	System.out.println("Finalizó la suite de pruebas");
	    }
}
