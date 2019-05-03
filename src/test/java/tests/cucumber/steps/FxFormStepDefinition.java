package tests.cucumber.steps;

import Base.AiBPersonalFxFormPageObjectModel;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;

import tests.ThreadLocalDriver;

/**
 * Step definition class for AIB fx-form page.
 */

public class FxFormStepDefinition extends BaseSteps {

	public AiBPersonalFxFormPageObjectModel aibPersonalFxFormPageObjectModel;
	
	@Before
    public void setupLoginSteps () {
        System.out.println("Cucumber Before-login-test-cucumber");
        setupCucumber();
        aibPersonalFxFormPageObjectModel = new AiBPersonalFxFormPageObjectModel(ThreadLocalDriver.getTLDriver());
	}
	/**
 * Given statement.
 * @param branch name of the branch.
 * @throws MalformedURLException malformed url is occured.
 * @throws InterruptedException 
 */
	
  @Given("^that customer wants to enquire contact details about \"([^\"]*)\" for "
      + "ROI personal FX Form and enters same in searchbox$")
  public void roi_personal_FX_Form_and_enters_same_in_searchbox(String branch)
      throws MalformedURLException, InterruptedException {
	  aibPersonalFxFormPageObjectModel.navigate();
	  
	    
  }
/**
 * and statement.
 * 
 * @param branch name of the branch
 * @throws InterruptedException
 */

  
  @And("^Enters \"([^\"]*)\" details$") public void enters_detsils(String
  branch) throws InterruptedException {
  aibPersonalFxFormPageObjectModel.enterBranchDetails(branch); 
  }
 /**
	 * Then statement.
	 * 
	 * @param branch name of the branch.
	 */

  
  @Then("^customer is not provided with any \"([^\"]*)\" information$") 
  public void customer_is_not_provided_with_any_information(String branch) { 
	  boolean expected = true; 
      boolean actual = aibPersonalFxFormPageObjectModel.invisibilityBranch();
      assertEquals(expected, actual);
  
  }
 
  /**
* Teardown.
 */
 
 @After 
 public void quitDriver() { 
	 System.out.println("Tear down");
	 } 
 }

