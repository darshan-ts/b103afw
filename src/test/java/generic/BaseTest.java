package generic;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseTest
{
	/*
	In-order to use variables everywhere, we declared them as public.
	But in this case, ExtentReports is declared as static. The reason is:
	Every time it creates an object of TestNG class and only first time it will initialize ExtentReports.
	During Second time again it will create an object of another test class(TestNG class), if in case
	ExtentReports is declared as non static,it will create another copy and becomes null. So we get NullPointer Exception.
	To avoid it technically, it is declared as static.
	
	For remaining non static variables such as driver,wait,test we don't get NullPointer Exception. If we don't initialize these variables, obviously we face NullPointer Exception.
	But luckily, we have initialized these variables in our program.
	*/
	public static ExtentReports extent; //static means single copy stored in static pool and one time initialization is enough to solve NullPointer Exception.
	public static final String CONFIG_PATH="./config.properties"; //If variable is declared as final, then it should always be written in Uppercase/Capital letters because it is constant(Here it is CONFIG_PATH of String type). Am the one who designing framework and I don't want anybody to change path of it. So final keyword must be used.
	public static final String REPORT_PATH="./target/report.html";
	public static final String EXCEL_PATH="./data/input.xlsx";
	
	public WebDriver driver; //Before its scope was package private. In-order to make accessible outside current package its declared as public and used in scripts.TC1java file
	public WebDriverWait wait; //WebDriverWait is also declared as public to use it anywhere.
	public ExtentTest test;
	
	@BeforeSuite
	public void createReport()
	{
		extent=new ExtentReports(); //This is creating ExtentReport object
//		ExtentSparkReporter spark=new ExtentSparkReporter("./target/report.html"); //Spark is just a template/format to display report.
		ExtentSparkReporter spark=new ExtentSparkReporter(REPORT_PATH);
		extent.attachReporter(spark); //Hey ExtentReport please create report in spark format
	}
	
	@AfterSuite
	public void publishReport()
	{
		extent.flush();
	}
	
	@BeforeMethod
//	@BeforeMethod code always executes before each and every test
//	public void setUp()-> In some companies setUp() and tearDown() methods are also used
	public void preCondition(Method method) throws MalformedURLException
	{
//		driver=new ChromeDriver();
//		driver.get("http://www.google.com");
//		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //This is implicitlyWait timeout
//		wait=new WebDriverWait(driver, Duration.ofSeconds(10)); //This is explicitlyWait timeout
		
		test = extent.createTest(method.getName());
		
		//Earlier it was hardcoded. Now we are taking/reading data from config.properties file
		String grid= Utility.getProperty(CONFIG_PATH, "GRID");
		String grid_url= Utility.getProperty(CONFIG_PATH,"GRID_URL");
		String browser = Utility.getProperty(CONFIG_PATH, "BROWSER");
		String appURL=Utility.getProperty(CONFIG_PATH, "APP_URL");
		String ito=Utility.getProperty(CONFIG_PATH, "ITO");
		String eto=Utility.getProperty(CONFIG_PATH, "ETO");
		
		/* 
		 * This piece of code opens browser in local system
//		test.info("Open the Chrome Browser");
		test.info("Open the "+browser+" Browser");
		if(browser.equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver(); //This is local system code and we have to support Selenium Grid also. So, we must use RemoteWebDriver. If we replace this code with RemoteWebDriver then I will never be able to run on my own laptop.If I replace the program, it always run on Saucelabs.There should be an option to run it on both local and Remote platform. 
		}
//		else if{
//				Homework:add other browsers using else if statements
//			driver=new FirefoxDriver();
//		}
		else
		{
			driver=new EdgeDriver();
		}
		*/
		
		if(grid.equalsIgnoreCase("yes"))
		{
			test.info("Open the "+browser+" Browser in remote system:"+grid_url);
			if(browser.equalsIgnoreCase("chrome")) {
				driver=new RemoteWebDriver(new URL(grid_url),new ChromeOptions()); //The constructor URL(String) is deprecated warning. Don't worry!
			}
			else
			{
				driver=new RemoteWebDriver(new URL(grid_url),new EdgeOptions());
			}
		}
		else
		{
			test.info("Open the "+browser+" Browser in local system");
			if(browser.equalsIgnoreCase("chrome")) {
				driver=new ChromeDriver();
			}
			else
			{
				driver=new EdgeDriver();
			}
		}
		
		test.info("Enter the URL:"+appURL);
//		driver.get("http://www.google.com");
		driver.get(appURL);
		
		test.info("Maximize the browser");
		driver.manage().window().maximize();
		
		test.info("Set ITO:"+ito);
		int integerITO = Integer.parseInt(ito); //The problem with this code is return type is always String even if we mention numbers. So we have to convert it using Integer.parseInt() concept 
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(integerITO));
		
		test.info("Set ETO:"+eto);
		int integerETO = Integer.parseInt(eto); //The problem with this code is return type is always String even if we mention numbers. So we have to convert it using Integer.parseInt() concept 
//		wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait=new WebDriverWait(driver, Duration.ofSeconds(integerETO));
	}
		
	@AfterMethod
//	public void tearDown()
	public void postCondition(ITestResult testresult)
	{
		
		//String testName = testresult.getTestName(); //getTestName() is not getting testName properly. So,testName is coming null and 'null.png' got created. Instead of this, testresult.getMethod().getMethodName() must be used.
		String testName = testresult.getMethod().getMethodName();
		if(testresult.getStatus()==1)
		{
			test.pass("TEST is PASSED/SUCCESS");
		}
		else
		{
//			Utility.getScreenShot(driver,"./target/page.png"); //If more than 1 test fails this image file gets over-writed. That's not a good idea. So we will save name of the file same as test name.So that Image name is not hardcoded.
			
//			Utility.getScreenShot(driver,"./target/"+testName+".png");
//			test.fail("TEST is FAILED");
			
//			String path="./target/"+testName+".png"; //There is no point in storing as a Variable. It must be used only when it has to be reused.
//			Utility.getScreenShot(driver, path);
//			Media m = MediaEntityBuilder.createScreenCaptureFromPath(path).build(); //It's already in target folder and not necessary to mention it explicitly. Due to this mistake screenshot was not getting attached in extent report.
//			test.fail(m);
			
			Utility.getScreenShot(driver, "./target/"+testName+".png"); 
			Media m = MediaEntityBuilder.createScreenCaptureFromPath("./"+testName+".png").build();
			test.fail(m); //It's not only marking test as failure, but also attaching screenshot at particular line/step. If we write normal code, then screenshot gets attached at the top of the extent report.
		}
		test.info("Close the brower");
		driver.quit();
		
		/*
		 //Test status
		  int CREATED = -1;
		  int SUCCESS = 1;
		  int FAILURE = 2;
		  int SKIP = 3;
		  int SUCCESS_PERCENTAGE_FAILURE = 4;
		  int STARTED = 16;
		  */
	}	
}
