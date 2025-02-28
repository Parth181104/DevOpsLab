import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterTest {
    public static void main(String[] args) {
        // Set the path to ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\anike\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Test Case: Verify Registration Form Loads Correctly
            driver.get("http://localhost/github/DevOpsLab/website/regtry.php"); // Adjust URL as needed
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup"))); // Wait for the sign-up form to be visible

            // Check if the sign-up form is displayed
            WebElement signUpForm = driver.findElement(By.id("signup"));
            if (signUpForm.isDisplayed()) {
                System.out.println("✅ Test Passed: Registration form is displayed correctly.");
            } else {
                System.out.println("❌ Test Failed: Registration form is NOT displayed.");
            }

        } catch (Exception e) {
            System.out.println("❌ Test Failed: Exception occurred - " + e.getMessage());
        } finally {
            // Close browser
            driver.quit();
        }
    }
}