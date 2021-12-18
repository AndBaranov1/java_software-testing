package qa.test.addressbook.tests;

import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigationHelper().gotoHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Jero", "TestJons", "TestYTesting", "GameTestingPro", "Test", "Jon", "Jon", null), true);
    }
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Jero", "TestJons", "TestYTesting", "GameTestingPro", "Test", "Jon", "Jon", null), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
    app.getContactHelper().selectContact();
  }
}
