import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;


class MainPage extends PageBase {

    private By mouseHoverBy = By.xpath("//a[contains(text(), 'Contribute Dataset')]/parent::li");
     public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://archive.ics.uci.edu/");
    }    
    public void mouseHover() {
        WebElement hoverable = this.waitAndReturnElement(mouseHoverBy);
        new Actions(driver).moveToElement(hoverable).perform();
    }
}
