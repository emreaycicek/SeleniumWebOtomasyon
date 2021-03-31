package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.Log4j;

public class LoginPage {
    By userName = By.xpath("//input[@id='L-UserNameField']");
    By password = By.xpath("//input[@id='L-PasswordField']");
    By btnSave = By.xpath("//input[@id='gg-login-enter']");

    private WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public void signIn(String _username,String _password){
        driver.findElement(userName).sendKeys(_username);
        Log4j.info("User Name :" + _username);
        driver.findElement(password).sendKeys(_password);
        Log4j.info("Password :" + _password);
        driver.findElement(btnSave).click();

    }
}
