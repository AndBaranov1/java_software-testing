package qa.test.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {

    app.goTo().gotoContactPage();
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("Jero", "TestJons", "TestYTesting", "GameTestingPro", "Test", "Jon", "Jon");
    app.getContactHelper().createContact(contact);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
    Comparator<? super ContactData> byMiddlename = (f1, f2) -> String.CASE_INSENSITIVE_ORDER.compare(f1.getMiddlename(), f2.getMiddlename());
    before.sort(byMiddlename);
    after.sort(byMiddlename);
    Assert.assertEquals(before, after);
    Comparator<? super ContactData> byFname = (l1, l2) -> String.CASE_INSENSITIVE_ORDER.compare(l1.getFname(), l2.getFname());
    before.sort(byFname);
    after.sort(byFname);
    Assert.assertEquals(before, after);
  }
}
