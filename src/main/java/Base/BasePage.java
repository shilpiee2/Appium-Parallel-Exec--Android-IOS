package Base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class BasePage {
	
	protected static Logger Log = Logger.getLogger(BasePage.class);

    protected AppiumDriver<?> driver;
    protected WebDriverWait wait;

    public BasePage (AppiumDriver<?> driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 15);
    }
    
    public void setText(WebElement ele, String strText)
			throws InterruptedException {
		try {
			explicitWait(ele, 100);
			ele.click();
			ele.clear();
			Actions action = new Actions(driver);
			action.moveToElement(ele);
			ele.sendKeys(strText);
			Thread.sleep(5000);
			System.out.println("Set the text for " + ele.getText()
					+ ele.getAttribute("id"));
		} catch (ElementNotVisibleException en) {
			System.out.println("MoveTo " + ele.getText() + ele.getAttribute("name")
					+ " is present in the DOM but not visible ");
		} catch (StaleElementReferenceException s) {
			System.out.println(strText + " is not attached to page document "
					+ s.getStackTrace());
		} catch (WebDriverException w) {
			System.out.println(strText + w.getStackTrace());
		}
	}
     
    public boolean invisibilityOfElement(WebElement we) {
		boolean flag = true;
		try {
			if (we.isDisplayed()) {
				System.out.println("Pass------------> Element is visible");
				flag = false;
			}
		} catch (NullPointerException ex) {
			System.out.println("Fail------------> Element is not visible");

		} catch (NoSuchElementException ex1) {
			flag = true;
			System.out.println("Fail------------> Element is not visible");
		}
		return flag;
	}
    
    public String getProperties(String propKey, String propertiesFileName) {
        try {
          String fileProperties = ClassLoader.getSystemResource(propertiesFileName).getFile();
          FileInputStream fis = new FileInputStream(fileProperties);
          Properties prop = new Properties();
          prop.load(fis);
          return prop.getProperty(propKey);
        } catch (IOException e) {
          e.printStackTrace();
          return null;
        }
      }
    
    protected String getCssValue(WebElement ele, String value) {
		String str = null;
		try {
			explicitWait(ele, 20);
			str = ele.getCssValue(value).trim();
			Log.info("Returned text of Element as ");
		} catch (ElementNotVisibleException en) {
			Log.info(
					"FAIL");
		} catch (StaleElementReferenceException s) {
			Log.info(
					"FAIL");
		}
		return str;
	}

    /**
	 * Description : Function to check Field is visible or not.
	 * 
	 * @param we
	 *            webelement.
	 */
	public void elementIsVisiblePass(WebElement we) {
		if (we.isDisplayed()) {
			Log.info("Mentioned element is present  " + we);
		} else {
			Log.info("Click " + we.getText() + we.getAttribute("name")
					+ " is not present in the DOM  ");
		}
	}

	/**
	 * Description :- Function to compare actual result with Expected result.
	 * 
	 * @param actual
	 *            :- Actual Result value.
	 * @param expected
	 *            :- Expected Result value.
	 */
	public void verifyAssert(String actual, String expected) {
		if (actual.equals(expected)) {
			Log.info("PASS");
		} else {
			Log.info("FAIL");
		}
	}

	/**
	 * Description function to get the current url.
	 * 
	 * @return url.
	 */

	public String currentUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * Function to click on capcha webelement.
	 * 
	 * @param submitButton
	 *            webelement of submit button.
	 * @param captchaFrameElement
	 *            frame name.
	 * @param captchaCheckBox
	 *            webelement of checkbox capcha.
	 */

	public void clickRecaptcha(WebElement submitButton,
			WebElement captchaFrameElement, WebElement captchaCheckBox) {
		clickOnCaptchaBySwitchingFrame(captchaFrameElement, captchaCheckBox);
		try {
			Thread.sleep(4000);
		} catch (UnhandledAlertException uae) {
			uae.printStackTrace();
			System.err.println("Google Captcha connection error: "
					+ driver.switchTo().alert().getText());
			driver.switchTo().alert().accept();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Description: function to click on capcha by switching frame.
	 * 
	 * @param frameEle
	 *            Capcha frame element.
	 * @param captchaCheckBoxEle
	 *            capcha checkbox element.
	 */

	public void clickOnCaptchaBySwitchingFrame(WebElement frameEle,
			WebElement captchaCheckBoxEle) {
		switchFrame(frameEle, "Switch to Captcha Frame");
		click(captchaCheckBoxEle, "Click on Captcha");
		driver.switchTo().defaultContent();
	}

	/**
	 * Description: Function is to switch frame.
	 * 
	 * @param frameElement
	 *            frame webelement.
	 * @param strDescription
	 *            description of the frame element.
	 */

	public void switchFrame(WebElement frameElement, String strDescription) {
		try {
			driver.switchTo().frame(frameElement);
		} catch (NoSuchFrameException | StaleElementReferenceException n) {
			Log.info("exception occured.....!");
		}
	}

	/**
	 * Description: Function to put explicit wait time.
	 * 
	 * @param ele
	 *            Element to put wait.
	 * @param t1
	 *            time in milli seconds.
	 */

	protected void explicitWait(WebElement ele, long t1) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, t1);
			wait.until(ExpectedConditions.visibilityOf(ele));
			Log.info(ele.getText() + ele.getAttribute("name") + " is Visible");
			System.out.println("Element Present" + ele.getText());
		} catch (NoSuchElementException t) {
			Log.info("Unable to Locate the Element " + t.getStackTrace());
		} catch (WebDriverException w) {
			Log.info(w.getStackTrace());
		}
	}

	/**
	 * Function to perform click action.
	 * 
	 * @param ele
	 *            element on which click is performed.
	 * @param strDescription
	 *            description in String.
	 */

	protected void click(WebElement ele, String strDescription) {
		try {
			explicitWait(ele, 10);
			ele.click();
			Log.info(strDescription);
		} catch (ElementNotVisibleException en) {
			Log.info("Fail to " + strDescription
					+ " as the Element is present in DOM but not visible "
					+ en.getStackTrace());
		} catch (StaleElementReferenceException s) {
			Log.info("Fail to " + strDescription
					+ " as the Element is not attached to page document "
					+ s.getStackTrace());
		} catch (WebDriverException w) {
			try {
				clickOnElementUsingJavaSciptExecutor(ele);
			} catch (Exception e) {
				Log.info(strDescription + w.getStackTrace());
			}
		}
	}

	/**
	 * Description: Function to click on Element using JavscriptExecutor.
	 * 
	 * @param webElement
	 *            Element on which the function is performed.
	 */

	public void clickOnElementUsingJavaSciptExecutor(WebElement webElement) {
		explicitWait(webElement, 2000);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", webElement);
	}

	/**
	 * Description: This function will return all the field names present in UI.
	 * 
	 * @param fieldnames
	 *            fields in UI
	 * @return List of Strings.
	 */

	public List<String> checkAllErrorMessagePresentInUiActual(
			WebElement[] fieldnames) {
		ArrayList<Set<String>> actualresult = new ArrayList<Set<String>>();
		List<String> actualresult1 = new ArrayList<String>();
		List<String> actualresult2 = new ArrayList<String>();

		int size = fieldnames.length;
		for (int i = 0; i < fieldnames.length; i++) {
			for (WebElement fieldnames1 : fieldnames) {
				String value = fieldnames1.getText();
				actualresult1.add(value);
			}
		}
		for (int j = 0; j < fieldnames.length; j++) {
			actualresult2.add(actualresult1.get(j));
		}
		return actualresult2;
	}

	/**
	 * Description: Function to check expected field values present in UI.
	 * 
	 * @param fieldNames
	 *            Array of strings
	 * @param fieldNamesValue
	 *            Array of string(field value)
	 * @return List of Strings
	 */

	public Set<String> checkFieldValuesPresentInUiExpected(String[] fieldNames,
			String[] fieldNamesValue) {
		Set<String> expectedresult = new LinkedHashSet<String>();
		for (String fieldNamesValue1 : fieldNamesValue) {
			expectedresult.add(fieldNamesValue1);
		}
		return expectedresult;
	}

	/**
	 * Description: This function will return all the field names present in UI.
	 * 
	 * @param fieldnames
	 *            names of fields in UI
	 * @return List of Strings.
	 */
	public Set<String> checkFieldValuesPresentInUiActual(WebElement[] fieldnames) {
		ArrayList<String> actualresult = new ArrayList<String>();
		Set<String> actualresult1 = new LinkedHashSet<String>();
		Set<String> hs = new HashSet<String>();
		int length = fieldnames.length;
		for (int i = 0; i < fieldnames.length; i++) {
			for (WebElement fieldnames1 : fieldnames) {
				String value = fieldnames1.getText();
				actualresult1.add(value);
			}
		}
		return actualresult1;
	}

	/**
	 * Description: This function will return all the values in dropdown.
	 * 
	 * @param locator
	 *            CSS locator for Dropdown
	 * @return String value.
	 */

	public String dropdownActualValue(String locator) {
		List<String> returnvalue = getAllOptionsDorpdown(By
				.cssSelector(locator));
		ArrayList<String> actualresult1 = new ArrayList<String>();
		ArrayList<String> actualresult2 = new ArrayList<String>();
		int size = returnvalue.size();
		for (int i = 0; i < size; i++) {
			String returnvalue2 = returnvalue.get(i).trim();
			actualresult1.add(returnvalue2);
			if (!(i == (size - 1))) {
				actualresult1.add(",");
			}
		}
		actualresult1.get(actualresult1.size() - 1).replaceAll(",", "");
		StringBuilder builder = new StringBuilder(actualresult1.size());
		for (Object ob : actualresult1) {
			builder.append(ob);
		}
		String val = builder.toString();
		return val;
	}

	/**
	 * Description: Function to select the drop down box.
	 * 
	 * @param dropDown
	 *            : WebElement for which we will perform the DropDown action.
	 * @param value
	 *            : String Value need to select from dropdown box.
	 */

	public void selectDropdown(WebElement dropDown, String value) {
		Select select = new Select(dropDown);
		select.selectByVisibleText(value.trim());
	}

	/**
	 * Description: This function get all the values present in dropdown.
	 * 
	 * @param locator
	 *            CSS locator for Dropdown.
	 * @return List of strings.
	 */

	public List<String> getAllOptionsDorpdown(By locator) {
		List<String> options = new ArrayList<String>();
		for (WebElement option : new Select(driver.findElement(locator))
				.getOptions()) {
			if (option.getAttribute("value") != "") {
				options.add(option.getText());
			}
		}
		return options;
	}

	/**
	 * Function to click on element using javascript executor.
	 * 
	 * @param webElement
	 *            WebElement to click.
	 * @param strDescription
	 *            Description of the webelement.
	 */

	public void clickOnElementUsingJavaScriptExecutor(WebElement webElement,
			String strDescription) {
		try {
			explicitWait(webElement, 20);
			Log.info(strDescription);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", webElement);
			Log.info("PASS");
		} catch (ElementNotVisibleException en) {
			Log.info("Fail to " + strDescription
					+ " as the Element is present in DOM but not visible "
					+ en.getStackTrace());
		} catch (StaleElementReferenceException s) {
			Log.info("Fail to " + strDescription
					+ " as the Element is not attached to page document "
					+ s.getStackTrace());
		} catch (WebDriverException w) {
			Log.info(strDescription + w.getStackTrace());
		}
	}

	/**
	 * Function to click on Radio Button and Check Box Element.
	 * 
	 * @param arrElement
	 *            Radio Button/CheckBox
	 * @param arrValue
	 *            Description of Radio Button/CheckBox.
	 */

	public void clickOnRadioButtonsAndCheckBox(WebElement arrElement,
			String arrValue) {
		try {
			WebElement ele = arrElement;
			List<WebElement> elementToClick = ele.findElements(By
					.tagName("label"));
			for (WebElement allCheck : elementToClick) {
				if (allCheck.getText().trim().equalsIgnoreCase(arrValue.trim())) {
					click(allCheck, "click on the element");
					Log.info("Click on Radiobutton/Checkbox for Group Websites Forms");
					break;
				}
			}
		} catch (NoSuchElementException n) {
			n.getStackTrace();
		}
	}

	/**
	 * Generic function to fill Data.
	 * 
	 * @param arrWebelement
	 *            WebElemnt on page.
	 * @param arrValue
	 *            String in Value.
	 * @throws InterruptedException
	 */

	public void fillData(WebElement arrWebelement, String arrValue)
			throws InterruptedException {
		setText(arrWebelement, arrValue.trim());
	}

	/**
	 * Generic function of the selecting drop down value from tab.
	 * 
	 * @param dropDown
	 *            Dropdown tab element.
	 * @param value
	 *            Value in String.
	 */

	public void selectDropdownUsingValue(WebElement dropDown, String value) {
		Select select = new Select(dropDown);
		select.selectByVisibleText(value.trim());
		Log.info(" Select the Drop Down" + value);
	}

	/**
	 * Function to select the drop down WebElement.
	 * 
	 * @param arrWebelement
	 *            Webelement.
	 * @param arrValue
	 *            String in value.
	 */

	public void selectDropDown(WebElement arrWebelement, String arrValue) {
		click(arrWebelement, "Click on drop down box");
		WebElement parentElement = arrWebelement;
		List<WebElement> listOfLi = parentElement
				.findElements(By.tagName("li"));
		for (WebElement li : listOfLi) {
			if (li.getText().trim().equalsIgnoreCase(arrValue.trim())) {
				click(li, "click on the element");
				Log.info("PASS");
				Log.info("Select the Dropdown for Group Websites Forms");
				break;
			}
		}
	}

	/**
	 * Generic function to put static wait.
	 * 
	 * @param time
	 *            Time in milli secs.
	 */

	protected void hardWait(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generic function to compare the value with Text.
	 * 
	 * @param errorMessages
	 *            webelement
	 * @param errorText
	 *            String in Value.
	 */

	public void compareTheValueWithGetText(WebElement errorMessages,
			String errorText) {
		verifyAssert(getText(errorMessages).trim(), errorText.trim());
	}

	/**
	 * Generic function to compare value with the attribute value.
	 * 
	 * @param errorMessages
	 *            Webelemnt on the webpage.
	 * @param attributevalue
	 *            String in Value.
	 * @param errorText
	 *            Error text in String.
	 */

	public void compareTheValueWithGetAttribute(WebElement errorMessages,
			String attributevalue, String errorText) {
		verifyAssert(getAttribute(errorMessages, attributevalue),
				errorText.trim());
	}

	/**
	 * Function to get the attribute value.
	 * 
	 * @param ele
	 *            WebElement on page.
	 * @param value
	 *            String value of element.
	 * @return String value of attribute.
	 */

	protected String getAttribute(WebElement ele, String value) {
		String str = null;
		try {
			explicitWait(ele, 20);
			str = ele.getAttribute(value).trim();
			Log.info("Able to Returned attribute of Element as---> "
					+ ele.getText() + ele.getAttribute("name--->") + str);
		} catch (ElementNotVisibleException en) {
			Log.info("Not able to get the attribute for " + ele.getText()
					+ ele.getAttribute("name")
					+ " is present in the DOM but not visible "
					+ en.getStackTrace());
		} catch (StaleElementReferenceException s) {
			Log.info(" Get attribute Element is not attached to page document "
					+ s.getStackTrace());
		}
		return str;
	}

	/**
	 * function to get the text for the webelement.
	 * 
	 * @param ele
	 *            WebElement.
	 * @return Value in String.
	 */

	protected String getText(WebElement ele) {
		String str = null;
		try {
			explicitWait(ele, 20);
			str = ele.getText().trim();
			// Reporter.reportEvent("PASS", "Returned text for the Element as "
			// + str);
			Log.info("Returned text of Element as " + str);
		} catch (ElementNotVisibleException en) {
			Log.info("Click " + ele.getText() + ele.getAttribute("name")
					+ " is present in DOM but not visible "
					+ en.getStackTrace());
		} catch (StaleElementReferenceException s) {
			Log.info(" Get text Element is not attached to page document "
					+ s.getStackTrace());
		}
		return str;
	}
}
