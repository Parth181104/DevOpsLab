import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TestingSelenium {
    public static void main(String[] args) {
        // Set the path to ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\anike\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Open the website
            driver.get("http://localhost/github/DevOpsLab/website/index.php");

            // Test Case 1: Verify Page Title
            try {
                System.out.println("Page title is: " + driver.getTitle());
                if (driver.getTitle().equals("TrendyShe")) {
                    System.out.println("✅ Test Passed: Home page loaded successfully.");
                } else {
                    System.out.println("❌ Test Failed: Home page title is incorrect.");
                }
            } catch (Exception e) {
                System.out.println("❌ Test Failed: Exception occurred while checking page title - " + e.getMessage());
            }

            // Test Case 2: Verify Navigation Menu Visibility
            try {
                WebElement navMenu = driver.findElement(By.cssSelector(".navbar"));
                if (navMenu.isDisplayed()) {
                    System.out.println("✅ Test Passed: Navigation menu is visible.");
                } else {
                    System.out.println("❌ Test Failed: Navigation menu is NOT visible.");
                }
            } catch (Exception e) {
                System.out.println("❌ Test Failed: Exception occurred while checking navigation menu - " + e.getMessage());
            }

            // Test Case 3: Verify Content Section Visibility
            try {
                WebElement contentSection = driver.findElement(By.cssSelector(".content"));
                if (contentSection.isDisplayed()) {
                    System.out.println("✅ Test Passed: Content section is visible.");
                } else {
                    System.out.println("❌ Test Failed: Content section is NOT visible.");
                }
            } catch (Exception e) {
                System.out.println("❌ Test Failed: Exception occurred while checking content section - " + e.getMessage());
            }

            // Test Case 4: Verify Products Section Visibility
            try {
                WebElement productsSection = driver.findElement(By.id("newArrival"));
                if (productsSection.isDisplayed()) {
                    System.out.println("✅ Test Passed: Products section is visible.");
                } else {
                    System.out.println("❌ Test Failed: Products section is NOT visible.");
                }
            } catch (Exception e) {
                System.out.println("❌ Test Failed: Exception occurred while checking products section - " + e.getMessage());
            }

            // Test Case 5: Verify External Links
            try {
                List<WebElement> externalLinks = driver.findElements(By.cssSelector("a[target='_blank']")); // Adjust selector as needed
                for (WebElement link : externalLinks) {
                    String originalWindow = driver.getWindowHandle();
                    String linkUrl = link.getAttribute("href");

                    // Click the link
                    link.click();

                    // Switch to the new tab
                    for (String windowHandle : driver.getWindowHandles()) {
                        if (!windowHandle.equals(originalWindow)) {
                            driver.switchTo().window(windowHandle);
                            break;
                        }
                    }

                    // Verify the URL of the new tab
                    if (driver.getCurrentUrl().equals(linkUrl)) {
                        System.out.println("✅ Test Passed: External link opened correctly in a new tab: " + linkUrl);
                    } else {
                        System.out.println("❌ Test Failed: External link did NOT open correctly. Expected: " + linkUrl + ", but got: " + driver.getCurrentUrl());
                    }

                    // Close the new tab and switch back to the original window
                    driver.close();
                    driver.switchTo().window(originalWindow);
                }
            } catch (Exception e) {
                System.out.println("❌ Test Failed: Exception occurred while checking external links - " + e.getMessage());
            }

            
            // Test Case 6: Verify Footer Visibility
            try {
                WebElement footer = driver.findElement(By.cssSelector("footer")); // Adjust selector as needed
                if (footer.isDisplayed()) {
                    System.out.println("✅ Test Passed: Footer is visible.");
                } else {
                    System.out.println("❌ Test Failed: Footer is NOT visible.");
                }
            } catch (Exception e) {
                System.out.println("❌ Test Failed: Exception occurred while checking footer visibility - " + e.getMessage());
            }
            // Test Case 7: Check for Console Errors
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript(
                    "var errors = [];" +
                    "var originalError = console.error;" +
                    "console.error = function(message) {" +
                    "   errors.push(message);" +
                    "   originalError.apply(console, arguments);" +
                    "};" +
                    "window.errors = errors;"
                );

                Thread.sleep(2000); // Wait for any potential errors to be logged
                List<?> consoleErrors = (List<?>) js.executeScript("return window.errors;");

                if (consoleErrors.isEmpty()) {
                    System.out.println("✅ Test Passed: No console errors found.");
                } else {
                    System.out.println("❌ Test Failed: Console errors found:");
                    for (Object error : consoleErrors) {
                        System.out.println("Error: " + error);
                    }
                }
            } catch (Exception e) {
                System.out.println("❌ Test Failed: Exception occurred while checking console errors - " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("❌ Test Failed: Exception occurred - " + e.getMessage());
        } finally {
            // Close browser
            driver.quit();
        }
    }
}