package qa.test.addressbook;

import org.testng.annotations.*;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {

    gotoGroupPage();
    initGroupCreation();
    fillGroupForm(new GroupData("test_group", "test1", "test comment"));
    submitGroupCreation();
    returnToGroupPage();
    logoutGroupPage();
  }

}
