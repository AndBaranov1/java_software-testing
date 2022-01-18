package qa.test.addressbook.tests;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.json.TypeToken;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;
import qa.test.addressbook.model.Contacts;

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
        //list.add(new Object[] {new ContactData().withMiddlename(split[0]).withAddress(split[1]).withLastname(split[2])
        // .withPhoneHome(split[3]).withNickname(split[4]).withPhoneMobile(split[5]).withEmail(split[6])});
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
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
      return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "validContactsFromJson")
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