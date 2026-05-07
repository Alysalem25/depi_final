package com.orangehrm;

import com.orangehrm.pages.AdminPage;
import com.orangehrm.pages.LoginPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class Main {

    private static WebDriver driver;
    private static LoginPage loginPage;
    private static AdminPage adminPage;

    public static void main(String[] args) {

        try {

            setupDriver();

            loginPage = new LoginPage(driver);
            adminPage = new AdminPage(driver);

            // =========================
            // TC_LOGIN_001
            // Valid Login Test
            // =========================
            test1();

            // =========================
            // TC_LOGIN_005
            // Invalid Login Test
            // =========================
            test2();

            // =========================
            // TC_LOGIN_006
            // Invalid Login Test for 5 times
            // =========================
            test3();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println(" Browser Closed");
            }
        }
    }

    // ====================================
    // Setup Chrome Driver
    // ====================================
    private static void setupDriver() throws Exception {
        System.setProperty("java.net.preferIPv4Stack", "true");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Start ChromeDriver manually to bypass Java 25 bug
        String chromeDriverPath = System.getProperty("user.home") + "\\.cache\\selenium\\chromedriver\\win64\\147.0.7727.117\\chromedriver.exe";
        ProcessBuilder pb = new ProcessBuilder(chromeDriverPath, "--port=9515");
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process driverProcess = pb.start();
        
        // Ensure the chromedriver process is killed when the program exits
        Runtime.getRuntime().addShutdownHook(new Thread(driverProcess::destroy));

        // Wait a second for the server to start
        Thread.sleep(2000);

        driver = new RemoteWebDriver(
                new URL("http://localhost:9515"),
                options
        );
        driver.manage().window().maximize();
        System.out.println(" Chrome Started Successfully");
    }

    // ====================================
    // TC_LOGIN_001
    // ====================================
    private static void test1() throws InterruptedException {
        System.out.println("\n========== TC_LOGIN_001 ==========");
        loginPage.open();
        displayTestCaseNavbar("TC_LOGIN_001: Valid Login Test");
        waitTime(3000);
        loginPage.loginTC_001("Admin", "admin123");
        waitTime(4000);
        displayTestCaseNavbar("TC_LOGIN_001: Verifying Dashboard");
        if (adminPage.isOnDashboard()) {
            System.out.println(" Login Successful");
            System.out.println(" TC_LOGIN_001 Passed");
            adminPage.clickAdminTab();
            displayTestCaseNavbar("TC_LOGIN_001: Navigated to Admin Menu");
            System.out.println(" Navigated to Admin Menu");
            waitTime(2000);
            loginPage.logout();
            System.out.println(" Logged out successfully");
        } else {
            System.out.println(" TC_LOGIN_001 Failed");
        }
    }

    // ====================================
    // TC_LOGIN_005
    // ====================================
    private static void test2() throws InterruptedException {
        System.out.println("\n========== TC_LOGIN_005 ==========");
        loginPage.open();
        displayTestCaseNavbar("TC_LOGIN_005: Invalid Login Test with passs: wrong123");
        waitTime(3000);
        loginPage.loginTC_001("Admin", "wrong123");
        waitTime(4000);
        displayTestCaseNavbar("TC_LOGIN_005: Verifying Login Failed");
        if (adminPage.isOnDashboard()) {
            System.out.println(" TC_LOGIN_005 Failed");
        } else {
            System.out.println(" Login Failed As Expected");
            System.out.println(" TC_LOGIN_005 Passed");
        }
    }



    // ====================================
    // TC_LOGIN_006
    // ====================================
    private static void test3() throws InterruptedException {
        System.out.println("\n========== TC_LOGIN_006 ==========");
        loginPage.open();
        displayTestCaseNavbar("TC_LOGIN_006: Invalid Login Test with passs: wrong123 , times: 5");
        waitTime(3000);
        for (int i = 1; i <= 5 ; i++) {
            System.out.println(" Attempt " + i);
            displayTestCaseNavbar("TC_LOGIN_006Attempt " + i);
            loginPage.loginTC_001("Admin", "wrong123");
            // Wait for the page to reload and show the error message before trying again
            waitTime(2000); 
        }
        waitTime(2000);
        displayTestCaseNavbar("TC_LOGIN_006: Verifying Login Failed");
        if (adminPage.isOnDashboard()) {
            System.out.println(" TC_LOGIN_006 Failed");
        } else {
            System.out.println(" Login Failed As Expected");
            System.out.println(" TC_LOGIN_006 Passed");
        }
    }

    // ====================================
    // Reusable Wait Method
    // ====================================
    private static void waitTime(int milliseconds) {

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // ====================================
    // Display Test Case Navbar
    // ====================================
    private static void displayTestCaseNavbar(String testName) {
        if (driver instanceof org.openqa.selenium.JavascriptExecutor) {
            try {
                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                String script =
                        "var banner = document.getElementById('selenium-test-navbar');" +
                        "if (!banner) {" +
                        "  banner = document.createElement('div');" +
                        "  banner.id = 'selenium-test-navbar';" +
                        "  banner.style.position = 'fixed';" +
                        "  banner.style.bottom = '0';" +
                        "  banner.style.left = '0';" +
                        "  banner.style.width = '100%';" +
                        "  banner.style.backgroundColor = '#ff9800';" +
                        "  banner.style.color = 'white';" +
                        "  banner.style.textAlign = 'center';" +
                        "  banner.style.padding = '10px';" +
                        "  banner.style.fontSize = '22px';" +
                        "  banner.style.fontWeight = 'bold';" +
                        "  banner.style.zIndex = '999999';" +
                        "  banner.style.boxShadow = '0 -4px 6px rgba(0,0,0,0.3)';" +
                        "  banner.style.fontFamily = 'Arial, sans-serif';" +
                        "  document.body.appendChild(banner);" +
                        "}" +
                        "banner.innerText = '▶ RUNNING: ' + arguments[0];";
                js.executeScript(script, testName);
            } catch (Exception e) {
                // Ignore if javascript execution fails (e.g., page is still loading)
            }
        }
    }
}