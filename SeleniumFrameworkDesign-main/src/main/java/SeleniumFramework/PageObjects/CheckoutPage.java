package SeleniumFramework.PageObjects;

import SeleniumFramework.AbstractComponent.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckoutPage extends AbstractComponent {

    WebDriver driver;
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="[placeholder='Select Country']")
    WebElement country;

    @FindBy(xpath="//a[@class='btnn action__submit ng-star-inserted']")
    WebElement submit;

    //@FindBy(xpath="//button[contains(@class,'ta-item')])[2]")
    //WebElement selectCountry;

    @FindBy(xpath="//button[@type='button'][2]")
    WebElement selectItems;

    By results = By.cssSelector(".ta-results");

    public void setSelectCountry(String countryName){
        country.sendKeys(countryName);
        // Wait for the autosuggestion results to appear
        waitForElementToAppear(results);
        selectItems.click();
    }

    public ConfirmationPage submitOrder(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait for the button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(submit));

        // Use JavaScript to scroll the button into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submit);

        // Wait until the button is fully visible (optional)
        wait.until(ExpectedConditions.visibilityOf(submit));

        // Now click the button
        // Click the button using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);

        return new ConfirmationPage(driver);
    }
}
