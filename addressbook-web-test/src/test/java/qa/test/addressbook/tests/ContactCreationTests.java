package qa.test.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {

    app.getNavigationHelper().gotoContactPage();
    //int before = app.getGroupHelper().getContactCount();
    app.getContactHelper().createContact(new ContactData("Jero", "TestJons", "TestYTesting", "GameTestingPro", "Test", "Jon", "Jon"));
    //int after = app.getGroupHelper().getContactCount();
    //Assert.assertEquals(after, before + 1);
  }
}
