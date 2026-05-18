import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.*;  

import java.net.URL;
import java.net.MalformedURLException;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.Random;
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
        // login
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
    
    @Test
    public void testFormAndFileUpload() {
        // login
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.login("uci.edu.test@proton.me", "uci.edu.test");

        FormsPage formsPage = new FormsPage(this.driver);
        formsPage.fillForm(
            RandomTestData.randomDatasetName(), 
            RandomTestData.randomAbstract(), 
            RandomTestData.randomRows(), 
            RandomTestData.randomFeatures()
        );
        formsPage.uploadFile(new File("src/test/resources/test.png").getAbsolutePath());
        Assert.assertTrue(formsPage.getBodyText().contains("test.png"));
        PageBase page = formsPage.submitForm();
        
        Assert.assertTrue(page.getBodyText().contains("Introductory Paper"));

    }


    @Test
    public void testDownLoad(){
        DownloadPage downloadPage = new DownloadPage(this.driver);
        try {
            downloadPage.downloadFile();
            File file = new File("/home/selenium/tests/task3/tmp/iris.zip");
            Assert.assertTrue(file.exists());
            Assert.assertTrue( file.length() > 0);

            file.delete();

        } catch (Exception e) {
            Assert.fail("Letöltés sikertelen: " + e.getMessage());
        }
    }
    
    /*@Test
    public void testSearch() {
        MainPage mainPage = new MainPage(this.driver);
        Assert.assertTrue(mainPage.getFooterText().contains("Eötvös Loránd University"));

        SearchResultPage searchResultPage = mainPage.search("Student guide 2025");
        String bodyText = searchResultPage.getBodyText();
        Assert.assertTrue(bodyText.contains("Searched content"));
        Assert.assertTrue(bodyText.contains("Student guide 2025/26"));
    }

    @Test
    public void testSearch2() {
        String[] searchQueries={"something","asd","xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"};
        for(String searchQuery : searchQueries) {
            MainPage mainPage = new MainPage(this.driver);
            SearchResultPage searchResultPage = mainPage.search(searchQuery);
            String bodyText = searchResultPage.getBodyText();
            Assert.assertTrue(bodyText.contains("Searched content"));
        } */
    

    @AfterMethod
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
