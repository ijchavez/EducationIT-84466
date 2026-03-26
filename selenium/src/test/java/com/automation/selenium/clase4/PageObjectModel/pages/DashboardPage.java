package com.automation.selenium.clase4.PageObjectModel.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.testng.Assert;

public class DashboardPage {

	private WebDriver driver;
	
	public DashboardPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
	}
	

	@FindBy(id="dashboardTitle")
	private WebElement dashboardTitle;
	
	@FindBy(id="profileUser")
	private WebElement profileUser;
	
	public void checkDashboardTitle(String registrationName) {
		Assert.assertEquals(dashboardTitle.getText(), "Bienvenido, ".concat(registrationName));
		
	}
	
	public void checkProfileUser(String username) {
		Assert.assertEquals(profileUser.getText(), username);
		
	}
}
