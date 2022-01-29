package qa.test.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("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");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("AndBaranov1", "java_software-testing")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
