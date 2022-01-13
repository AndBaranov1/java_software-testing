package qa.test.addressbook.tests;

import org.testng.annotations.Test;
import qa.test.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneAddressEmailTests extends  TestBase{

  @Test
  public void testContactPhones() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    assertThat(contact.getAddress(), equalTo(mergeAddresses(contactInfoFromEditForm)));
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getPhoneHome(), contact.getPhoneMobile(), contact.getPhoneWork()).stream().filter((s) -> ! s.equals(""))
            .map(ContactPhoneAddressEmailTests::cleaned).collect(Collectors.joining("\n"));
  }

  public static String cleaned (String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }

  private String mergeAddresses(ContactData contact) {
    return Arrays.asList(contact.getAddress()).stream().filter((s) -> !s.equals("")).map(ContactPhoneAddressEmailTests::cleanedAddress)
            .collect(Collectors.joining("\n"));
  }

  public static String cleanedAddress(String address) {
    return address.replaceAll("\\s", "");
  }

  private static String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactPhoneAddressEmailTests::cleanedEmail)
            .collect(Collectors.joining("\n"));
  }

  public static String cleanedEmail(String email) {
    return email.replaceAll("\\s", "");
  }
}
