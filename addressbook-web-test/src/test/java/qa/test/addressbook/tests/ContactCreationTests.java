package qa.test.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.json.TypeToken;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;
import qa.test.addressbook.model.Contacts;
import qa.test.addressbook.model.GroupData;
import qa.test.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXml () throws IOException {
   // File photo = new File("src/test/resources/stru.png");
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
      return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    //File photo = new File("src/test/resources/stru.png");
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null){
        json += line;
        line = reader.readLine();
      }
      Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
      return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }
  }

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) {
    Groups groups = app.db().groups();
    app.goTo().homePage();
    app.goTo().ContactPage();
    Contacts before = app.db().contacts();
    app.contact().create(contact.inGroup(groups.iterator().next()));
   // ContactData contact = new ContactData().withMiddlename("Jero").withLastname("TestJons")
           // .withNickname("TestYTesting").withTitle("GameTestingPro").withCompany("Test").withAddress("Jon").withFname("Jon")
         //   .withEmail("1testemail@mail.ru").withAddress("test_address").withPhoneHome("1111").withPhoneMobile("799955522")
          //  .withPhoneWork("8888555").withPhoto(photo);
    //app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    //Assert.assertEquals(after.size(), before.size() + 1);
    //contact.withId(app.contact().findMaxId());
/*
    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    assertThat(after.size(), equalTo(before.size() + 1));

 */
    assertThat(after, equalTo
            (before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    verifyContactListInUI();
  }
  @Test
  public void testContactInGroups(){
    Groups groups = app.db().groups();
    File photo = new File("src/test/resources/stru.png");
    ContactData newContact = new ContactData().withFname("fname").withLastname("lastname").withPhoto(photo)
            .inGroup(groups.iterator().next());
    app.goTo().homePage();
    app.contact().initContactCreation();
    app.contact().fillContactForm(newContact, true);
    app.contact().submitContactCreation();
    app.contact().returnToHomePage();
  }
}