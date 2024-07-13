package scripts;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import generic.BaseTest;
import generic.Utility;

public class TC1 extends BaseTest
{
	@Test
	public void test1()
	{
//		Reporter.log(driver.getTitle(),true); //driver is declared as public in BaseTest.java and because of this it can be used here without any issues.
		String data = Utility.getExcelData(EXCEL_PATH, "TC1", 0, 0);
		test.info("Excel Data:"+data);
		test.info(driver.getTitle()); //Reporter.log statement can be written like this also.
//		Assert.fail();
	}
}
