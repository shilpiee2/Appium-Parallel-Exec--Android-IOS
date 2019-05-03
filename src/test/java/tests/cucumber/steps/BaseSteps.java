package tests.cucumber.steps;

import org.openqa.selenium.support.ui.WebDriverWait;

import Base.*;
import tests.ThreadLocalDriver;

public class BaseSteps {

    protected AiBPersonalFxFormPageObjectModel aiBPersonalFxFormPageObjectModel = null;
    protected AibPersonalWebChatFormPageObjectModel aibPersonalWebChatFormPageObjectModel = null;
    protected WebDriverWait wait;

    //Screen Classes Initialization
    
    protected void setupCucumber () {
        System.out.println("Cucumber Base Test Before-login-test-cucumber");
        wait = new WebDriverWait(ThreadLocalDriver.getTLDriver(), 20);
        aiBPersonalFxFormPageObjectModel = new AiBPersonalFxFormPageObjectModel(ThreadLocalDriver.getTLDriver());
        aibPersonalWebChatFormPageObjectModel = new AibPersonalWebChatFormPageObjectModel(ThreadLocalDriver.getTLDriver());
        /*//Unlock the device if it is locked.
        final Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("adb shell input keyevent 224");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    protected void teardown(){
        ThreadLocalDriver.getTLDriver().quit();
    }
}
