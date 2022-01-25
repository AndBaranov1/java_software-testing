package qa.test.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import qa.test.mantis.tests.TestBase;

public class RegistrationHelper extends HelperBase {

  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(By.cssSelector("input[value='Зарегистрироваться']"));
  }
  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("input[value='Изменить учетную запись']"));
  }
  public void adminEnter(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), username);
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Войти']"));
  }
  public void userEnter(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), username);
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Войти']"));
  }

  public void goToUserPage(String username) {
    wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
    click(By.xpath("//*[@id=\"menu-items\"]/li[7]/a"));
    click(By.xpath("//*[@id=\"manage-menu\"]/ul/li[1]/a"));
    type(By.name("username"), username);
  }

  public void ResetPassword(String username) {
    wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
    click(By.linkText(username));
    click(By.cssSelector("input[value='Сбросить пароль']"));
  }
}
