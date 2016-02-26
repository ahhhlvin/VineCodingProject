package ahhhlvin.c4q.nyc.vinecodingproject;

import java.util.ArrayList;

/**
 * Created by alvin2 on 2/25/16.
 */
public class GitIssue {

    String issueTitle;
    String issueBody;
    ArrayList<IssueComment> commentsList;

    public GitIssue() {
    }

    public GitIssue(String issueTitle, String issueBody) {
        this.issueTitle = issueTitle;
        this.issueBody = issueBody;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueBody() {
        return issueBody;
    }

    public void setIssueBody(String issueBody) {
        this.issueBody = issueBody;
    }

    public ArrayList<IssueComment> getCommentsList() {
        return commentsList;
    }
}
