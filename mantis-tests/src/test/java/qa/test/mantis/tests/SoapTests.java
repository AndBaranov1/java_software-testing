package qa.test.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import qa.test.mantis.model.Issue;
import qa.test.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;


public class SoapTests extends TestBase {

  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
    skipIfNotFixed(1);
    Set<Project> projects = app.soap().getProjects();
    System.out.println(projects.size());
    for (Project project : projects) {
      System.out.println(project.getName());
    }
  }


  @Test(enabled = false)
  public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException{
    Set<Project> projects = app.soap().getProjects();
    Issue issue = new Issue().withSummary("Test issue")
            .withDescription("Test issue description").withProject(projects.iterator().next());
    Issue created = app.soap().addIssue(issue);
    app.soap().getIssueStatus(issue.getId());
    Assert.assertEquals(issue.getSummary(), created.getSummary());
  }

  @Test(enabled = true)
  public void testGetIssues() throws MalformedURLException, ServiceException, RemoteException {
    skipIfNotFixed(1);
    Set<Issue> issues = app.soap().getIssues();
    System.out.println(issues.size());
    for (Issue issue : issues) {
      System.out.println(issue.getSummary());
    }
  }

  @Test(enabled = true)
  public void testGetStatusIssue() throws MalformedURLException, ServiceException, RemoteException {
    skipIfNotFixed(4);
    int issueId = 4;
    String issueStatus = app.soap().getIssueStatus(issueId);
    System.out.println(issueStatus);
  }
}
