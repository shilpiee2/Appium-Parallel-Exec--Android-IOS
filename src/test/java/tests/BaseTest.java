package tests;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import Base.AiBPersonalFxFormPageObjectModel;
import Base.AibPersonalWebChatFormPageObjectModel;
import java.io.IOException;
import java.net.URL;

public class BaseTest {
	
	public String platform = null;
	
	public static String hubUrl = "http://192.168.0.94:4444/wd/hub";

    public WebDriverWait wait;
    //private ThreadLocalDriver threadLocalDriver = new ThreadLocalDriver();
    //Base Screens for all cases
    protected AiBPersonalFxFormPageObjectModel aibPersonalFxFormPageObjectModel = null;
    protected AibPersonalWebChatFormPageObjectModel aibPersonalWebChatFormPageObjectModel = null;
    @BeforeMethod
    @Parameters({"deviceName", "platformVersion","udid","port","chromedriverpath","chromeport","platform"})
    public void setup (@Optional String deviceName,@Optional String platformVersion,@Optional String udid,@Optional String port,@Optional String chromedriverpath,@Optional String chromeport,@Optional String platform) throws IOException {
        
    	System.out.println("TestNG Before");
        switch (platform.toLowerCase()) {
       
        case "web":
        	
        	ChromeOptions options = new ChromeOptions();
    		options.addArguments("--start-maximized");
    		options.addArguments("--test-type");
    		options.addArguments("--ignore-certificate-errors");
    		options.addArguments("--disable-extensions");
    		System.out.println("Chrome Browser is opening..... ");
    		ThreadLocalDriver.setTLDriver(new RemoteWebDriver(new URL(hubUrl),options));
            wait = new WebDriverWait(ThreadLocalDriver.getTLDriver(), 20);
            
            break;
            
        case "android":
        	
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
            ThreadLocalDriver.setTLDriver(new RemoteWebDriver(new URL("http://127.0.0.1:"+port+"/wd/hub"),caps));
            wait = new WebDriverWait(ThreadLocalDriver.getTLDriver(), 20);
        	break;    
        	
        default:
			System.out.println("Platform not supported! Check if you set ios or android on the parameter");
			break;
        }
        
        //BasePage Initialization
        
        aibPersonalFxFormPageObjectModel = new AiBPersonalFxFormPageObjectModel(ThreadLocalDriver.getTLDriver());
        aibPersonalWebChatFormPageObjectModel = new AibPersonalWebChatFormPageObjectModel(ThreadLocalDriver.getTLDriver());
        
    }
    
    @AfterMethod
    public synchronized void teardown(){
        ThreadLocalDriver.getTLDriver().quit();
    }

}
