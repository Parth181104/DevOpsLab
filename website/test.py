from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import time

# Set up WebDriver (Use path to your ChromeDriver)
driver = webdriver.Chrome(executable_path="chromedriver.exe")

# Open the registration page
driver.get("http://localhost/register.php")  # Change URL as needed
time.sleep(2)  # Wait for page to load

# Fill out the registration form
driver.find_element(By.ID, "FirstName").send_keys("Mahima")
driver.find_element(By.ID, "lastName").send_keys("Kela")
driver.find_element(By.ID, "email").send_keys("test@example.com")
driver.find_element(By.ID, "password").send_keys("Test@1234")
driver.find_element(By.ID, "confirm_password").send_keys("Test@1234")

# Click the Sign-Up button
driver.find_element(By.CLASS_NAME, "btn").click()
time.sleep(3)

# Verify successful registration
if "Thank you for signing up" in driver.page_source:
    print("✅ Test Passed: Registration Successful")
else:
    print("❌ Test Failed")

# Close the browser
driver.quit()
