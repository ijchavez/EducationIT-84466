package com.automation.selenium.clase4.PageObjectModel.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LandingPage {
	private WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
	}
	
	@FindBy(xpath="//a[@href='./pages/login.html']")
	private WebElement loginLink;
	
	@FindBy(xpath="//a[@href='./pages/registro.html']")
	private WebElement registrationLink;
	
	public void clickOnLoginLink() {
		loginLink.click();
	}
	
	public void clickOnRegistrationLink() {
		registrationLink.click();
	}
}
