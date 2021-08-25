package Mobile.Plutus_Payzapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Base {

	public static AppiumDriverLocalService service;
	public static AndroidDriver<AndroidElement> driver;
	
	public AppiumDriverLocalService startService()
	{
		
		boolean flag = checkIfServerIsRunnning(4723);
		if(!flag)
		{
			service=AppiumDriverLocalService.buildDefaultService();
			service.start();
		}
		return service;
	}
	
	public boolean checkIfServerIsRunnning(int port) {

	    boolean isServerRunning = false;
	    ServerSocket serverSocket;
	    try {
	        serverSocket = new ServerSocket(port);
	        serverSocket.close();
	    } catch (IOException e) {
	        //If control comes here, then it means that the port is in use
	        isServerRunning = true;
	    } finally {
	        serverSocket = null;
	    }
	    return isServerRunning;
	}
	
	public static void startEmulator() throws IOException, InterruptedException
	{
		System.out.println("Hit Batch File" + System.getProperty("user.dir")+"\\src\\main\\java\\resources\\startEmulator.bat");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\startEmulator.bat");
	//To give time to emulator to open
		Thread.sleep(5000);
	}
	
	public static AndroidDriver<AndroidElement> capabilites(String appName) throws IOException, InterruptedException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\Mobile\\Plutus_Payzapp\\global.properties");
		Properties prop = new Properties();
		prop.load(fis);
		prop.get(appName);
		File appPath = new File("src//main//java");
		File app = new File(appPath, (String) prop.get(appName));
		DesiredCapabilities cap = new DesiredCapabilities();
		//To call from global properties 
		String device = (String) prop.get("device");
		//To call from MVN Test globally
	//	String device = System.getProperty("deviceName");
		if(device.contains("Emulator"))
		{
			startEmulator();
		}
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, prop.get(device));
		cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 14);
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		driver = new AndroidDriver<>(new URL("http://192.168.1.6:4723/wd/hub"), cap);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	public AndroidDriver<AndroidElement> runCapabilites(String appName,boolean cloud) throws IOException, InterruptedException
	{
		if(cloud)
		{
			return cloudCapabilites(appName);
		}
		{
			return capabilites(appName);	
		}
	}
	
	public static AndroidDriver<AndroidElement> cloudCapabilites(String appName) throws IOException, InterruptedException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\Mobile\\Plutus_Payzapp\\global.properties");
		Properties prop = new Properties();
		prop.load(fis);
		DesiredCapabilities cap = new DesiredCapabilities();
		//To call from global properties 
		String device = (String) prop.get("device");
		//To call from MVN Test globally
	//	String device = System.getProperty("deviceName");
		if(device.contains("Emulator"))
		{
			startEmulator();
		}
		cap.setCapability("browserstack.user", "krishanmohan_LJTy7Q");
	    cap.setCapability("browserstack.key", "Zx8yXxJsTuPD1JYzw2UP");
	    if(appName.equalsIgnoreCase("GeneralappName"))
	    {
	    cap.setCapability("app", "bs://3a53e417b2b38901451110547517dbcd5841098a");
	    }
	    else
	    {
	    	cap.setCapability("app", "bs://0b63d8c024c5eb2befb227c62f28f99ba36afc69");	
	    }
	    cap.setCapability("device", "OnePlus 7T");
	    cap.setCapability("os_version", "10.0");
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 14);
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		driver = new AndroidDriver<>(new URL("http://hub.browserstack.com/wd/hub"), cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	public static void getScreenShot(String name) throws IOException
	{
		File srcFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"\\"+name+".jpg"));
	}

}
