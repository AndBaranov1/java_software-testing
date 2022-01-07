package qa.test.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.test.addressbook.model.GroupData;
import qa.test.addressbook.model.Groups;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    // Метод group().isThereAGroup проверяет наличие групп, замена на list().size() == 0
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test_group"));
    }
  }

  @Test
  public void testGroupModification() {
   Groups before = app.group().all();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("test_group1").withHeader("test1").withFooter("test comment");
    app.group().modify(group);
    Groups after = app.group().all();
    assertEquals(after.size(), before.size());

    //before.remove(modifiedGroup);
    //before.add(group);
    /*
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
     */
    //Assert.assertEquals(before, after);

    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
  }




}
