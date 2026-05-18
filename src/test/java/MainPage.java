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


class MainPage extends PageBase {

    private By footerBy = By.id("footer");
    private By searchBarTogglerBy = By.id("search-box-toggle");
    private By searchBarBy = By.xpath("//div[@id='search-box']//input[@type='search']");

     public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://archive.ics.uci.edu/");
    }    
    
}
