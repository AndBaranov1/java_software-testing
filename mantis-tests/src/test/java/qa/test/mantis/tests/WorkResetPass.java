package qa.test.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.UserData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.test.mantis.model.MailMessage;
import qa.test.mantis.model.Users;
import qa.test.mantis.model.UsersData;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class WorkResetPass extends TestBase {


    @BeforeMethod
    public void startMailServer (){
        app.mail().start();
    }

    @Test
    public void changePasswordTest () throws IOException, MessagingException {
        Users users = app.db().usersWithoutAdmin();
        UsersData user = users.iterator().next();
        String userName = user.getUserName();
        String email = user.getEmail();
        String newPassword = "password";
        app.session().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        app.goTo().managePage();
        app.goTo().usersManageTab();
        app.user().selectUser(user.getId());
        app.user().passwordReset();
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = app.mail().findConfirmationLink(mailMessages, email);
        long now = System.currentTimeMillis();
        String realname = String.format("realname%s", now);
        app.registration().finishChangingPassword(realname, confirmationLink, newPassword);
        assertTrue(app.newSession().login(userName, newPassword));
    }
    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}