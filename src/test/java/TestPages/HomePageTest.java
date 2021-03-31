package TestPages;

import Collections.HomePageCollection;
import Collections.LoginPageCollection;
import Pages.HomePage;
import Pages.LoginPage;
import org.junit.Test;
import utilities.Log4j;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HomePageTest extends BaseTest{

    protected void Login(){

        navigateUrl(LoginPageCollection.loginPageUrl);
        new LoginPage(driver).signIn(LoginPageCollection.username, LoginPageCollection.password);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void Control() throws IOException {
        navigateUrl(HomePageCollection.url);
        Log4j.info("Opening Page : " + HomePageCollection.url);
        new HomePage(driver).CheckHomePage();
        Login();
        new HomePage(driver).CheckLoggedIn();
        new HomePage(driver).CheckAll();
    }

}
