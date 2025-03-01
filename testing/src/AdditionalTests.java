import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdditionalTests {
    public static void main(String[] args) {
        // Set the path to ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\anike\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Test Case 1: Verify Login Form Loads Correctly
            driver.get("http://localhost/github/DevOpsLab/website/login.php"); // Adjust URL as needed
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signIn"))); // Wait for the sign-in form to be visible

            // Check if the sign-in form is displayed
            WebElement signInForm = driver.findElement(By.id("signIn"));
            if (signInForm.isDisplayed()) {
                System.out.println("✅ Test Passed: Login form is displayed correctly.");
            } else {
                System.out.println("❌ Test Failed: Login form is NOT displayed.");
            }

           

        } catch (Exception e) {
            System.out.println("❌ Test Failed: Exception occurred - " + e.getMessage());
        } finally {
            // Close browser
            driver.quit();
        }
    }
}