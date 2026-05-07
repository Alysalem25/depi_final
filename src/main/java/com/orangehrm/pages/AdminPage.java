package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPage {
    private WebDriver driver;

    // Locators for Admin page elements
    private By adminMenuTab = By.xpath("//span[text()='Admin']");

    public AdminPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isOnDashboard() {
        return driver.getCurrentUrl().contains("dashboard");
    }

    public void clickAdminTab() {
        driver.findElement(adminMenuTab).click();
    }
}
