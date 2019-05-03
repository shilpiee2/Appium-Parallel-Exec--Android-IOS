package Base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


/**
 * Page object model for AIB FX form Page.
 */

public class AiBPersonalFxFormPageObjectModel extends BasePage {
		
  /**
 * css value of branchfield.
 */
  @FindBy(id = "branchName")
  private WebElement branchField;
  /**
 * css value of branchDropdown.
 */
  @FindBy(id = "branchNSC")
  private WebElement branchDropdown;
  
  /**
 * page object model initialization.
 */
  
  public AiBPersonalFxFormPageObjectModel(AppiumDriver driver) {
	  super(driver);
	PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	  
  }

	/**
 * method to enter the branch into branch field tab.
 * @param branchName name of the branch
 * @throws InterruptedException 
 */

  public void enterBranchDetails(String branch) throws InterruptedException {
    setText(branchField, branch);
  }
  
  /**
 * Method to verify the invisibility of branch.
 * @return 0/1
 */

  public boolean invisibilityBranch() {
	  explicitWait(branchDropdown, 100);
    branchField.sendKeys(Keys.RETURN);
    boolean result = invisibilityOfElement(branchDropdown);
    Assert.assertEquals(true, result);
    return result;
  }

public void navigate() {
	  String url = "https://aib2.aibtest.ie/personal-forms/fx-form";
	  driver.get(url);
	  //assertEquals(url, "https://aib2.aibtest.ie/personal-forms/fx-form");
	  }
}
