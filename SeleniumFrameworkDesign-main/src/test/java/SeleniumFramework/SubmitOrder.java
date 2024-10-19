package SeleniumFramework;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import SeleniumFramework.PageObjects.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.Test;

public class SubmitOrder extends BaseTest {

	static String productName = "ZARA COAT 3";
	static String productName1 = "ADIDAS ORIGINAL";

	@Test(priority=1,description="User placed order successfully")
	public void submitOrder() throws IOException {
		LandingPage landingPage = launchApplication();
		ProductCatalougue productCatalougue = landingPage.loginApplication
				("testuser01@yopmail.com","Test@123");
		List<WebElement> products = productCatalougue.getProductList();
		productCatalougue.addProductToCart(productName);
		productCatalougue.goToCartPage();
		CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.setSelectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		confirmationPage.verifyConfirmationMessage();
	    driver.close();
	}

	@Test(priority=2,description="Check number of order line items after deletion of product " +
			"from cart page in Order Page")
	public void verifyNumberofItemsAfterDeletionInOrderPage() throws IOException {
		LandingPage landingPage = launchApplication();
		ProductCatalougue productCatalougue = landingPage.loginApplication
				("testuser01@yopmail.com","Test@123");
		List<WebElement> products = productCatalougue.getProductList();
		productCatalougue.addProductsToCart(productName,productName1);
		productCatalougue.goToCartPage();
		CartPage cartPage = new CartPage(driver);
		//Boolean match = cartPage.verifyProductDisplay(productName);
		//Assert.assertTrue(match);
		cartPage.deleteProductFromCart(productName);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.setSelectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		confirmationPage.verifyConfirmationMessage();
		confirmationPage.verifyNumberOfItemsOrderPage();
		driver.close();
		 //driver clsoe ////QA AAAAAAAAAAAAAAAAAA
	}
}
