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

    public void createJiraTicket(String issueType, String summary, String description, String assignee) {

        try {

            Issue.FluentCreate fleuntCreate = jira.createIssue(project, issueType);

            fleuntCreate.field(Field.SUMMARY, summary);
            fleuntCreate.field(Field.DESCRIPTION, description);
            fleuntCreate.field(Field.ASSIGNEE,assignee);
           // fleuntCreate.field(Field.LABELS,label);
           // fleuntCreate.field(Field.COMMENT,comment);

            Issue issue = fleuntCreate.execute();
            issue.addComment("No problem. We'll get right on it! @ilija ");

            System.out.println("A new issue is created in JIRA with ID: " + issue);

        } catch (JiraException e) {
            e.printStackTrace();
        }

    }

}
