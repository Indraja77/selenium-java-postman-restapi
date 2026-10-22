package tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class HomePageVerificationTest extends BaseTest {

    @Test
    public void verifyHomePageNavigationElements() throws Exception {
        // Assert the home page loaded
        Assert.assertTrue(driver.getTitle().contains("Automation Exercise"),
                "Home page did not load. Title: " + driver.getTitle());

        // Assert key navigation elements are present
        String[] menuItems = {"Home", "Products", "Cart", "Signup / Login", "Contact us"};
        for (String item : menuItems) {
            List<WebElement> links = driver.findElements(By.partialLinkText(item));
            Assert.assertTrue(links.size() > 0, "Menu not found: " + item);
        }

        // Full-page screenshot
        takeFullPageScreenshot("01_home_page");
    }
}