package qa.test.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.test.mantis.model.MailMessage;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class WorkResetPass extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }
  @Test
  public void testResetPass() throws IOException, MessagingException {
    long now = System.currentTimeMillis();
    app.registration().userEnter(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    String resUser = app.getProperty("g.User");
    app.registration().goToUserPage(resUser);
    app.registration().ResetPassword(resUser);
    String email = String.format(app.getProperty("g.Email"), now);
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    String password = app.getProperty("g.Password");
    app.registration().finish(confirmationLink, password);
    app.registration().userEnter(resUser, password);
    assertTrue(app.newSession().login(resUser, password));
  }
  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }
  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
