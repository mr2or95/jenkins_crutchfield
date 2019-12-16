/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.com;

import java.util.concurrent.TimeUnit;
import static jdk.nashorn.internal.objects.NativeObject.keys;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Z_Wong 25
 */
public class CrutchfieldTest {
 private WebDriver driver;
    private String baseUrl;
    private String email_address;
    private String passw;
    
    public CrutchfieldTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    System.setProperty("webdriver.chrome.driver", "E:\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        baseUrl = new String("https://www.crutchfield.com/");
        email_address = new String("mr2or95@comcast.net");
        passw = new String("Yes1Heard");
    }
    
    @After
    public void tearDown() {
       // driver.quit();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // Product Management Tests
    //@Test
    public void testSpeakerSearch() throws Exception {
        driver.manage().window().maximize();
        driver.get(baseUrl);
        //driver.findElement(By.linkText("Shop all departments")).click();
        
        // search home audio
        driver.findElement(By.linkText("Home audio")).click();
        
        // specify home speakers
        driver.findElement(By.linkText("Home speakers")).click();
        
        // look at bookshelf speakers
        driver.findElement(By.linkText("Bookshelf speakers")).click();
        
        // show more brands on the filter panel
        driver.findElement(By.xpath(".//div[@id='accordionExample']/div/div[2]/div/div[3]/a/span")).click();
        // filter on Martin logan and JBL speakers
        //driver.findElement(By.id("Brand14")).click();
        //driver.findElement(By.id("Brand10")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath(".//*[contains(text(),'MartinLogan')]")).click();
        driver.findElement(By.xpath(".//*[contains(text(),'LX16')]")).click();
                
        Assert.assertTrue("The selected speakers do not match the tested ones",driver.findElement(By.xpath(".//div[@class='masterPageContent']/div/div/div/div/div[2]/p")).getText().matches("^Item # 839MLX16GB$"));
    }

    //@Test
    public void testSearchBox() throws Exception {
        String resultSearch;
        
        driver.manage().window().maximize();
        driver.get(baseUrl);
        Thread.sleep(4000);
        
        // get focus on the search textbox
        driver.findElement(By.xpath(".//div[@class='logo-search-row']/div[2]/form/div/div/input")).click();
        driver.findElement(By.xpath(".//div[@class='logo-search-row']/div[2]/form/div/div/input")).sendKeys("wireless routers");
        driver.findElement(By.xpath(".//div[@class='logo-search-row']/div[2]/form/div/div/input")).sendKeys(Keys.RETURN);
        
        //driver.findElement(By.xpath("./html/body/header/div[1]/div[1]/section/div/div[2]/div[2]/form/div/button/svg")).click();
        
        resultSearch = (driver.findElement(By.xpath(".//div[@class='search-page-wrapper']/section/div/div[2]/p/span")).getText());
        System.out.println("This is the results number " + resultSearch);
        
        // check that we have returned more than zero items
        assertFalse("No items returned from search",resultSearch.equals("Dang."));
        
        // try searching for item that should not be in the inventory
        driver.findElement(By.xpath(".//div[@class='logo-search-row']/div[2]/form/div/div/input")).click();
        driver.findElement(By.xpath(".//div[@class='logo-search-row']/div[2]/form/div/div/input")).clear();
        driver.findElement(By.xpath(".//div[@class='logo-search-row']/div[2]/form/div/div/input")).sendKeys("aspirin");
        driver.findElement(By.xpath(".//div[@class='logo-search-row']/div[2]/form/div/div/input")).sendKeys(Keys.RETURN);
        
        // Dang will appear if nothing is found
        resultSearch = (driver.findElement(By.xpath(".//div[@id='NoResultsDiv']/div/h1")).getText());
        System.out.println("This is the results number " + resultSearch);
        // we shouldn't get anything
        assertTrue("We found items in a case where we shouldn't",resultSearch.equals("Dang."));
    }
    
    //@Test
    public void testProductCompare() throws Exception {
        String resultSearch;
        
        driver.manage().window().maximize();
        driver.get(baseUrl);
        Thread.sleep(4000);
        
        // select home audio
        driver.findElement(By.linkText("Home audio")).click();
        driver.findElement(By.linkText("Powered subwoofers")).click();
        
        Actions action = new Actions(driver);
        
        // filter products by price range - min and max range
        action.doubleClick(driver.findElement(By.xpath(".//div[@class='card-body']/div/input"))).perform();
        driver.findElement(By.xpath(".//div[@class='card-body']/div/input")).sendKeys("150");
        driver.findElement(By.xpath(".//div[@class='card-body']/div/input")).sendKeys(Keys.RETURN);
        
        Thread.sleep(2000);
        action.doubleClick(driver.findElement(By.xpath(".//div[@class='card-body']/div/input[2]"))).perform();
        driver.findElement(By.xpath(".//div[@class='card-body']/div/input[2]")).sendKeys("2490");
        driver.findElement(By.xpath(".//div[@class='card-body']/div/input[2]")).sendKeys(Keys.RETURN);
        
        // compare the first three subwoofers
        driver.findElement(By.id("701S810SWW")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("107PSW110B")).click();
        // move the compare window out of the way
        driver.findElement(By.xpath(".//div[@id='productCompare']/div/div/div/div/a")).click();
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        
        Thread.sleep(3000);
        // Choose the 3rd subwoofer in the comparison
        driver.findElement(By.id("701S808SWW")).click();
        
        Thread.sleep(3000);
        driver.findElement(By.xpath(".//div[@id='productCompare']/div/div/div/div/a")).click();
         // click on compare button
        Thread.sleep(3000);
        driver.findElement(By.xpath(".//div[@id='productCompare']/div/div/div[2]/div[2]/div/a")).click();
        
        // verify we are on comparison page
        assertEquals("Powered Subwoofers Comparison",
                driver.findElement(By.xpath(".//div[@id='compareDetailsTab']/div/div/div//h1/span")).getText());
    
        // checkbox to hide similiar features of products
        driver.findElement(By.id("aSimilar")).click();
        
        // remove one of the items
        driver.findElement(By.xpath(".//*[@id='comparetable']/tbody/tr[27]/td[3]/div/a")).click();
       
    }
    
    //@Test
    public void testGiftCard() throws Exception {
        String resultSearch;
        
        driver.manage().window().maximize();
        driver.get(baseUrl);
        Thread.sleep(4000);
        
        // get focus on the search textbox and search gift cards
        driver.findElement(By.xpath(".//div[@class='logo-search-row']/div[2]/form/div/div/input")).click();
        driver.findElement(By.xpath(".//div[@class='logo-search-row']/div[2]/form/div/div/input")).sendKeys("gift cards");
        driver.findElement(By.xpath(".//div[@class='logo-search-row']/div[2]/form/div/div/input")).sendKeys(Keys.RETURN);
        
        Thread.sleep(3000);
        
        // request card to be emailed clicking on radio button
        driver.findElement(By.xpath(".//div[@id='gcPurchase']/form/div/div/ul/li/label")).click();
        
        // select $100 gift card amount
        driver.findElement(By.xpath(".//div[@id='gcPurchase']/form/div[2]/div/ul/li[4]/label")).click();
        
        // verify the picture changes to $100 amount
        assertEquals("$100",driver.findElement(By.xpath(".//p[@class='giftCardImg']/span")).getText());
        
        // read the terms and conditions
        driver.findElement(By.xpath(".//div[@class='row gcTermsTrigger']/div/p/a")).click();
        
        // enter receipient info

        driver.findElement(By.xpath(".//div[@class='row emailFields']/div[2]/div/input")).click();
        driver.findElement(By.xpath(".//div[@class='row emailFields']/div[2]/div/input")).sendKeys("Zachary Wong");
        driver.findElement(By.xpath(".//div[@class='row emailFields']/div[3]/div/input")).click();
        driver.findElement(By.xpath(".//div[@class='row emailFields']/div[3]/div/input")).sendKeys(email_address);
        driver.findElement(By.xpath(".//div[@class='row emailFields']/div[4]/div/textarea")).click();
        driver.findElement(By.xpath(".//div[@class='row emailFields']/div[4]/div/textarea")).sendKeys("Merry Christmas");
        
        driver.findElement(By.xpath(".//div[@class='row emailFields']/div[6]/div/input")).click();
        driver.findElement(By.xpath(".//div[@class='row emailFields']/div[6]/div/input")).sendKeys("Dad");
        
        //check out for gift card
        driver.findElement(By.id("gcSubmit")).click();
        
        // fill out billing info
        driver.findElement(By.xpath(".//div[@id='BillToName']/div/input")).click();
        driver.findElement(By.xpath(".//div[@id='BillToName']/div/input")).sendKeys("Terry Wong");
        driver.findElement(By.xpath(".//div[@id='BillToAddress1']/div/input")).click();
        driver.findElement(By.xpath(".//div[@id='BillToAddress1']/div/input")).sendKeys("238 Hawk Hollow Drive");
        driver.findElement(By.xpath(".//div[@id='addressToZip']/div/div/input")).click();
        driver.findElement(By.xpath(".//div[@id='addressToZip']/div/div/input")).sendKeys("60103");
        driver.findElement(By.xpath(".//div[@id='BillToPhone']/div/input")).click();
        driver.findElement(By.xpath(".//div[@id='BillToPhone']/div/input")).sendKeys("6304084970");
        driver.findElement(By.xpath(".//div[@id='BillToEmail']/div/input")).click();
        driver.findElement(By.xpath(".//div[@id='BillToEmail']/div/input")).sendKeys("mr2or95@comcast.net");
        
        //driver.findElement(By.xpath(".//div[@id='addressToCity']/div/div/input")).click();
        //driver.findElement(By.xpath(".//div[@id='addressToCity']/div/div/input")).sendKeys("Bartlett");
        //new Select(driver.findElement(By.xpath(".//div[@id='addressToCity']/div/div/input"))).selectByVisibleText("Bartlett");
        
        driver.findElement(By.xpath(".//div[@id='addressToState']/div/div/select")).click();
        new Select(driver.findElement(By.xpath(".//div[@id='addressToState']/div/div/select"))).selectByVisibleText("Illinois");
        
        driver.findElement(By.xpath(".//div[@class='form-group checkout-action']/div/button")).click();
    }    
    
    // Account Management Tests
    //@Test
    public void testAccountAddressUpdate() throws Exception {
        //String email_address = new String("mr2or95@comcast.net");
        //String passw = new String("Yes1Heard");
        driver.get(baseUrl);
        // click on the go to my account logo
        driver.findElement(By.xpath(".//*[@id=\"open-my-account\"]/div")).click();
        // click on the sign in button
        driver.findElement(By.linkText("Sign in")).click();
        Thread.sleep(3000);
        // enter the email address
        driver.findElement(By.id("signInEmailAddress")).click();
        driver.findElement(By.id("signInEmailAddress")).sendKeys(email_address);
        // enter the password
        driver.findElement(By.id("signInPassword")).click();
        driver.findElement(By.id("signInPassword")).sendKeys(passw);
        // finally click on the sign in submit button
        Thread.sleep(8000);
        driver.findElement(By.id("submitSignIn")).click();
        // check billing address
        Thread.sleep(3000);
        driver.findElement(By.xpath(".//*[@id=\"addressbookTabHeading\"]/h5/button")).click();
        driver.findElement(By.xpath(".//*[@id=\"billingAddressEditToggle\"]/div/div/div[1]/div/div/div/div/ul/li[1]/button")).click();
        //change city
        driver.findElement(By.id("addressInputCity")).click();
        driver.findElement(By.id("addressInputCity")).clear();
        driver.findElement(By.id("addressInputCity")).sendKeys("Iron Mountain");
        //change state
        driver.findElement(By.id("addressInputState")).click();
        driver.findElement(By.id("addressInputState")).clear();
        driver.findElement(By.id("addressInputState")).sendKeys("MI");
        //change the zip code
        driver.findElement(By.id("addressInputZip")).click();
        driver.findElement(By.id("addressInputZip")).clear();
        driver.findElement(By.id("addressInputZip")).sendKeys("49801");
        
        // update the changed data
        driver.findElement(By.xpath(".//*[@id=\"billingAddressEditToggle\"]/div/div/div[2]/form/div/button[1]")).click();
        
        Assert.assertEquals("Billing address",driver.findElement(By.xpath(".//*[@id=\"billingAddressSection\"]/h4")).getText());
    }
    
    //@Test
    public void testAccountRewards() throws Exception {
        
        driver.get(baseUrl);
        // click on the go to my account logo
        driver.findElement(By.xpath(".//*[@id=\"open-my-account\"]/div")).click();
        // click on the sign in button
        driver.findElement(By.linkText("Sign in")).click();
        Thread.sleep(3000);
        // enter the email address
        driver.findElement(By.id("signInEmailAddress")).click();
        driver.findElement(By.id("signInEmailAddress")).sendKeys(email_address);
        // enter the password
        driver.findElement(By.id("signInPassword")).click();
        driver.findElement(By.id("signInPassword")).sendKeys(passw);
        // finally click on the sign in submit button
        Thread.sleep(8000);
        driver.findElement(By.id("submitSignIn")).click();
        // check current rewards
        Thread.sleep(3000);
        
        // make sure the account has reward points
        String points = driver.findElement(By.xpath(".//div[@class='row top-cards pb-4']/div[3]/div[2]/div/div/span")).getText();
        assertFalse("There are zero reward points for this account",points.equals("0"));
        System.out.println("The reward points are " + driver.findElement(By.xpath(".//div[@class='row top-cards pb-4']/div[3]/div[2]/div/div/span")).getText());
        
        driver.findElement(By.xpath(".//*[@id=\"rewardsTabHeading\"]/h5/button")).click();
        Thread.sleep(2000);
        
        //click on selecting rewards
        driver.findElement(By.id("pointsRedemptionDropDown")).click();
        // select a choice in the redemptions dropdown
        new Select(driver.findElement(By.id("pointsRedemptionDropDown"))).selectByIndex(2);
        
        driver.findElement(By.linkText("Cancel")).click();
        
        // log out
        Thread.sleep(3000);
        driver.findElement(By.xpath(".//*[@id=\"myAccountContainer\"]/div[3]/div[2]/p[4]/a")).click();
                
    }
    
    //@Test
    public void testCreateAccount() throws Exception {
        
        driver.get(baseUrl);
        
        // click on the go to my account logo
        //driver.findElement(By.linkText("Account info")).click();
        // click on the go to my account logo
        driver.findElement(By.xpath(".//*[@id=\"open-my-account\"]/div")).click();
        // click on the create button
        driver.findElement(By.xpath(".//div[@class='accountAction']/a[2]")).click();
        //Thread.sleep(3000);
        //driver.findElement(By.xpath(".//div[@id=\"loginAccordion\"]/div[2]/div/h4/button")).click();
        
        // add an email address
        driver.findElement(By.id("emailInput1")).click();
        driver.findElement(By.id("emailInput1")).sendKeys("BillTomy@gmail.com");
        
        // provide a password
        driver.findElement(By.id("createNewPassword")).click();
        driver.findElement(By.id("createNewPassword")).sendKeys("HereWe34Goad");
        
        // re-enter the password
        driver.findElement(By.id("createNewPassword1")).click();
        driver.findElement(By.id("createNewPassword1")).sendKeys("HereWe34Goad");
        
        // click the submit button
        //driver.findElement(By.xpath(".//div[@id='accountSignUpForm']/div/form/div/div/button")).click();
        
    }
    
    // Customer Relations Management
    //@Test
    public void testFindFitsCarCrutchfield() throws Exception {
        driver.get(baseUrl);
        Thread.sleep(3000);
        
        // get focus on the car year
        driver.findElement(By.xpath(".//*[@id=\"HompageVehicleSelector\"]/div[1]/div[3]/div[1]/div[1]/button")).click();
        Thread.sleep(3000);
        
        // set the year to 2019
        driver.findElement(By.xpath(".//*[@id=\"HompageVehicleSelector\"]/div[1]/div[3]/div[1]/div[1]/ul/li[2]/a")).click();
        
        Thread.sleep(3000);
        // set the make to Audi
        driver.findElement(By.linkText("Audi")).click();
        Thread.sleep(3000);
        
        // say this is an A4
        driver.findElement(By.xpath(".//*[@id=\"HompageVehicleSelector\"]/div[1]/div[3]/div[1]/div[3]/ul/li[2]/a")).click();
        
        // equipment includes Bang Oulafsen stereo in the car
        Thread.sleep(3000);
        driver.findElement(By.xpath(".//*[@id=\"HompageVehicleSelector\"]/div[1]/div[3]/div[1]/div[4]/ul/li[1]/a")).click();
        
        // request car stereos
        driver.findElement(By.xpath(".//*[@id=\"headingThree\"]/button")).click();
        
        // verify Crutchfield does not recommend replacing stereo
        assertEquals("We don't recommend replacing the radio in your vehicle.", driver.findElement(By.xpath(".//*[@id=\"car-stereos-prod-tab\"]/div/div[1]/div[1]/div/div[2]/div")).getText());
    }
    
    //@Test
    public void testResearchInstall() throws Exception {
        
        driver.manage().window().maximize();
        driver.get(baseUrl);
        Thread.sleep(2000);
        
        // select home audio
        driver.findElement(By.linkText("Articles & videos")).click();
        
        // select how-to guides
        driver.findElement(By.xpath(".//div[contains(@class,'js-research-menu open')]/div[2]/div[3]/a/img")).click();
        
        // locate subject dropdown
        driver.findElement(By.xpath(".//div[@id='learnHome']/nav/div/ul/li/a")).click();
        // select home audio
        driver.findElement(By.xpath(".//div[@id='learnHome']/nav/div/ul/li/ul/li[3]/a")).click();
        
        // select how to install home stereo receivers
        driver.findElement(By.xpath(".//div[@id='articleListResults']/div/div[4]/a/div/div/img")).click();
        
        // say no thanks to the chance to win rewards card
        driver.findElement(By.xpath(".//form[@id='emailSignupHomepage']/div[2]/input")).click();
        
        // verify that we are on the page showing installation info for home receivers
        assertEquals("Home theater receiver setup guide",driver.findElement(By.xpath(".//div[@class='panel-main-content  ']/section/div[2]/div/div/article/div/div/h1/span")).getText());
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        
        // read comments
        driver.findElement(By.id("commentContainerTrigger")).click();
    }
    
    //@Test
    public void testSubmitReviewQA() throws Exception {
        
        driver.manage().window().maximize();
        driver.get(baseUrl);
        Thread.sleep(2000);
        
        // select more equipment
        driver.findElement(By.linkText("More")).click();
        Thread.sleep(3000);
        // pick fitness trackers
        driver.findElement(By.linkText("Fitness trackers")).click();
                
        //select the Garmin HRM-Dual and look at details
        driver.findElement(By.xpath(".//div[@class='row prod-block-body product-list-items']/div[2]/a[contains(@rel,'150HRMDUAL')]/h4")).click();
        
        // open the existing reviews
        driver.findElement(By.id("CustomerReviewsTab")).click();
        
        // create a review for this product
        driver.findElement(By.xpath(".//p[@class='submit-review-cta']/a")).click();
        
        Thread.sleep(3000);
        // go to the text area for the review comment
        driver.findElement(By.xpath(".//div[@id='ReviewDisplay']/div[3]/div/form/div[3]/div/div/textarea")).click();
        driver.findElement(By.xpath(".//div[@id='ReviewDisplay']/div[3]/div/form/div[3]/div/div/textarea")).sendKeys("Great product. I have never had any issues with it.");
        // add email address
        driver.findElement(By.xpath(".//div[@id='ReviewDisplay']/div[3]/div/form/div[4]/div/div/input")).click();
        driver.findElement(By.xpath(".//div[@id='ReviewDisplay']/div[3]/div/form/div[4]/div/div/input")).sendKeys(email_address);
        // add a title to the review
        driver.findElement(By.xpath(".//div[@id='ReviewDisplay']/div[3]/div/form/section/div[2]/div/div/input")).click();
        driver.findElement(By.xpath(".//div[@id='ReviewDisplay']/div[3]/div/form/section/div[2]/div/div/input")).sendKeys("Fantastic tracker; Must have!");
        
        // Add a first name
        driver.findElement(By.xpath(".//div[@id='ReviewDisplay']/div[3]/div/form/section/div[2]/div[2]/div/input")).click();
        driver.findElement(By.xpath(".//div[@id='ReviewDisplay']/div[3]/div/form/section/div[2]/div[2]/div/input")).sendKeys("Terry");
        
        // Provide pros and cons
        driver.findElement(By.xpath(".//div[@id='ReviewDisplay']/div[3]/div/form/section/div[3]/div/div/textarea")).click();
        driver.findElement(By.xpath(".//div[@id='ReviewDisplay']/div[3]/div/form/section/div[3]/div/div/textarea")).sendKeys("Reliable, accurate, and consistent. Wish I found sooner");
        
        driver.findElement(By.xpath(".//div[@id='ReviewDisplay']/div[3]/div/form/section/div[3]/div[2]/div/textarea")).click();
        driver.findElement(By.xpath(".//div[@id='ReviewDisplay']/div[3]/div/form/section/div[3]/div[2]/div/textarea")).sendKeys("Battery needs replacing often; Always seems to slip off when in use");
        
        // open the existing Q&A tab
        driver.findElement(By.id("tab-E")).click();
        
        // Let's ask a question
        Thread.sleep(2000);
        driver.findElement(By.xpath(".//span[@class='TurnToItemInputTeaser']/div/div/h4/a")).click();
        
        // Type in my question
        driver.findElement(By.xpath(".//div[@id='TurnToContent']/div[5]/div/div/div[3]/div/div/textarea")).click();
        driver.findElement(By.xpath(".//div[@id='TurnToContent']/div[5]/div/div/div[3]/div/div/textarea")).sendKeys("What is your return policy?");
        
        // Verify that some similar questions were asked
        assertFalse("Apparently no one asked this question",driver.findElement(By.xpath(".//div[@id='TT4askQResultsLine']/span")).getText().equals("No results found."));
        
    }
    
    // Order Management
    //@Test
    public void testAddUpdateCartCrutchfield() throws Exception {
        // maximize window
        driver.manage().window().maximize();
        driver.get(baseUrl);
        Thread.sleep(3000);
        
        // select cameras on screen menu
        driver.findElement(By.xpath(".//*[@id=\"megaNav\"]/div/a[5]")).click();
        
        // access Nikon cameras to pick one
        driver.findElement(By.xpath(".//*[@id=\"megaNav\"]/div/div[6]/div[1]/div[1]/a[2]")).click();
        
        // filter to five star customer ratings
        driver.findElement(By.id("Customer Rating0")).click();
        
        Thread.sleep(4000);
        // add Nikon D750 camera to the cart
        driver.findElement(By.xpath(".//*[@id=\"prodGroupResults\"]/ul/li[1]/div/div[2]/div[3]/div/div[3]/a")).click();
        
        // click on keep shopping
        driver.findElement(By.cssSelector("body > div.modal.fade.show > div > div > div > div > div.product-info-container > div:nth-child(2) > div.next-step-links.col-6.col-md-12 > div > button")).click();
        
        // select home audio on screen menu
        driver.findElement(By.xpath(".//*[@id=\"megaNav\"]/div/a[2]")).click();
        
        // access stereo receivers equipment
        driver.findElement(By.xpath(".//*[@id=\"megaNav\"]/div/div[3]/div[1]/div[1]/a[3]")).click();
        
        Thread.sleep(3000);
        // access sort dropdown
        driver.findElement(By.xpath(".//*[@id=\"sortStrip\"]/div[3]/div/div/a")).click();
        
        // sort by best sellers
        driver.findElement(By.xpath(".//*[@id=\"sortStrip\"]/div[3]/div/div/ul/li[3]/a")).click();
        Thread.sleep(3000);
        
        // add Sony STR-DH190 receiver to cart
        driver.findElement(By.xpath(".//div[@id='productList']/div/div/div/ul/li[2]/div/div[2]/div[3]/div/div[3]/a")).click();
        
        // go to the cart
        driver.findElement(By.xpath("./html/body/div[5]/div/div/div/div/div[4]/div[2]/div[2]/div/a")).click();
        
        // make sure the camera is in the cart
        assertEquals("Nikon D750 (body only)",driver.findElement(By.xpath(".//*[@id=\"CartForm\"]/div[3]/div/div/div[1]/div[1]/div/div[1]/div[2]/div[1]/h4/a")).getText());
    
        // see if the Sony receiver is in the cart
        assertEquals("Sony STR-DH190",driver.findElement(By.xpath(".//*[@id=\"CartForm\"]/div[3]/div/div/div[1]/div[2]/div/div[1]/div[2]/div[1]/h4/a")).getText());
    
        // update the number of receivers from 1 to 3
        WebElement selectReceiver = driver.findElement(By.name("qtyMobile1"));
        Select qtyRec = new Select(selectReceiver);
        qtyRec.selectByIndex(3);
        
        Thread.sleep(2000);
        // update the quantity
        driver.findElement(By.xpath(".//*[@id=\"CartForm\"]/div[3]/div/div/div[1]/div[2]/div/div[1]/div[3]/div[1]/div[2]/div/div/div/button")).click();
        
        // now delete the camera from the cart
        WebElement selectCamera = driver.findElement(By.name("qtyMobile0"));
        Select qtyCam = new Select(selectCamera);
        qtyCam.selectByIndex(0);
        
        Thread.sleep(2000);
        // update the quantity to zero
        driver.findElement(By.xpath(".//*[@id=\"CartForm\"]/div[3]/div/div/div[1]/div[1]/div/div[1]/div[3]/div[1]/div[2]/div/div/div/button")).click();
        
        // make sure 3 items in cart
        String cartQty = driver.findElement(By.xpath(".//section[@id='advisorConnectIcons']/div/div/div/a/div/div")).getText();
        
        assertTrue("The quantity in cart is not three",cartQty.equals("3"));   
   
    }
    
    @Test
    public void testExistingCart() throws Exception {
        
        driver.manage().window().maximize();
        driver.get(baseUrl);
        // click on the go to my account logo
        driver.findElement(By.xpath(".//*[@id=\"open-my-account\"]/div")).click();
        // click on the sign in button
        driver.findElement(By.linkText("Sign in")).click();
        Thread.sleep(3000);
        // enter the email address
        driver.findElement(By.id("signInEmailAddress")).click();
        driver.findElement(By.id("signInEmailAddress")).sendKeys(email_address);
        // enter the password
        driver.findElement(By.id("signInPassword")).click();
        driver.findElement(By.id("signInPassword")).sendKeys(passw);
        // finally click on the sign in submit button
        Thread.sleep(8000);
        driver.findElement(By.id("submitSignIn")).click();
        
        Thread.sleep(5000);
        //make sure I am logged into my account
        assertEquals("Terry",driver.findElement(By.xpath(".//*[@id=\"open-my-account\"]/div")).getText());
        
        Thread.sleep(3000);
        // go to the shopping cart
        //driver.findElement(By.xpath(".//*[@id=\"mastheadCartTrigger\"]/div/div[2]/svg[2]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Terry'])[1]/following::div[4]")).click();
        
        Thread.sleep(3000);
        
        // go to checkout
        driver.findElement(By.xpath(".//*[@id=\"CartForm\"]/div[3]/div/div/div[2]/div[1]/div[2]/div/div[3]/a/div[2]")).click();
        
        // provide billing info
        driver.findElement(By.id("billingName")).click();
        driver.findElement(By.id("billingName")).clear();
        driver.findElement(By.id("billingName")).sendKeys("Terry Wong");
        
        driver.findElement(By.id("billingAddress1")).click();
        driver.findElement(By.id("billingAddress1")).clear();
        driver.findElement(By.id("billingAddress1")).sendKeys("238 Hawk Hollow Drive");
        
        //driver.findElement(By.id("ct100_CheckoutResponsiveMainContentPlaceHolder_billingCityStateZip_addressCity")).click();
        //driver.findElement(By.id("ct100_CheckoutResponsiveMainContentPlaceHolder_billingCityStateZip_addressCity")).clear();
        //driver.findElement(By.id("ct100_CheckoutResponsiveMainContentPlaceHolder_billingCityStateZip_addressCity")).sendKeys("Bartlett");
        
        //driver.findElement(By.id("ct100_CheckoutResponsiveMainContentPlaceHolder_billingCityStateZip_addressState")).click();
        //new Select (driver.findElement(By.id("ct100_CheckoutResponsiveMainContentPlaceHolder_billingCityStateZip_addressState"))).selectByVisibleText("Illinois");
        
        driver.findElement(By.xpath(".//div[@id='addressToZip']/div/div/input")).click();
        driver.findElement(By.xpath(".//div[@id='addressToZip']/div/div/input")).clear();
        driver.findElement(By.xpath(".//div[@id='addressToZip']/div/div/input")).sendKeys("60103");
        
        driver.findElement(By.id("billingPhone")).click();
        driver.findElement(By.id("billingPhone")).clear();
        driver.findElement(By.id("billingPhone")).sendKeys("6304084970");
        
        //driver.findElement(By.id("email")).click();
        //driver.findElement(By.id("email")).clear();
        //driver.findElement(By.id("email")).sendKeys(email_address);
        
        // indicate this is a gift
        driver.findElement(By.id("giftOrder")).click();
        
        // uncheck the same shiiping info as billing
        driver.findElement(By.xpath(".//*[@id='same']")).click();
        
        // fill out the shipping information
        // name
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[2]/div/input")).click();
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[2]/div/input")).clear();
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[2]/div/input")).sendKeys("Daniel Graham");
        // address
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[3]/div/input")).click();
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[3]/div/input")).clear();
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[3]/div/input")).sendKeys("1415 Maritime Drive");
        // zip code
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[6]/div/div/input")).click();
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[6]/div/div/input")).clear();
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[6]/div/div/input")).sendKeys("47715");
       
        // city
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[5]/div/div/div/input")).click();
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[5]/div/div/div/input")).clear();
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[5]/div/div/div/input")).sendKeys("Evansville");
        
        // state
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[5]/div[2]/div/div/select")).click();
        new Select (driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[5]/div[2]/div/div/select"))).selectByVisibleText("Indiana");
        // zip code
        //driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[6]/div/div/input")).click();
        //driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[6]/div/div/input")).clear();
        //driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[6]/div/div/input")).sendKeys("47715");
        // phone
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[7]/div/input")).click();
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[7]/div/input")).clear();
        driver.findElement(By.xpath(".//*[@id='ShippingAddressPanel']/div[7]/div/input")).sendKeys("8125987601");
        
        // select the shipping & payment button
        driver.findElement(By.xpath(".//*[@id=\"AddressForm\"]/div[14]/div/button")).click();
        
        // select shipping option of UPS air saver
        driver.findElement(By.xpath(".//*[@id='shippingBilling']/div[3]/div/div/div[2]/label")).click();
        
        // enter the payment info
        driver.findElement(By.xpath(".//div[@id='option1']/fieldset/div/div/div/div/input")).click();
        driver.findElement(By.xpath(".//div[@id='option1']/fieldset/div/div/div/div/input")).sendKeys("4388576135110765");
        
        driver.findElement(By.xpath(".//div[@id='option1']/fieldset/div[2]/div/div/input")).click();
        driver.findElement(By.xpath(".//div[@id='option1']/fieldset/div[2]/div/div/input")).sendKeys("0823");
        
        //security code
        driver.findElement(By.xpath(".//div[@id='option1']/fieldset/div[3]/div/div/input")).click();
        driver.findElement(By.xpath(".//div[@id='option1']/fieldset/div[3]/div/div/input")).sendKeys("028");
        
        driver.findElement(By.id("continueCC")).click();
        
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("errorDisplayText")));
        
        assertEquals("* Invalid card number",driver.findElement(By.id("errorDisplayText")).getText());
        
    }       
}
