package ahhhlvin.c4q.nyc.vinecodingproject;

import java.util.ArrayList;

/**
 * Created by alvin2 on 2/25/16.
 */
public class GitIssue {

    String mIssueTitle;
    String mIssueBody;
    ArrayList<IssueComment> mCommentsList;

    public GitIssue() {
        mCommentsList = new ArrayList<>();
    }

    public GitIssue(String issueTitle, String issueBody) {
        this.mIssueTitle = issueTitle;
        this.mIssueBody = issueBody;
    }

    public String getmIssueTitle() {
        return mIssueTitle;
    }

    public void setmIssueTitle(String mIssueTitle) {
        this.mIssueTitle = mIssueTitle;
    }

    public String getmIssueBody() {
        return mIssueBody;
    }

    public void setmIssueBody(String mIssueBody) {
        this.mIssueBody = mIssueBody;
    }

    public ArrayList<IssueComment> getmCommentsList() {
        return mCommentsList;
    }
}
