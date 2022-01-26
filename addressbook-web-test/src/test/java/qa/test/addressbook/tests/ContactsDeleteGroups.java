package qa.test.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.Assert;

import org.testng.annotations.Test;
import qa.test.addressbook.model.GroupData;
import qa.test.addressbook.model.ContactData;
import qa.test.addressbook.model.Contacts;
import qa.test.addressbook.model.GroupData;
import qa.test.addressbook.model.Groups;

import java.util.HashSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactsDeleteGroups extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testContactDeleteGroups() {
    ContactData addC = selectedContact();
    Groups before = addC.getGroups();
    GroupData GroupToAdd = selectGroups(addC);
    app.contact().deleteContactGroup(addC, GroupToAdd);

    Contacts contacts = app.db().contacts();
    Groups after = null;
    for (ContactData contact : contacts) {
      if (contact.getId() == addC.getId()) {
        after = contact.getGroups();
      }
    }
    assertThat(after, equalTo(before.without(GroupToAdd)));
  }


  public GroupData selectGroups(ContactData contact) {
    return contact.getGroups().iterator().next();
  }

    public ContactData selectedContact () {
      Contacts contacts = app.db().contacts();
      Groups groups = app.db().groups();
      int i = contacts.size();
      for (ContactData contact : contacts) {
        if (contact.getGroups().size() > 0) {
          return contact;
        }
        if (contact.getGroups().size() == 0) {
          i = i - 1;
        }
      }
      if (i == 0) {
        app.contact().create(new ContactData().withMiddlename("middlename").withLastname("lastname").withNickname("TestYTesting")
                .inGroup(groups.iterator().next()));
        Contacts contacts2 = app.db().contacts();
        for (ContactData contact2 : contacts2) {
          if (contact2.getGroups().size() > 0) {
            return contact2;
          }
        }
        contacts = contacts2;
      }
      return contacts.iterator().next();
    }
  }