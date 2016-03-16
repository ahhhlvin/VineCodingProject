package ahhhlvin.c4q.nyc.vinecodingproject;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alvin2 on 2/25/16.
 */
public class IssuesViewAdapter extends RecyclerView.Adapter<IssuesViewAdapter.IssueViewHolder> {

    private List<GitIssue> mIssues;
    private Context mContext;

    public IssuesViewAdapter(List<GitIssue> list, Context context) {
        mIssues = list;
        mContext = context;
    }

    public void addIssues(List<GitIssue> list) {
        mIssues.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.issue_layout, parent, false);
        return new IssueViewHolder(v);
    }

    @Override
    public void onBindViewHolder(IssueViewHolder holder, int position) {
        final GitIssue issueData = mIssues.get(position);
        String issueTitle = issueData.getIssueTitle();
        String issueBody = issueData.getIssueBody();
        holder.title.setText(issueTitle);
        holder.body.setText(issueBody);
    }

    @Override
    public int getItemCount() {
        return mIssues.size();
    }

    public class IssueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView title;
        protected TextView body;

        public IssueViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.issue_title_tv);
            body = (TextView) v.findViewById(R.id.issue_body_tv);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder commentsBuilder = new AlertDialog.Builder(v.getContext(), R.style.dialog);
            String message = "";

            if (mIssues.get(getAdapterPosition()).getCommentsList().size() > 0) {
                for (int i = 0; i < mIssues.get(getAdapterPosition()).getCommentsList().size(); i++) {
                    message += mIssues.get(getAdapterPosition()).getCommentsList().get(i).getCommentBody() + "\n";
                }
            } else {
                message = "No comments available for issue";
            }
            commentsBuilder.setMessage(message).setCancelable(true).create().show();
        }
    }
}
