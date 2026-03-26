package com.automation.selenium.clase4.PageObjectModel.test;


import org.testng.annotations.Test;

import com.automation.selenium.clase4.PageObjectModel.base.BaseTest;

public class LoginTests extends BaseTest{

    @Test(description = "Successful login test")
    public void sucessfulLoginTest()  {
    	landingPage.clickOnLoginLink();
    	
    	loginPage.fillUserNameInput("usuario.demo");
    	loginPage.fillPasswordInput("Qa1234!");
    	
    	loginPage.clickOnLoginButton();

        loginPage.checkSuccessLoginMessageEyebrow();
        loginPage.checkSuccessLoginMessageTitle();
        loginPage.checkSuccessLoginMessageBody();
        
    }
    
    @Test(description = "Blocked User login test")
    public void blockedUserTest()  {
       	landingPage.clickOnLoginLink();
       	
    	loginPage.fillUserNameInput("usuario.bloqueado");
    	loginPage.fillPasswordInput("Lock1234!");
    	
    	loginPage.clickOnLoginButton();
    	
    	loginPage.checkBlockedLoginMessageEyebrow();
    	loginPage.checkBlockedLoginMessageTitle();
    	loginPage.checkBlockedLoginMessageBody();
    }

    /*
     * [public void com.automation.selenium.clase4.PageObjectModel.test.LoginTests.invalidUserTest()] 
     * has no parameters defined but was found to be using a data provider (either explicitly specified or inherited from class level annotation).
			Data provider mismatch
			
			- Establecer los parametros en el test de acuerdo al data provider
			- Utilizar los datos traidos por parametro en el test
     * */
    @Test(description = "Invalid User login test", 
    	  //dataProvider = "usuariosInvalidos", 
    		dataProvider = "usuariosInvalidosExcel",
    	  dataProviderClass = com.automation.selenium.clase5.dataProvider.DataProviderExample.class)
    public void invalidUserTest(String user, String password)  {
       	landingPage.clickOnLoginLink();
       	
    	loginPage.fillUserNameInput(user);
    	loginPage.fillPasswordInput(password);
    	
    	loginPage.clickOnLoginButton();
    	
    	loginPage.checkInvalidLoginMessageEyebrow();
    	loginPage.checkInvalidLoginMessageTitle();
    	loginPage.checkInvalidLoginMessageBody();
    }
}