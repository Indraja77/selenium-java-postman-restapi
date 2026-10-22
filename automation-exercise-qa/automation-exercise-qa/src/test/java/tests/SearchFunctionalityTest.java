package tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class SearchFunctionalityTest extends BaseTest {

    @Test
    public void verifySearchResultsContainSearchTerm() throws Exception {
        String searchTerm = "Jeans";

        // Go to the Products page and search
        driver.get("https://automationexercise.com/products");
        driver.findElement(By.id("search_product")).sendKeys(searchTerm);
        WebElement searchButton = driver.findElement(By.id("submit_search"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);

        // Assert the results page is displayed
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".features_items h2.title")));
        Assert.assertTrue(heading.getText().toUpperCase().contains("SEARCHED PRODUCTS"),
                "Results page not shown. Heading: " + heading.getText());

        // Assert every result contains the search term
        List<WebElement> products = driver.findElements(By.cssSelector(".features_items .productinfo p"));
        Assert.assertTrue(products.size() > 0, "No products found");
        for (WebElement product : products) {
            Assert.assertTrue(product.getText().toLowerCase().contains(searchTerm.toLowerCase()),
                    "Does not match: " + product.getText());
        }

        // Screenshot
        takeFullPageScreenshot("03_search_results");
    }
}