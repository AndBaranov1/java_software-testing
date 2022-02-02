package qa.test.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;
import qa.test.addressbook.model.Contacts;
import qa.test.addressbook.model.GroupData;
import qa.test.addressbook.model.Groups;

import java.util.Collection;
import java.util.HashSet;

public class ContactsInGroups extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
    if (app.db().contacts().size() == 0) {
      app.goTo().ContactMDPage();
      app.contact().create(new ContactData()
              .withMiddlename("Jero")
              .withLastname("TestJons")
              .withFname("Jon")
              .withNickname("TestYTesting").withTitle("GameTestingPro").withCompany("Test").withAddress("Jon"));
    }
  }

  @Test
  public void testContactInGroups() {
    Contacts before = app.db().contacts();
    Groups groups = app.db().groups();
    ContactData modifyedContact = selectedContact();
    app.contact().ContactInGroup(modifyedContact.inGroup(groups.iterator().next()));
    Contacts after = app.db().contacts();

    int max = 0;
    for (ContactData c : after) {
      if (c.getId() > max) {
        max = c.getId();
      }
    }
    modifyedContact.setId(max);
    before.add(modifyedContact);
  }

  public ContactData selectedContact() {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() < groups.size()) {
        return contact;
      } else if (contact.getGroups().size() > groups.size()) {
        Contacts contacts2 = app.db().contacts();
        for (ContactData contact2 : contacts2) {
          selectedContact();
          ContactData modifyedContact = selectedContact();
          app.contact().ContactInGroup(modifyedContact.inGroup(groups.iterator().next()));
          return contact2;
        }
      } else if ((contact.getGroups().size() == groups.size())) {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test_group1").withHeader("test1").withFooter("test comment"));
        app.goTo().ContactMDPage();
        selectedContact();
        ContactData modifyedContact = selectedContact();
        app.contact().ContactInGroup(modifyedContact.inGroup(groups.iterator().next()));
      }
    }
    return contacts.iterator().next();
  }

  public GroupData selectedGroup(ContactData contact) {
    Groups all = app.db().groups();
    Collection<GroupData> freeGroups = new HashSet<GroupData>(all);
    freeGroups.removeAll(contact.getGroups());
    return freeGroups.iterator().next();
  }
}