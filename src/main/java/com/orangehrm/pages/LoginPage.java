package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    // Locators
    private By usernameInput = By.name("username");
    private By passwordInput = By.name("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    // <a href="/web/index.php/auth/logout" role="menuitem" class="oxd-userdropdown-link">Logout</a>
    private By logoutButton = By.cssSelector("a[href='/web/index.php/auth/logout']");
    // <span data-v-bdd6d943="" class="oxd-userdropdown-tab"><img data-v-bdd6d943="" alt="profile picture" class="oxd-userdropdown-img" src="/web/index.php/pim/viewPhoto/empNumber/7"><p data-v-bdd6d943="" class="oxd-userdropdown-name">Lily David</p><i data-v-bddebfba="" data-v-bdd6d943="" class="oxd-icon bi-caret-down-fill oxd-userdropdown-icon"></i></span>
    private By logoutTab = By.cssSelector("span[class='oxd-userdropdown-tab']");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

//    public void loginPageTestFlow()

    // TC_LOGIN_001 -- AC_001 -- Verify successful login with valid email and password
    public void loginTC_001(String username, String password) {
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public void logout(){
        driver.findElement(logoutTab).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(logoutButton).click();        
    }







}
