package qa.test.mantis.appmanager;

import org.openqa.selenium.By;

public class UsersHelper extends HelperBase{
    public UsersHelper(ApplicationManager app) {
        super(app);
    }
    public void selectUser (int userId) {
        click(By.cssSelector("a[href='manage_user_edit_page.php?user_id=" + userId + "']"));
    }

    public void passwordReset (){
        click(By.xpath("//form[@id='manage-user-reset-form']/fieldset/span/input"));
    }
}
