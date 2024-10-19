package SeleniumFramework.PageObjects;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumFramework.AbstractComponent.AbstractComponent;

public class ProductCatalougue extends AbstractComponent{

	WebDriver driver;
	
	public ProductCatalougue(WebDriver driver) {
			super(driver);
			this.driver=driver;
			PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="mb-3")
	List<WebElement> products; 
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	By productsBy = By.className("mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");
	
	
	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productsBy);
		List<WebElement> products = driver.findElements(productsBy);
		System.out.println("Total products found: " + products.size()); // Log total products found
		for (WebElement product : products) {
			// Log the entire product HTML for debugging
			//System.out.println("Product HTML: " + product.getAttribute("outerHTML"));

			String productText = product.findElement(By.cssSelector("b")).getText().trim().toLowerCase();
			System.out.println("Checking product: " + productText);  // Log product being checked
		}
		return products; // Return the list of products
	}

	public WebElement getProductByName(String productName) {
		// Retrieve the product list only once
		List<WebElement> products = getProductList();

		return products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().trim().equalsIgnoreCase(productName))
				.findFirst()
				.orElseThrow(() -> new NoSuchElementException("Product with name '" + productName + "' not found."));
	}

	//TO add single product into cart
	public void addProductToCart(String productName) {
		WebElement prod = getProductByName(productName);

		// Check if prod is null before attempting to find addToCart button
		if (prod != null) {
			prod.findElement(addToCart).click();
			waitForElementToAppear(toastMessage);
			waitForElementToDisappear(spinner);
		} else {
			// Handle the case where the product is not found
			throw new NoSuchElementException("Product with name '" + productName + "' not found.");
		}
	}

	// Method to add multiple products to the cart
	public void addProductsToCart(String... productNames) {
		for (String productName : productNames) {
			try {
				addProductToCart(productName);
			} catch (NoSuchElementException e) {
				System.out.println("Warning: " + e.getMessage());
				// Optionally log the error if needed
			}
		}
	}
}
