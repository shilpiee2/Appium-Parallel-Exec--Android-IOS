package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class ThreadLocalDriver {

    private static ThreadLocal<AppiumDriver> tlDriver = new ThreadLocal<>();

    public synchronized static void setTLDriver(AppiumDriver driver) {
        tlDriver.set(driver);
    }

    public synchronized static AppiumDriver getTLDriver() {
        return tlDriver.get();
    }
}
