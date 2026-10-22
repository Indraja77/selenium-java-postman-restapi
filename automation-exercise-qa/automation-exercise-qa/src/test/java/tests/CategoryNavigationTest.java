package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class CategoryNavigationTest extends BaseTest {

    @Test
    public void verifyDressCategoryPageLoads() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Open the Women menu
        WebElement women = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("a[href='#Women']")));
        js.executeScript("arguments[0].click();", women);

        // Click the Dress sub-category
        WebElement dress = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("a[href='/category_products/1']")));
        js.executeScript("arguments[0].click();", dress);

        // Assert the destination page loaded correctly
        wait.until(ExpectedConditions.urlContains("/category_products/1"));
        WebElement heading = driver.findElement(By.cssSelector(".features_items h2.title"));
        Assert.assertTrue(heading.getText().toUpperCase().contains("DRESS"),
                "Wrong page. Heading: " + heading.getText());

        // Screenshot
        takeFullPageScreenshot("02_category_page");
    }
}