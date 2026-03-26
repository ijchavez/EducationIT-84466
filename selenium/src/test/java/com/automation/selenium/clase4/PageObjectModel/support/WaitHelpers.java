package com.automation.selenium.clase4.PageObjectModel.support;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitHelpers {
	public static WebDriverWait stablishWait(WebDriver driver, int durationOfSeconds) {
		return new WebDriverWait(driver, Duration.ofSeconds(durationOfSeconds));
	}
	

}
