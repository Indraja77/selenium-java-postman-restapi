package base;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // STEP 1: Runs before every test
    @BeforeMethod
    public void openBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Open the Automation Exercise home page
        driver.get("https://automationexercise.com");
        removeAds();
    }

    // STEP 2: Runs after every test
    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }

    // Removes ad banners from the page
    protected void removeAds() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        String script = "document.querySelectorAll('iframe, ins.adsbygoogle')"
                + ".forEach(function(ad) { ad.remove(); });";

        js.executeScript(script);
    }

    // Takes a full-page screenshot and saves it in the "verification" folder
    protected void takeFullPageScreenshot(String fileName) throws Exception {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll to the bottom of the page
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        // Dynamic wait: wait until the footer is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("footer")));

        // Scroll back to the top
        js.executeScript("window.scrollTo(0, 0);");

        // Take the screenshot of the whole page body
        WebElement body = driver.findElement(By.tagName("body"));
        File screenshot = body.getScreenshotAs(OutputType.FILE);

        // Create the "verification" folder if it does not exist
        Files.createDirectories(Paths.get("verification"));

        // Save the screenshot as a .png file
        Files.copy(screenshot.toPath(),
                Paths.get("verification", fileName + ".png"),
                StandardCopyOption.REPLACE_EXISTING);
    }
}