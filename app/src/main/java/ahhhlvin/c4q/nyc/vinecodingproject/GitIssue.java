package ahhhlvin.c4q.nyc.vinecodingproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alvin2 on 2/25/16.
 */
public class GitIssue {

    private String mIssueTitle;
    private String mIssueBody;
    List<IssueComment> mCommentsList;

    public GitIssue() {
        mCommentsList = new ArrayList<>();
    }

    public GitIssue(String issueTitle, String issueBody) {
        mIssueTitle = issueTitle;
        mIssueBody = issueBody;
    }

    public String getIssueTitle() {
        return mIssueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        mIssueTitle = issueTitle;
    }

    public String getIssueBody() {
        return mIssueBody;
    }

    public void setIssueBody(String issueBody) {
        mIssueBody = issueBody;
    }

    public List<IssueComment> getCommentsList() {
        return mCommentsList;
    }
}
