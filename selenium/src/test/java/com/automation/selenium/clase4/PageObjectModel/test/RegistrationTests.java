package com.automation.selenium.clase4.PageObjectModel.test;

import org.testng.annotations.Test;

import com.automation.selenium.clase4.PageObjectModel.base.BaseTest;

public class RegistrationTests extends BaseTest{
	private static final String FULL_NAME = "Fernando Perez";
	private static final String PASSWORD = "fealskqp1123";
	private static final String USERNAME = "ferperez_123";
	@Test
	public void succesfulRegistrationTest() {
		landingPage.clickOnRegistrationLink();
	    
		registrationPage.fillNameInput(FULL_NAME);
	    registrationPage.fillEmailInput("fperez@gmail.com");
	    registrationPage.fillUsernameInput(USERNAME);
	    registrationPage.fillPasswordInput(PASSWORD);
	    registrationPage.fillConfirmPasswordInput(PASSWORD);
	    registrationPage.doClickOnRegistrationButton();
	    registrationPage.checkRegistrationMessage(FULL_NAME);
	    
	    dashboardPage.checkDashboardTitle(FULL_NAME);
	    dashboardPage.checkProfileUser(USERNAME);
	    
	}
	
	@Test(dataProvider = "registroValido",
    	  dataProviderClass = com.automation.selenium.clase5.dataProvider.DataProviderExample.class)
	public void successfulRegistrationWithDataProvider(String fullName, String email, String username, String password) {
		landingPage.clickOnRegistrationLink();
	    
		registrationPage.fillNameInput(fullName);
	    registrationPage.fillEmailInput(email);
	    registrationPage.fillUsernameInput(username);
	    registrationPage.fillPasswordInput(password);
	    registrationPage.fillConfirmPasswordInput(password);
	    registrationPage.doClickOnRegistrationButton();
	    registrationPage.checkRegistrationMessage(fullName);
	    
	    dashboardPage.checkDashboardTitle(fullName);
	    dashboardPage.checkProfileUser(username);
	    
	}
	
}
