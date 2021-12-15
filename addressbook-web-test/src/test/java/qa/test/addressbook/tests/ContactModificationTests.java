package qa.test.addressbook.tests;

import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Jero", "TestJons", "TestYTesting", "GameTestingPro", "Test", "Jon", "Jon"));
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
    app.getContactHelper().selectContact();



  }
}
