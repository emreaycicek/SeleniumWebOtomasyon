package Pages;

import Collections.HomePageCollection;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Log4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class HomePage {

    By loginName = By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div[1]/div[3]/div/div[1]/div/div[2]/span");
    By searchBar = By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[1]/div[2]/input");
    By findButton = By.xpath("//span[normalize-space()='BUL']");
    By secondPage = By.xpath("//*[@id=\"best-match-right\"]/div[5]/ul/li[2]/a");
    By rndProduct = By.xpath("//*[@id=\"best-match-right\"]/div[3]/div[2]/ul/li");
    By computerName = By.xpath("//*[@id=\"sp-title\"]");
    By computerPriceHigh = By.xpath("//span[@id='sp-price-highPrice']");
    By computerPriceLow = By.xpath("//div[@id='sp-price-lowPrice']");
    By addToBasket = By.xpath("//button[normalize-space()='Sepete Ekle']");
    By basketButton = By.xpath("//a[@class='header-cart-hidden-link']");
    By basketTotalPrice = By.xpath("//div[@class='total-price']");
    By productNumber = By.xpath("//select[@class='amount']");
    By productDeleteButton = By.xpath("//div[@class='gg-d-24 hidden-m update-buttons-container']//i[@class='gg-icon gg-icon-bin-medium']");
    By emptyBasketText = By.xpath("//h2[contains(text(),'Sepetinizde ürün bulunmamaktadır.')]");

    private WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    public void CheckHomePage(){

        Assert.assertEquals(driver.getCurrentUrl(), HomePageCollection.url);
        if(!driver.getCurrentUrl().equals(HomePageCollection.url)){
            Log4j.error("Mismatch HomePage Url");
        }
        Log4j.info("Check HomePage Url");
        Log4j.info("Current Url: "+ driver.getCurrentUrl() + " Test Url: "+ HomePageCollection.url);
    }
    public void CheckLoggedIn(){
        String name = driver.findElement(loginName).getText();
        Assert.assertEquals(name, HomePageCollection.loginUserName);

        if(!name.equals(HomePageCollection.loginUserName)){
            Log4j.error("Mismatch Login Username");
        }
        Log4j.info("Check Login Name");
        Log4j.info("Current Name: "+ name + " Test Name: "+ HomePageCollection.loginUserName);
    }

    public void CheckAll() throws IOException {
        driver.findElement(searchBar).sendKeys(HomePageCollection.searchWord);
        Log4j.info("Search bar word: "+ HomePageCollection.searchWord);
        driver.findElement(findButton).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(secondPage));

        driver.findElement(secondPage).click();
        Assert.assertEquals(driver.getCurrentUrl(),HomePageCollection.secondPageurl);
        if(!driver.getCurrentUrl().equals(HomePageCollection.secondPageurl)){
            Log4j.error("Mismatch Second Page Url");
        }
        Log4j.info("Check Second Page Url");
        Log4j.info("Current Url: "+ driver.getCurrentUrl() + " Test Url: " + HomePageCollection.secondPageurl);

        List<WebElement> allProducts = driver.findElements(rndProduct);
        Random rand = new Random();
        int randomProduct = rand.nextInt(allProducts.size());
        Log4j.info("Total Products: " + allProducts.size());
        Log4j.info("Random Product: " + randomProduct);
        allProducts.get(randomProduct).click();

        String TestFile = "newFile.txt";
        File FC = new File(TestFile);
        FC.createNewFile();

        FileWriter FW = new FileWriter(TestFile);
        BufferedWriter BW = new BufferedWriter(FW);
        String compNameStr = driver.findElement(computerName).getText();
        Log4j.info("Computer Name: " + compNameStr);
        String compPriceHighStr = driver.findElement(computerPriceHigh).getText();
        Log4j.info("Computer High Price: " + compPriceHighStr);
        String compPriceLowStr = driver.findElement(computerPriceLow).getText();
        Log4j.info("Computer Low Price: " + compPriceLowStr);
        BW.write(compNameStr);
        BW.newLine();

        if(compPriceLowStr.equals("")){
            BW.write(compPriceHighStr);
            BW.close();
            driver.findElement(addToBasket).click();
            driver.findElement(basketButton).click();
            String basketPrice = driver.findElement(basketTotalPrice).getText();
            Log4j.info("Basket Total Price: " + basketPrice);
            Assert.assertEquals(compPriceHighStr,basketPrice);
            if(!compPriceHighStr.equals(basketPrice)){
                Log4j.error("Mismatch Product and Basket Price");
            }
            Log4j.info("Check Product and Basket Price");
            Log4j.info("Product Price: "+ compPriceHighStr + " Basket Price: " + basketPrice);
        }else{
            BW.write(compPriceLowStr);
            BW.close();
            driver.findElement(addToBasket).click();
            driver.findElement(basketButton).click();
            String basketPrice = driver.findElement(basketTotalPrice).getText();
            Log4j.info("Basket Total Price: " + basketPrice);
            Assert.assertEquals(compPriceLowStr,basketPrice);
            if(!driver.getCurrentUrl().equals(HomePageCollection.secondPageurl)){
                Log4j.error("Mismatch Product and Basket Price");
            }
            Log4j.info("Check Product and Basket Price");
            Log4j.info("Product Price: "+ compPriceLowStr + " Basket Price: " + basketPrice);
        }


        Select prdctNumber = new Select(driver.findElement(productNumber));
        List<WebElement> productCount = prdctNumber.getOptions();
        Log4j.info("Product Stock Quantity: " + productCount.size());

        if(productCount.size() >= 2) {
            prdctNumber.selectByValue("2");
            String productNumberStr = prdctNumber.getFirstSelectedOption().getText();
            Log4j.info("Product Number : " + productNumberStr);
            Assert.assertEquals(productNumberStr, "2");

            if(!productNumberStr.equals("2")){
                Log4j.error("Mismatch Product Number");
            }
            Log4j.info("Check Product Number");
            Log4j.info("Current Product Number: "+ productNumberStr + " Test Product Number: "+ "2");
        }

        driver.findElement(productDeleteButton).click();
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(emptyBasketText));
        String emptyBasketStr = driver.findElement(emptyBasketText).getText();
        Log4j.info("Empty Basket Text : " + emptyBasketStr);
        Assert.assertEquals(emptyBasketStr,HomePageCollection.emptyBasketText);
        if(!emptyBasketStr.equals(HomePageCollection.emptyBasketText)){
            Log4j.error("Mismatch Empty Basket Text");
        }
        Log4j.info("Check Empty Basket Text");
        Log4j.info("Current Empty Basket Text: "+ emptyBasketStr + " Test Empty Basket Text: "+ HomePageCollection.emptyBasketText);
    }

}
