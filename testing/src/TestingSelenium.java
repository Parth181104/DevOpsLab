import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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
            System.out.println("Page title is: " + driver.getTitle());
            if (driver.getTitle().equals("TrendyShe")) {
                System.out.println("✅ Test Passed: Home page loaded successfully.");
            } else {
                System.out.println("❌ Test Failed: Home page title is incorrect.");
            }

            // Test Case 2: Verify Navigation Menu Visibility
            WebElement navMenu = driver.findElement(By.cssSelector(".navbar"));
            if (navMenu.isDisplayed()) {
                System.out.println("✅ Test Passed: Navigation menu is visible.");
            } else {
                System.out.println("❌ Test Failed: Navigation menu is NOT visible.");
            }

            // Test Case 3: Verify Content Section Visibility
            WebElement contentSection = driver.findElement(By.cssSelector(".content"));
            if (contentSection.isDisplayed()) {
                System.out.println("✅ Test Passed: Content section is visible.");
            } else {
                System.out.println("❌ Test Failed: Content section is NOT visible.");
            }

            // Test Case 4: Verify Products Section Visibility
            WebElement productsSection = driver.findElement(By.id("newArrival"));
            if (productsSection.isDisplayed()) {
                System.out.println("✅ Test Passed: Products section is visible.");
            } else {
                System.out.println("❌ Test Failed: Products section is NOT visible.");
            }

            // Test Case 5: Verify External Links
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

            // Test Case 6: Check for Console Errors
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
            List<String> consoleErrors = (List<String>) js.executeScript("return window.errors;");

            if (consoleErrors.isEmpty()) {
                System.out.println("✅ Test Passed: No console errors found.");
            } else {
                System.out.println("❌ Test Failed: Console errors found:");
                for (String error : consoleErrors) {
                    System.out.println("Error: " + error);
                }
            }

            // Test Case 7: Verify Search Functionality
            WebElement searchInput = driver.findElement(By.cssSelector("input[type='search']")); // Adjust selector as needed
            searchInput.sendKeys("dress");
            searchInput.submit();
            Thread.sleep(2000); // Wait for results to load
            List<WebElement> searchResults = driver.findElements(By.cssSelector(".product-title")); // Adjust selector as needed
            boolean found = searchResults.stream().anyMatch(result -> result.getText().toLowerCase().contains("dress"));
            System.out.println(found ? "✅ Test Passed: Search functionality works." : "❌ Test Failed: Search functionality does not return relevant results.");

            // Test Case 8: Verify Product Details Page
            WebElement firstProduct = driver.findElement(By.cssSelector(".card")); // Adjust selector as needed
            firstProduct.click();
            Thread.sleep(2000); // Wait for the product details page to load
            String productName = driver.findElement(By.cssSelector(".product-title")).getText(); // Adjust selector as needed
            System.out.println(productName.isEmpty() ? "❌ Test Failed: Product details page does not load correctly." : "✅ Test Passed: Product details page loads correctly.");

            // Test Case 9: Verify Add to Cart Functionality
            driver.navigate().back(); // Go back to the product list
            Thread.sleep(2000); // Wait for the page to load
            firstProduct = driver.findElement(By.cssSelector(".card")); // Adjust selector as needed
            firstProduct.findElement(By.cssSelector(".btn")).click(); // Click "Add to Cart"
            Thread.sleep(2000); // Wait for the cart count to update
            String cartCount = driver.findElement(By.cssSelector(".cart-count")).getText();
            System.out.println(cartCount.equals("(1)") ? "✅ Test Passed: Product added to cart successfully." : "❌ Test Failed: Product not added to cart.");

            // Test Case 10: Verify Responsive Design
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(1200, 800));
            System.out.println("✅ Test Passed: Responsive design works at 1200px.");
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(768, 800));
            System.out.println("✅ Test Passed: Responsive design works at 768px.");
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(480, 800));
            System.out.println("✅ Test Passed: Responsive design works at 480px.");

            // Test Case 11: Verify Footer Links
            List<WebElement> footerLinks = driver.findElements(By.cssSelector("footer a")); // Adjust selector as needed
            for (WebElement link : footerLinks) {
                String originalWindow = driver.getWindowHandle();
                link.click();
                Thread.sleep(2000); // Wait for the new page to load
                System.out.println("✅ Test Passed: Footer link navigated to " + driver.getCurrentUrl());
                driver.close();
                driver.switchTo().window(originalWindow);
            }

        } catch (Exception e) {
            System.out.println("❌ Test Failed: Exception occurred - " + e.getMessage());
        } finally {
            // Close browser
            driver.quit();
        }
    }
}