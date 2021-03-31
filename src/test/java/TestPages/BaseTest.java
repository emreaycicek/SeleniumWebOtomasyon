package TestPages;

import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utilities.Log4j;

public class BaseTest {

    protected WebDriver driver;

    public void navigateUrl(String url) {

        driver.get(url);
    }

    @Before
    public void setUp() {
        //setProperty();
        Log4j.startLog("Test  is Starting");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        Log4j.endLog("Test is Ending");
        driver.quit();
    }



}


