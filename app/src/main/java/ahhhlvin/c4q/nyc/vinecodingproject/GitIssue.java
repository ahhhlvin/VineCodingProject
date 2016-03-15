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

    public List<IssueComment> getmCommentsList() {
        return mCommentsList;
    }
}
