package qa.test.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;
import qa.test.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      app.goTo().groupPage();
      app.contact().create(new ContactData().withMiddlename("Jero").withLastname("TestJons").withNickname("TestYTesting")
              .withTitle("GameTestingPro").withCompany("Test").withAddress("Jon").withFname("Jon"));
    }
  }

  @Test
  public void testContactDeletion() {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.goTo().homePage();
    app.contact().delete(deletedContact);
    Contacts after = app.db().contacts();
    Assert.assertEquals(after.size(), before.size() - 1);
    assertThat(after, equalTo(before.without(deletedContact)));

    verifyContactListInUI();
  }
}