package qa.test.mantis.tests;

import org.testng.Assert;
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

public class WorkResetPass extends TestBase {

    @BeforeMethod
    public void startMailServer (){
        app.mail().start();
    }

    @Test
    public void changePasswordTest () throws IOException, MessagingException {
        app.registration().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        Users users = app.db().usersWithoutAdmin();
        System.out.println("111" + users + "111");
        UsersData selectedUser = users.iterator().next();
        String username = selectedUser.getUserName();
        String email = selectedUser.getEmail();
        app.registration().resetPasswordUser(username);
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 60000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        String newPasswordForUser = "root";
        app.registration().finish(confirmationLink, newPasswordForUser);
        Assert.assertTrue(app.newSession().login(username, newPasswordForUser));
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