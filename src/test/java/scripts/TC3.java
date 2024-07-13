package scripts;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TC3 {
	
	@BeforeMethod
	public void m1(Method m) //By accepting an argument of type 'Method' we can get name of the test method. @BeforeMethod depends on argument method. This is called as dependency Injection.Usually caller will send an argument in normal method creation. In this case TestNG is sending an argument. TestNG will inject the argument that is dependent to call the method.
	{
		String mn = m.getName();
		System.out.println("The next method is:"+mn); //While executing TestNG methods, will be sent as an argument to @BeforeMethod annotation that predicts and prints name of the test which is going to execute next. 
	}
	
	@AfterMethod
	public void m2(ITestResult r)	//Use an argument of type ITestResult which gives details of the result. It doesn't make any sense to use this argument in @BeforeMethod. After execution only we get test results/status and it must be used in @AfterMethod only.
	{
		int s = r.getStatus();
//		System.out.println(s);
		if(s==1)
		{
			System.out.println("Hi This is PASS");
		}
		else
		{
			System.out.println("Hi This is FAIL");
		}
	}
	
	@Test
	public void testLogin1()
	{
		System.out.println("test login....");
	}
	
	@Test
	public void testLogout2()
	{
		System.out.println("test logout");
//		Assert.fail();
		
	}
	
//	@Test
//	public void testing()
//	{
//		System.out.println("testing");
//	}
	
//	if we add any tests further, it will fetch name of the test method automatically.
	
}
