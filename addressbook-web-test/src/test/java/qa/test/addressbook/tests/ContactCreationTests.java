package qa.test.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;
import qa.test.addressbook.model.Contacts;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts () {
    List<Object[]> list = new ArrayList<Object[]>();
    File photo = new File("src/test/resources/stru.png");
    list.add(new Object[] {new ContactData().withMiddlename("Jero1").withLastname("TestJons 1").withFname("Jon 1")});
    list.add(new Object[] {new ContactData().withMiddlename("Jero2").withLastname("TestJons 2").withFname("Jon 2")});
    list.add(new Object[] {new ContactData().withMiddlename("Jero3").withLastname("TestJons 3").withFname("Jon 3")});
    return list.iterator();
  }

  @Test(dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) {
    app.goTo().ContactPage();
    Contacts before = app.contact().all();
   // ContactData contact = new ContactData().withMiddlename("Jero").withLastname("TestJons")
           // .withNickname("TestYTesting").withTitle("GameTestingPro").withCompany("Test").withAddress("Jon").withFname("Jon")
         //   .withEmail("1testemail@mail.ru").withAddress("test_address").withPhoneHome("1111").withPhoneMobile("799955522")
          //  .withPhoneWork("8888555").withPhoto(photo);
    app.contact().create(contact);
    Contacts after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() + 1);
    //contact.withId(app.contact().findMaxId());

    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo
            (before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
}