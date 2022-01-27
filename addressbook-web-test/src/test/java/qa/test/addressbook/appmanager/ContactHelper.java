package qa.test.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import qa.test.addressbook.model.ContactData;
import qa.test.addressbook.model.Contacts;
import qa.test.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void homePage() {
    //click(By.linkText("home page"));
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home"));
  }

  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("address"), contactData.getAddress());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompany());
    type(By.name("home"),contactData.getPhoneHome());
    type(By.name("mobile"),contactData.getPhoneMobile());
    type(By.name("work"),contactData.getPhoneWork());
    type(By.name("email"),contactData.getEmail());
    attach(By.name("photo"), contactData.getPhoto());

    if (creation) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void initContactModification(int id) {
    click(By.xpath("//a[@href='edit.php?id=" + id + "']"));
  }

  public void deleteSelectedContact() {
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
    wd.findElement(By.cssSelector("div.msgbox"));
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    contactCache = null;
    homePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContact();
    contactCache = null;

  }

  public void modify(ContactData contact) {
    selectContactById(contact.getId());
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    contactCache = null;
    homePage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.xpath("//tr[@name = 'entry']"));
    for (WebElement element : elements){
      List<WebElement> cells = element.findElements(By.xpath(".//td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String fname = cells.get(2).getText();
      String lastname = cells.get(1).getText();
      String address = cells.get(3).getText();
      String phoneMobile = element.findElement(By.xpath("//td[6]")).getText();
      String email = element.findElement(By.tagName("a")).getText();
      contacts.add(new ContactData().withId(id).withLastname(lastname)
              .withFname(fname).withAddress(address).withPhoneMobile(phoneMobile).withEmail(email));
    }
    return  contacts;
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if(contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String fname = cells.get(2).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      contactCache.add(new ContactData().withId(id).withLastname(lastname)
              .withFname(fname).withAddress(address).withAllEmails(allEmails).withAllPhones(allPhones));
    }
    return new Contacts(contactCache);
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String fname = wd.findElement(By.name("firstname")).getAttribute("value");
    String middlename = wd.findElement(By.name("middlename")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String secondphone = wd.findElement(By.name("phone2")).getAttribute("value");

    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withLastname(lastname).withMiddlename(middlename)
            .withFname(fname).withAddress(address).withPhoneHome(home).withPhoneMobile(mobile).withPhoneWork(work)
            .withEmail(email).withEmail2(email2).withEmail3(email3).withSecondphone(secondphone);
  }

  private void initContactModificationById(int id) {
    wd.findElement(By.xpath("//a[@href='edit.php?id=" + id + "']")).click();
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public void ContactInGroup(ContactData contact) {
    initContactCheckbox(contact.getId());
    contactCache = null;
    homePage();
  }
  public void initContactCheckbox(int id) {
    wd.findElement(By.xpath("//input[@id="+ id +"]")).click();
    wd.findElement(By.xpath("//input[@value='Add to']")).click();
  }
  public void deleteContactGroup(ContactData contact, GroupData group){
    ContactDeletedGroup(group);
    selectContactById(contact.getId());
    initContactDelete();
  }

  public void addContactInGroup(ContactData contact, GroupData group) {
    selectContactById(contact.getId());
   // selectGroupInList(group.getName());
    new Select(wd.findElement(By.name("to_group"))).selectByValue(String.valueOf(group.getId()));
    click(By.name("add"));
    //initAddToGroup();
    contactCache = null;
  }

  private void selectGroupInList(String groupName) {
    new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(groupName);
  }
  private void initAddToGroup() {
    wd.findElement(By.name("add")).click();
  }

  public void ContactDeletedGroup(GroupData group) {
    new Select(wd.findElement(By.xpath("//select[@name='group']"))).selectByValue(String.valueOf(group.getId()));
  }
  public void initContactDelete(){
    wd.findElement(By.xpath("//input[@name='remove']")).click();
  }

  public void DellContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getPhoneMobile());
    type(By.name("email"), contactData.getEmail());
    attach(By.name("photo"), contactData.getPhoto());

    if (creation) {
      if (contactData.getGroups().size() > 0){
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("group"))).
                selectByValue(contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("group")));
    }
  }

/*
  //Метод перенесен из сценария создания контакта
  public int findMaxId() {
    //Сравнение идентификатора. Устанавливаем цикл по всем элементам
    List<ContactData> after = list();
    int max = 0;
    for (ContactData g : after) {
      if (g.getId() > max) {
        max = g.getId();
      }
    }
    //Все контакты в сравниваемых списках теперь имеют Id. Сравнивать можно без учета порядка.
    return max;
  }
 */
}