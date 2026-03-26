package com.automation.selenium.clase4.PageObjectModel.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.automation.selenium.clase4.PageObjectModel.support.WaitHelpers;

public class LoginPage {
	private WebDriver driver;
	
	//@FindBy(id="username_id")
	//private WebElement userNameInput;
	private static final By USERNAME_INPUT = By.id("username_id");
	
	//@FindBy(id="password")
	//WebElement passwordInput;
	private static final By PASSWORD_INPUT = By.id("password");
	
	@FindBy(id="iniciarSesionBtn")
	WebElement loginButton;
	
	@FindBy(id="mensajeInicioSesion")
	WebElement loginMessage;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
	}
	
	public void fillUserNameInput(String username) {
        //userNameInput.sendKeys(username);
		driver.findElement(USERNAME_INPUT).sendKeys(username);
	}
	
	public void fillPasswordInput(String password) {
		//passwordInput.sendKeys(password);
		driver.findElement(PASSWORD_INPUT).sendKeys(password);
	}
	
	public void clickOnLoginButton() {
        loginButton.click();
	}
	
	public WebElement getLoginMessage() {
		WebDriverWait wait = WaitHelpers.stablishWait(driver, 20);
		WebElement returnedLoginMessage = wait.until(ExpectedConditions.visibilityOf(loginMessage));
		
		return returnedLoginMessage;
	}
	
	public void checkSuccessLoginMessageEyebrow() {
		WebElement checkLoginMessageEyebrw = this.getLoginMessage().findElement(By.id("loginMessageEyebrow"));
		Assert.assertEquals(checkLoginMessageEyebrw.getText(), "LOGIN CORRECTO");
	}
	
	public void checkSuccessLoginMessageTitle() {
		WebElement checkLoginMessageTitle = this.getLoginMessage().findElement(By.id("loginMessageTitle"));
		Assert.assertEquals(checkLoginMessageTitle.getText(), "Inicio de sesion exitoso");
	}
	
	public void checkSuccessLoginMessageBody() {
		WebElement checkLLoginMessageBody = this.getLoginMessage().findElement(By.id("loginMessageBody"));
		Assert.assertEquals(checkLLoginMessageBody.getText(), "Bienvenido al laboratorio. El acceso fue concedido correctamente. Seras redirigido a una pagina mock.");
	}
	
	public void checkBlockedLoginMessageEyebrow() {
		WebElement checkLoginMessageEyebrw = this.getLoginMessage().findElement(By.id("loginMessageEyebrow"));
		Assert.assertEquals(checkLoginMessageEyebrw.getText(), "ACCESO RESTRINGIDO");
	}
	
	public void checkBlockedLoginMessageTitle() {
		WebElement checkLoginMessageTitle = getLoginMessage().findElement(By.id("loginMessageTitle"));
		Assert.assertEquals(checkLoginMessageTitle.getText(), "Usuario bloqueado");
	}
	
	public void checkBlockedLoginMessageBody() {
		WebElement checkLoginMessageBody = getLoginMessage().findElement(By.id("loginMessageBody"));
		Assert.assertEquals(checkLoginMessageBody.getText(), "La cuenta esta bloqueada temporalmente. Debes solicitar un desbloqueo antes de continuar.");
	}
	
	public void checkInvalidLoginMessageEyebrow() {
		WebElement checkLoginMessageEyebrw = this.getLoginMessage().findElement(By.id("loginMessageEyebrow"));
		Assert.assertEquals(checkLoginMessageEyebrw.getText(), "CREDENCIALES INVALIDAS");
	}
	
	public void checkInvalidLoginMessageTitle() {
		WebElement checkLoginMessageTitle = getLoginMessage().findElement(By.id("loginMessageTitle"));
		Assert.assertEquals(checkLoginMessageTitle.getText(), "Usuario o contrasena incorrecto");
	}
	
	public void checkInvalidLoginMessageBody() {
		WebElement checkLoginMessageBody = getLoginMessage().findElement(By.id("loginMessageBody"));
		Assert.assertEquals(checkLoginMessageBody.getText(), "La combinacion ingresada no es valida. Revisa la seccion de credenciales de prueba e intenta nuevamente.");
	}		
	
}
