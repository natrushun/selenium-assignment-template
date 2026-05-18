import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;


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
    

    /* A short sleep is added after each field without the delay, some fields are occasionally
       skipped due to an unknown timing issue.
    */
    public void fillForm(String datasetName, String abstractText, int rows, int features) {
        //intisal pause to ensure page is fully loaded before interacting with elements
        sleep(2000);

        sendKeysWithPause(datasetNameBy, datasetName);
        sendKeysWithPause(abstractBy, abstractText);
        sendKeysWithPause(rowsBy, String.valueOf(rows));
        sendKeysWithPause(featuresBy, String.valueOf(features));
        jsClick(characteristicsBy);
        jsClick(areaBy);
        jsClick(taskBy);
        jsClick(typeBy);
        
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
        sleep(1000);
        WebElement element = this.waitAndReturnElement(by);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private void sendKeysWithPause(By by, String text) {
        sleep(1000);
        waitAndReturnElement(by).sendKeys(text);
    }
 
    private void sleep(int ms) {
    try {
        Thread.sleep(ms);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
   
}
