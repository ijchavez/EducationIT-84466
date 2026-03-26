package com.automation.selenium.clase4.PageObjectModel.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

public class RegistrationPage {
	private WebDriver driver;
	
	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
	}
	
	@FindBy(id="name")
	private WebElement nameInput;
	
	@FindBy(id="email")
	private WebElement emailInput;
	
	@FindBy(id="username")
	private WebElement usernameInput;
	
	@FindBy(id="password")
	private WebElement passwordInput;
	
	@FindBy(id="confirmPassword")
	private WebElement confirmPasswordInput;
	
	@FindBy(id="mostrarMensajeBtn")
	private WebElement registrationButton;
	
	@FindBy(id="mensajeInicioSesion")
	private WebElement registrationMessage;	
	
	public void fillNameInput(String name) {
		nameInput.sendKeys(name);
	}
	
	public void fillEmailInput(String email) {
		emailInput.sendKeys(email);
	}
	
	public void fillUsernameInput(String username) {
		usernameInput.sendKeys(username);
	}
	
	public void fillPasswordInput(String password) {
		passwordInput.sendKeys(password);
	}
	
	public void fillConfirmPasswordInput(String confirmPassword) {
		confirmPasswordInput.sendKeys(confirmPassword);
	}
	
	public void doClickOnRegistrationButton() {
		registrationButton.click();
	}
	
	public void checkRegistrationMessage(String registrationName) {
		Assert.assertEquals(registrationMessage.getText(), 
				"Cuenta creada para '" + registrationName + "'. Seras redirigido a la dashboard mock con tu perfil precargado.");
	}
}
