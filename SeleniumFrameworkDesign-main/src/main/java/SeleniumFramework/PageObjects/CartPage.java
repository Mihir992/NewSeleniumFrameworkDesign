package SeleniumFramework.PageObjects;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import SeleniumFramework.AbstractComponent.AbstractComponent;

public class CartPage extends AbstractComponent{

	WebDriver driver;
	
	public CartPage(WebDriver driver) {
			super(driver);
			this.driver=driver;
			PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".totalRow button")
	WebElement checkoutEle; 
	
	@FindBy(css=".cartSection h3")
	List<WebElement> productTitles;

	@FindBy(xpath = "//div[@class='prodTotal cartSection']")
	List<WebElement> prodTotal;

	@FindBy(xpath = "//li[@class='totalRow']/span[@class='value'][1]")
	WebElement subTotal;


	By productsBy = By.className("mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");
	
	
	public Boolean verifyProductDisplay(String productName)
	{
		Boolean match = productTitles.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
	    return match;
	}
	
	public CheckoutPage goToCheckout()
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// Wait for the button to be clickable
		wait.until(ExpectedConditions.elementToBeClickable(checkoutEle));

		// Use JavaScript to scroll the button into view
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkoutEle);

		// Wait until the button is fully visible (optional)
		wait.until(ExpectedConditions.visibilityOf(checkoutEle));

		// Now click the button
		// Click the button using JavaScript
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutEle);
		//checkoutEle.click();
		return new CheckoutPage(driver);
	}

	public void deleteProductFromCart(String productName){
		for (int i = 0; i < productTitles.size(); i++) {
			WebElement product = productTitles.get(i);
			if(product.getText().equalsIgnoreCase(productName)) {
				// Assuming the trash button is within the same parent or accessible
				WebElement correspondingTrashButton = driver.findElements(By.xpath("//i[@class='fa fa-trash-o']")).get(i);
				// Click the trash button
				correspondingTrashButton.click();
				System.out.println("Product removed from cart: " + productName);
				return; // Exit after deleting the product
			}
			System.out.println("Product not found in cart: " + productName);
		}
	}

	public void calculateSubTotalAndTotal() {
		double subtotal = 0.0;

		// Iterate through the product total prices
		for (WebElement productTotalPrice : prodTotal) {
			String priceText = productTotalPrice.getText().replace("$", "").trim(); // Assuming the price is in the format $XX.XX
			double price = Double.parseDouble(priceText);
			subtotal += price;
			System.out.println("Product total price added: " + price);
		}

		// Set the subtotal in the WebElement
		String subTotalText = subTotal.getText().replace("$", "").trim();
		double displayedSubTotal = Double.parseDouble(subTotalText);

		// Print results
		System.out.println("Calculated Subtotal: " + subtotal);
		System.out.println("Displayed Subtotal: " + displayedSubTotal);

		// You can also calculate total if you have the logic for it
		// For example, if you have a tax rate or shipping fee
		double total = subtotal; // Modify this line if there are additional charges
		System.out.println("Total: " + total);
	}

}
