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


class FormsPage extends PageBase {

    private By datasetNameBy = By.xpath("//input[@type='text' and @name='Name']");
    private By abstractBy = By.xpath("//textarea[@name='Abstract']");
    private By rowsBy = By.xpath("//input[@type='number' and @name='NumInstances']");
    private By featuresBy = By.xpath("//input[@type='number' and @name='NumFeatures']");
    private By fileuploadBy = By.xpath("//input[@type='file' and @name='Graphics']");
    private By characteristicsBy = By.xpath("//input[@type='checkbox'and @value='Tabular']");
    private By areaBy = By.xpath("//input[@type='radio'and @value='Biology']");
    private By taskBy = By.xpath("//input[@type='checkbox'and @value='Classification']");
    private By typeBy = By.xpath("//input[@type='checkbox'and @value='Real']");
    private By submitBy = By.xpath("//button[@type='submit'and text()='Next']");
    private By nextPageBy = By.xpath("//*[contains(text(), 'Introductory Paper')]");


    public FormsPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://archive.ics.uci.edu/contribute/donation/metadata");
    }    
    
    public void fillForm(String datasetName, String abstractText, int rows, int features) {
        
        sleep(2000);
        this.waitAndReturnElement(datasetNameBy).sendKeys(datasetName);
        sleep(1000);
        this.waitAndReturnElement(abstractBy).sendKeys(abstractText);
        sleep(1000);
        this.waitAndReturnElement(rowsBy).sendKeys(String.valueOf(rows));
        sleep(1000);
        this.waitAndReturnElement(featuresBy).sendKeys(String.valueOf(features));
        sleep(1000);
        jsClick(characteristicsBy);
        sleep(1000);
        jsClick(areaBy);
        sleep(1000);
        jsClick(taskBy);
        sleep(1000);
        jsClick(typeBy);
        sleep(1000);
        
    }
    public void uploadFile(String filePath) {
        WebElement fileUploadElement = this.waitAndReturnHiddenElement(fileuploadBy);
        fileUploadElement.sendKeys(filePath);
    }

    public PageBase submitForm() {
        this.waitAndReturnElement(submitBy).click();
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(nextPageBy));
        return new PageBase(this.driver);
    }

    private void jsClick(By by) {
        WebElement element = this.waitAndReturnElement(by);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    private void sleep(int ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
   
}
