package generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utility {
	
	public static String getExcelData(String path,String sheetName,int row,int col)
	{
		String value="";
		try {
			//It will attach step Excel Data:username in extent report
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			value=wb.getSheet(sheetName).getRow(row).getCell(col).getStringCellValue();
			System.out.println(value);
			
			//It will print 'username' in the console
//			Workbook wb1 = WorkbookFactory.create(new FileInputStream(path));
//			Sheet s = wb1.getSheet(sheetName);
//			Row r=s.getRow(row);
//			Cell c = r.getCell(col);
//			String v= c.toString();
//			System.out.println(v);
			
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static void getScreenShot(WebDriver driver,String path) //Java rule says that, by default make method as static.If method is accessing any non static variable/member of a class then it must be declared as non static method.
	{
		TakesScreenshot t=(TakesScreenshot)driver;
		File srcFile = t.getScreenshotAs(OutputType.FILE);
		File dstFile=new File(path); //It doesn't make any sense to hard code it.So String path is passed as an argument.
		try {
			FileUtils.copyFile(srcFile, dstFile); //It's not good practice to throw exception. It's always better to try catch it.
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static String getProperty(String path, String key) // We added this method here, so that it can be called again and again.
	{
		Properties p=new Properties();
		
//		p.load(new FileInputStream("./config.properties"));
		try {
			p.load(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		String value = p.getProperty("APP_URL");
		String value = p.getProperty(key);
		return value; //returns value whatever we are reading from getProperty() and change return type from void to String
		
		/*
		 * Rules while creating method:
		 * Do not hardcode any code/input
		 * It's not good practice to throw exception. It's always better to try catch it.
		 */
	}

}
