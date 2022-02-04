package qa.test.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;
import qa.test.addressbook.model.Contacts;
import qa.test.addressbook.model.GroupData;
import qa.test.addressbook.model.Groups;

import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactsInGroups extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    Groups groups = app.db().groups();
    if (app.db().contacts().size() == 0) {
      app.goTo().ContactMDPage();
      app.contact().create(new ContactData()
              .withMiddlename("Jero")
              .withLastname("TestJons")
              .withFname("Jon")
              .withNickname("TestYTesting").withTitle("GameTestingPro").withCompany("Test").withAddress("Jon"));
    } else if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testContactInGroups() {
    app.goTo().homePage();
    ContactData contact = selectedContact();
    Groups before = contact.getGroups();
    GroupData groupForAdd = selectedGroup(contact);
    app.contact().addContactInGroup(contact, groupForAdd);
    Groups after = app.db().getContactFromDb(contact.getId()).getGroups();
    equalTo(before.withAdded(groupForAdd));
    assertThat(after, equalTo(before.withAdded(groupForAdd)));
  }

  public ContactData selectedContact() {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() < groups.size()) {
        return contact;
      }
    }
    app.goTo().groupPage();
    app.group().create(new GroupData().withName("test1"));
    app.goTo().homePage();
    return contacts.iterator().next();
  }

  public GroupData selectedGroup(ContactData contact) {
    Groups all = app.db().groups();
    Collection<GroupData> freeGroups = new HashSet<GroupData>(all);
    freeGroups.removeAll(contact.getGroups());
    return freeGroups.iterator().next();
  }
}