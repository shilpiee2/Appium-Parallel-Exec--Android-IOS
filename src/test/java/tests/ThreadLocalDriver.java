package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class ThreadLocalDriver {

    private static ThreadLocal<RemoteWebDriver> tlDriver = new ThreadLocal<>();

    public synchronized static void setTLDriver(RemoteWebDriver driver) {
        tlDriver.set(driver);
    }

    public synchronized static RemoteWebDriver getTLDriver() {
        return tlDriver.get();
    }
}
