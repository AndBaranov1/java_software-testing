package qa.test.addressbook.tests;

import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {

    app.getNavigationHelper().gotoContactPage();
    app.getContactHelper().fillContactForm(new ContactData("Jero", "TestJons", "TestYTesting", "GameTestingPro", "Test", "Jon", "Jon"));
    app.getContactHelper().submitContactCreation();
    app.getContactHelper().returnToHomePage();
  }
}
