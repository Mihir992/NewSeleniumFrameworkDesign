package SeleniumFramework;
import java.time.Duration;
import static org.junit.Assert.*;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.aventstack.extentreports.util.Assert;

import SeleniumFramework.PageObjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class EndToEndTest {
	String productName = "ZARA COAT 3";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		WebDriverManager.chromedriver().setup();
		
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://rahulshettyacademy.com/client/");
		driver.manage().window().maximize();
		LandingPage lp = new LandingPage(driver);
		driver.findElement(By.id("userEmail")).sendKeys("kartikshah@yopmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Admin@123");
		driver.findElement(By.id("login")).click();
		
		List<WebElement> products = driver.findElements(By.className("mb-3"));
		WebElement prod = products.stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst().orElse(null);
	    prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();     
	    
	    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
	    
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
	    driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
	    
	   /* List <WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
	    Boolean match = cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
	    Assert.assertTrue(match);*/
	    
	    driver.findElement(By.cssSelector(".totalRow button")).click();
	    //Assert.assertTrue(match);
	    
	    Actions a = new Actions(driver);
	    a.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")), "india").build().perform();
	    
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	    
	    driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();
	    
	    driver.findElement(By.cssSelector(".action__submit")).click();
	    
	    String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
	    confirmMessage.equals("Thankyou for the order") ;
	    
	    //driver.close();
	}

}
