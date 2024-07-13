package scripts;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import generic.BaseTest;

public class TC2 extends BaseTest
{
	@Test
	public void test2()
	{
//		Reporter.log(driver.getTitle(),true);//driver is declared as public in BaseTest.java and because of this it can be used here without any issues.
		test.info(driver.getTitle()); //Above statement can be written like this also.
		Assert.fail(); // This statement is making the TestNG test as failed here. But in extent report, still it is displaying as pass. Let's see how to capture the failure in extent report.
					   // goto TC3 and create @AfterMethod for the method m2(). In this method we need details about whether test method is pass/fail. So we need result of the method and we should pass ITestResult as an argument. 
	}
}
