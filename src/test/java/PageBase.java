import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;


class PageBase {

    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public PageBase(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }
    
    public void loadPage(String url) {
        this.driver.get(url);
    }

    
    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    protected WebElement waitAndReturnHiddenElement(By by) {
        return this.wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
    
    public String getBodyText() {
        WebElement bodyElement = this.waitAndReturnElement(By.tagName("body"));
        return bodyElement.getText();
    }

    public String getTitle() {
        return this.driver.getTitle();
    }

    public Boolean isLoggedIn() {
        try {
            By profileButtonBy = By.xpath("//div[@aria-label='Account actions']");
            this.waitAndReturnElement(profileButtonBy);
            return true;
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }
    public void setPageZoom(int zoomPercentage) {
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='" + zoomPercentage + "%'");
    }

    public PageBase logout() {
        By profileButtonBy = By.xpath("//div[@aria-label='Account actions']");
        this.waitAndReturnElement(profileButtonBy).click();
        By logoutButtonBy = By.xpath("//button[span='Logout']");
        this.waitAndReturnElement(logoutButtonBy).click();
        return this;
    }

    

   
}
