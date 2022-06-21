package qa.test.mantis.appmanager;

import org.openqa.selenium.By;

public class SessionHelper extends HelperBase {

    public SessionHelper(ApplicationManager app) {
        super(app);
    }
    public void login(String username, String password) {
        type(By.name("username"), username);
        click(By.xpath("//form[@id='login-form']/fieldset/input[2]"));
        type(By.name("password"), password);
        click(By.xpath("//input[@value='Login']"));
    }
}
