package mobile.Plutus_Payzapp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Mobile.Plutus_Payzapp.Base;
import Mobile.Plutus_Payzapp.TestData;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePage;

@Listeners(resources.Listeners.class)
public class Basic extends Base {

	@Test(dataProvider="InputData",dataProviderClass=TestData.class)
	public void saving(String input) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//service=startService();
		AndroidDriver<AndroidElement>driver = runCapabilites("Apiapp",true);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		HomePage hp = new HomePage(driver); 
		hp.btnClickPreference();
		driver.findElementByXPath("//android.widget.TextView[@text='3. Preference dependencies']").click();
		driver.findElementById("android:id/checkbox").click();
		driver.findElementByXPath("(//android.widget.RelativeLayout)[2]").click();
		driver.findElementById("android:id/edit").sendKeys(Keys.SHIFT,input);
	//	driver.findElementById("android:id/button1").click();
		driver.findElementsByClassName("android.widget.Button").get(1).click();
//		service.stop();
	}

}
