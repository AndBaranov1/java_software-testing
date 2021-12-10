package qa.test.addressbook.tests;

import org.testng.annotations.*;
import qa.test.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {

    app.gotoGroupPage();
    app.initGroupCreation();
    app.fillGroupForm(new GroupData("test_group", "test1", "test comment"));
    app.submitGroupCreation();
    app.returnToGroupPage();
    app.logoutGroupPage();
  }

}
