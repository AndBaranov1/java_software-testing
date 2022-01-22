package qa.test.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;
import qa.test.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withMiddlename("Jero").withLastname("TestJons").withNickname("TestYTesting").withTitle("GameTestingPro").
              withCompany("Test").withAddress("Jon").withFname("Jon"));
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId())
            .withMiddlename("Jero").withLastname("TestJons").withNickname("TestYTesting").withTitle("GameTestingPro")
            .withCompany("Test").withAddress("Jon").withFname("Jon");
    app.goTo().homePage();
    app.contact().modify(contact);
    Contacts after = app.db().contacts();
    Assert.assertEquals(after.size(), before.size());

    assertEquals(after.size(), before.size());
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));

    verifyContactListInUI();
  }
}