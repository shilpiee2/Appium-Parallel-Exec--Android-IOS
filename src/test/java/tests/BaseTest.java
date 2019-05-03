package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import Base.AiBPersonalFxFormPageObjectModel;
import Base.AibPersonalWebChatFormPageObjectModel;

import java.io.IOException;
import java.net.URL;

public class BaseTest {

    public WebDriverWait wait;
    //private ThreadLocalDriver threadLocalDriver = new ThreadLocalDriver();
    //Base Screens for all cases
    protected AiBPersonalFxFormPageObjectModel aibPersonalFxFormPageObjectModel = null;
    protected AibPersonalWebChatFormPageObjectModel aibPersonalWebChatFormPageObjectModel = null;
    @BeforeMethod
    @Parameters({"deviceName", "platformVersion","udid","port","chromedriverpath","chromeport"})
    public void setup (String deviceName, String platformVersion,String udid,String port,String chromedriverpath,String chromeport) throws IOException {
        System.out.println("TestNG Before");
        String path = System.getProperty("user.dir");

        //Unlock the device if it is locked.
        final Runtime rt = Runtime.getRuntime();
        rt.exec("adb shell input keyevent 224");

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", deviceName);
        caps.setCapability("platformVersion", platformVersion);
        caps.setCapability("platformName", "Android");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("noReset","false");
        caps.setCapability("chromeDriverPort", chromeport);
        if (chromedriverpath != null) {
			caps.setCapability(
					AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,
					chromedriverpath);
		}
        ThreadLocalDriver.setTLDriver(new AndroidDriver (new URL("http://127.0.0.1:"+port+"/wd/hub"),caps));
        wait = new WebDriverWait(ThreadLocalDriver.getTLDriver(), 20);

        //Base Screen Initialization
        aibPersonalFxFormPageObjectModel = new AiBPersonalFxFormPageObjectModel(ThreadLocalDriver.getTLDriver());
        aibPersonalWebChatFormPageObjectModel = new AibPersonalWebChatFormPageObjectModel(ThreadLocalDriver.getTLDriver());
    }

    @AfterMethod
    public synchronized void teardown(){
        ThreadLocalDriver.getTLDriver().quit();
    }

}
