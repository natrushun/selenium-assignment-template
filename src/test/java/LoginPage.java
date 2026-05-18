import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;


class LoginPage extends PageBase {

    private By usernameBy = By.xpath("//input[@type='text']");
    private By passwordBy = By.xpath("//input[@type='password']");
    private By loginButtonBy = By.xpath("//button[span='Submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://archive.ics.uci.edu/auth/login");
    }    
    
    public PageBase login(String email, String password) {
        this.waitAndReturnElement(usernameBy).sendKeys(email);
        this.waitAndReturnElement(passwordBy).sendKeys(password);
        this.waitAndReturnElement(loginButtonBy).click();
        return new PageBase(this.driver);
    }
}
