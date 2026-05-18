import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;



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
