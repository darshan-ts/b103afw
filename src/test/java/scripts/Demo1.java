package scripts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Demo1 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// How to read data from property file
		Properties p=new Properties();
		p.load(new FileInputStream("./config.properties"));
		String value = p.getProperty("APP_URL"); // The problem with this code is return type is always String even if we mention numbers. So we have to convert it using Integer.parseInt() concept 
		System.out.println(value); //If we do any mistake, in passing "APP_URL" such as "APP_URl" it gives null. It is case and space sensitive.

	}

}
