package scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import generic.BaseTest;
import generic.Utility;
import page.HomePage;
import page.LoginPage;



public class ValidLogin extends BaseTest
{

	@Test(priority = 1)
	public void testValidLogin()
	{
//		get data from excel
		String un = Utility.getExcelData(EXCEL_PATH, "ValidLogin", 1, 0);
		String pw = Utility.getExcelData(EXCEL_PATH, "ValidLogin", 1, 1);
		
//		1. enter valid user name
		LoginPage loginPage=new LoginPage(driver);
		loginPage.setUserName(un);
		
//		2. enter valid password
		loginPage.setPassword(pw);
		
//		3. click on go button
		loginPage.clickGoButton();
		
//		4. verify home page is displayed
		HomePage homePage=new HomePage(driver);
		boolean result = homePage.verifyHomePageIsDisplayed(wait);
		Assert.assertTrue(result);
	}
}
