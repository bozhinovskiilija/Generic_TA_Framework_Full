package utils;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

public class JiraServiceProvider {

    public JiraClient jira;

    public String project;

    public JiraServiceProvider(String jiraUrl, String username, String password, String project) {

        BasicCredentials creds = new BasicCredentials(username, password);

        jira = new JiraClient(jiraUrl, creds);

        this.project = project;

    }

    public void createJiraTicket(String issueType, String summary, String description, String reporterName) {

        try {

            //Component.FluentCreate fleuntCreate = jira.createIssue(project, issueType);

            Issue.FluentCreate fleuntCreate = jira.createIssue(project, issueType);

            fleuntCreate.field(Field.SUMMARY, summary);

            fleuntCreate.field(Field.DESCRIPTION, description);

            Issue issue = fleuntCreate.execute();

            System.out.println("A new issue is created in JIRA with ID: " + issue);

        } catch (JiraException e) {

            e.printStackTrace();

        }

    }

}
