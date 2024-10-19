package SeleniumFramework.PageObjects;

import SeleniumFramework.AbstractComponent.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class ConfirmationPage extends AbstractComponent {

    WebDriver driver;
    // Expected confirmation message
    private static final String EXPECTED_CONFIRMATION_MESSAGE = "THANKYOU FOR THE ORDER.";

    public ConfirmationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css=".hero-primary")
    WebElement confirmationMessage;

    public String getConfirmationMessage(){
        return confirmationMessage.getText();
    }

    public void verifyConfirmationMessage() {
        String confirmMessage = getConfirmationMessage();
        Assert.assertEquals(confirmMessage, EXPECTED_CONFIRMATION_MESSAGE, "Confirmation message does not match expected.");
    }

    public void verifyNumberOfItemsOrderPage(){
        List<WebElement> numberOfItems = driver.findElements(By.xpath("//table[@class='order-summary']//tr[@class='line-item ng-star-inserted']"));
        int lineItemCount = numberOfItems.size();
        System.out.println("Total Number of Item in Order Page:"+lineItemCount);
    }
}
