package qa.test.mantis.appmanager;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase{

    public NavigationHelper(ApplicationManager app) {
        super(app);
    }
    public void managePage() {
        click(By.xpath("//div[@id='sidebar']/ul/li[7]/a/i"));
    }

    public void usersManageTab() {
        click(By.linkText("Manage Users"));


    }
}
