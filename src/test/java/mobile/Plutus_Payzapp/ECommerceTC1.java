package mobile.Plutus_Payzapp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Mobile.Plutus_Payzapp.Base;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

@Listeners(resources.Listeners.class)
public class ECommerceTC1 extends Base {

	@BeforeTest
	public void killAllNodes() throws IOException, InterruptedException
	{
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(3000);
	}
	
	
	@Test
	public void home() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//service=startService();
		AndroidDriver<AndroidElement>driver = runCapabilites("GeneralappName",true);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver.findElementById("com.androidsample.generalstore:id/nameField").sendKeys(Keys.SHIFT,"hello");
		//to hide active keyboard in mobile app
	//	driver.hideKeyboard();
		driver.findElementByXPath("//*[@text='Female']").click();
	//	driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"France\"));");
	//	driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"));");
   driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\"Argentina\").instance(0))")).click();   
	//		driver.findElementByXPath("//*[@text='France']").click();
		driver.findElementById("com.androidsample.generalstore:id/btnLetsShop").click();
		//Toast Message 
		//Default class name android.widget.Toast
		//Attribute value as name
		String toastMessage= driver.findElementByXPath("//android.widget.Toast[1]").getAttribute("name");
		
		System.out.println(toastMessage);
		
		Assert.assertEquals("Please entersss your name", toastMessage);
	//	service.stop();
	}
}