import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.*;  
import java.net.URL;
import java.net.MalformedURLException;
import java.io.File;

public class SeliniumTest {
    public WebDriver driver;
    
    @BeforeMethod
    public void setup()  throws MalformedURLException  {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
    }
    
    // login_form_test
    @Test
    public void testLogin() {

        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.login("uci.edu.test@proton.me", "uci.edu.test");

        MainPage mainPage = new MainPage(this.driver);
        Assert.assertTrue(mainPage.isLoggedIn());
    }

    // test_dependencies
    // logout 
    @Test (dependsOnMethods = {"testLogin"})
    public void testLogout() {
        // login
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.login("uci.edu.test@proton.me", "uci.edu.test");

        MainPage mainPage = new MainPage(this.driver);
        Assert.assertTrue(mainPage.isLoggedIn());

        // logout
        PageBase page=mainPage.logout();
        Assert.assertFalse(page.isLoggedIn());  
    }

    // static_page_test
    @Test
    public void staticPageTest() {
        MainPage mainPage = new MainPage(this.driver);
        String bodyText = mainPage.getBodyText();
        Assert.assertTrue(bodyText.contains("Welcome to the UC Irvine Machine Learning Repository"));
          
    }

    // multiple_page_test
    @Test
    public void multiple_page_test(){

        PageBase page = new PageBase(this.driver);
        // Define a map of URLs and their expected titles
        Map<String, String> pageExpectedTitles = new LinkedHashMap<>();
        pageExpectedTitles.put("https://archive.ics.uci.edu/datasets", "Browse Datasets");
        pageExpectedTitles.put("https://archive.ics.uci.edu/contribute/donation", "Donation Policy");
        pageExpectedTitles.put("https://archive.ics.uci.edu/about", "About");
        pageExpectedTitles.put("https://archive.ics.uci.edu/citation", "Citation");
        pageExpectedTitles.put("https://archive.ics.uci.edu/contact", "Contact");

        for (Map.Entry<String, String> entry : pageExpectedTitles.entrySet()) {
            String url = entry.getKey();
            String expectedText = entry.getValue();

            page.loadPage(url);

            String body = page.getBodyText();
        
            Assert.assertTrue(body.contains(expectedText));
        }
    }

    // page_title
    @Test
    public void testPageTitle() {
        MainPage mainPage = new MainPage(this.driver);
        String title = mainPage.getTitle();
        Assert.assertTrue(title.contains("UCI Machine Learning Repository"));
    }
    
    // form_with_user
    // file_upload
    @Test (dependsOnMethods = {"testLogin"})
    public void testFormAndFileUpload() {
        // login
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.login("uci.edu.test@proton.me", "uci.edu.test");
        // fill form and upload file
        FormsPage formsPage = new FormsPage(this.driver);

        // fill form uses thread sleep
        formsPage.fillForm(
            RandomTestData.randomDatasetName(), 
            RandomTestData.randomAbstract(), 
            RandomTestData.randomNumber(50), 
            RandomTestData.randomNumber(10)
        );
        formsPage.uploadFile(new File("src/test/resources/test.png").getAbsolutePath());

        // verify file upload
        Assert.assertTrue(formsPage.getBodyText().contains("test.png"));

        // submit form and verify next page
        PageBase page = formsPage.submitForm();
        Assert.assertTrue(page.getBodyText().contains("Introductory Paper"));
    }

    // download_file
    @Test
    public void testDownLoad(){
        DownloadPage downloadPage = new DownloadPage(this.driver);
        try {
            downloadPage.downloadFile();
            String projectRoot = System.getProperty("user.dir");
            File file = new File(projectRoot + "/src/test/resources/iris.zip");
            Assert.assertTrue(file.exists());
            Assert.assertTrue( file.length() > 0);

            // Clean up
            file.delete();

        } catch (Exception e) {
            Assert.fail("Download failed: " + e.getMessage());
        }
    }

    @Test
    public void testMouseHover() {
        MainPage mainPage = new MainPage(this.driver);
        mainPage.mouseHover();
        Assert.assertTrue(mainPage.getBodyText().contains("Donate New"));
    }
    
    @AfterMethod
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
