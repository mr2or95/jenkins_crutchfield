/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.com;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertFalse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author Z_Wong 25
 */
public class StepDefsTest {
    private static WebDriver driver;
    
    //@Given("^(?:member|user|consumer) (?:logs|navigates|is) [\\w]{3,8} the (?:web)?site (.*.com) with (.*@*) username and (.*[0-9A-Za-z]+) password$")
    @Given("^(?:member|user|consumer) (?:logs|navigates|is) [\\w]{3,8} the (?:web)?site with username and password$")
	//public void memberAccessesExistingCrutchfieldAccount(String baseUrl, String userName, String passWord) throws Throwable {
    public void memberAccessesExistingCrutchfieldAccount(DataTable testData) throws Throwable {
	Map<String, String> loginMap = testData.asMap(String.class,String.class);
	System.setProperty("webdriver.chrome.driver", "E://drivers//chromedriver.exe");
	driver = new ChromeDriver();
		
        driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	System.out.println(loginMap.toString());
		
	//driver.get(baseUrl);
	driver.get(loginMap.get("website"));
        // click on the go to my account logo
        driver.findElement(By.xpath(".//*[@id=\"open-my-account\"]/div")).click();
        // click on the sign in button
        driver.findElement(By.linkText("Sign in")).click();
        Thread.sleep(5000);
        // enter the email address
        driver.findElement(By.id("signInEmailAddress")).click();
        //driver.findElement(By.id("signInEmailAddress")).sendKeys(userName);
        driver.findElement(By.id("signInEmailAddress")).sendKeys(loginMap.get("login"));
        // enter the password
        driver.findElement(By.id("signInPassword")).click();
        //driver.findElement(By.id("signInPassword")).sendKeys(passWord);
        driver.findElement(By.id("signInPassword")).sendKeys(loginMap.get("passw"));
        
	}

	@Given("^successfully (?:logs|accesses|gets) [\\w]{2,}$")
	public void memberIsOnAccountHomePage() throws Throwable {
		
		// finally click on the sign in submit button
                Thread.sleep(4000);
                driver.findElement(By.id("submitSignIn")).click();
	}

	@When("^(?:member|user|consumer) (?:questions|checks|looks) to [\\w]{3,} [\\w]{2,} reward points are (?:present|available|accessible)$")
	public void memberRequestsCurrentRewardPoints() throws Throwable {
	    
		// make sure the account has reward points
                String points = driver.findElement(By.xpath(".//div[@class='row top-cards pb-4']/div[3]/div[2]/div/div/span")).getText();
        
                // Assert to make sure it is not zero points
                assertFalse("There are zero reward points for this account",points.equals("0"));
        
                // Print out points amount in the output
                System.out.println("The reward points are " + driver.findElement(By.xpath(".//div[@class='row top-cards pb-4']/div[3]/div[2]/div/div/span")).getText());
	}

	@When("^(?:member|user|consumer) (?:picks|chooses|selects) an (?:desirable|available|present) reward redemption$")
	public void memberRedeemsPointsForReward() throws Throwable {
	    
		// Access the rewards tab
		driver.findElement(By.xpath(".//*[@id=\"rewardsTabHeading\"]/h5/button")).click();
                Thread.sleep(2000);
        
                //click on rewards dropdown control
                driver.findElement(By.id("pointsRedemptionDropDown")).click();
                // select a choice in the redemption dropdown
                //Select reward = new Select(driver.findElement(By.id("pointsRedemptionDropDown"))).selectByIndex(2);
                Select reward = new Select(driver.findElement(By.id("pointsRedemptionDropDown")));
                reward.selectByIndex(2);
        
                // Momentarily review the reward chosen
                Thread.sleep(3000);
                // Print out redemption selection
                WebElement option = reward.getFirstSelectedOption(); 
                System.out.println("The reward chosen by member is " + option.getText());
	}

	@Then("^(?:member|user|consumer) is (?:rewarded|redeemed|given) the reward [\\w]{3,8} (?:submitted|requested|delivered)$")
	public void memberProvidedRewardAtSubmittal() throws Throwable {
		// Really just cancel the request for this test
		driver.findElement(By.linkText("Cancel")).click();
        
                // log out
                Thread.sleep(3000);
                driver.findElement(By.xpath(".//*[@id=\"myAccountContainer\"]/div[3]/div[2]/p[4]/a")).click();
        
                driver.close();
	}

    
}
