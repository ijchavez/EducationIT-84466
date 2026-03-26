package com.automation.selenium.clase3.TestNG;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automation.selenium.evidence.EvidenceService;

public class LoginTests {

    private static final String LOGIN_URL =
            "https://seleniumjavalocators.vercel.app/pages/login.html";

    private WebDriver driver;

    private EvidenceService evidenceService;

    @BeforeClass
    public void setupEvidenceService() {
        evidenceService = new EvidenceService();
    }

    
    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(LOGIN_URL);
    }

    @Test(description = "Successful login test")
    public void loginTest()  {
        WebElement userNameInput = driver.findElement(By.id("username_id"));
        userNameInput.sendKeys("usuario.demo");

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("Qa1234!");

        WebElement loginButton = driver.findElement(By.id("iniciarSesionBtn"));
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement loginMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("mensajeInicioSesion"))
        );

        WebElement loginMessageEyebrow = loginMessage.findElement(By.id("loginMessageEyebrow"));
        WebElement loginMessageTitle = loginMessage.findElement(By.id("loginMessageTitle"));
        WebElement loginMessageBody = loginMessage.findElement(By.id("loginMessageBody"));

        Assert.assertEquals(loginMessageEyebrow.getText(), "LOGIN CORRECTO");
        Assert.assertEquals(loginMessageTitle.getText(), "Inicio de sesion exitoso");
        Assert.assertEquals(
                loginMessageBody.getText(),
                "Bienvenido al laboratorio. El acceso fue concedido correctamente."
        );
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
    //crear algo que lea el testng-results.xml para clase
}