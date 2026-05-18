import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.By;
import java.net.URL;
import java.util.stream.Collectors;

class DownloadPage extends PageBase {

    private By downloadBy = By.xpath("//a[contains(@href, 'iris.zip')]");

     public DownloadPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://archive.ics.uci.edu/dataset/53/iris");
     }    
    
    public void downloadFile() throws Exception {
        WebElement downloadLink = this.waitAndReturnElement(downloadBy);
        String url = downloadLink.getAttribute("href");

        String cookies = driver.manage().getCookies()
        .stream()
        .map(c -> c.getName() + "=" + c.getValue())
        .collect(Collectors.joining("; "));


        ProcessBuilder pb = new ProcessBuilder(
            "curl", "-L", "-o", "/home/selenium/tests/task3/tmp/iris.zip",
            "-b", cookies, url
        );
        Process process = pb.start();
        int exitCode = process.waitFor();
        
        if (exitCode != 0) {
            throw new Exception("curl failed, exit code: " + exitCode);
        }
    }



}
