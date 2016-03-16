package ahhhlvin.c4q.nyc.vinecodingproject;

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

    private Context mContext;
    private List<GitIssue> mIssues;

    public IssuesViewAdapter(Context context, List<GitIssue> list) {
        mContext = context;
        mIssues = list;
    }

    public void addIssues(List<GitIssue> issues) {
        // where do you add the data???
        mIssues.addAll(issues);
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
        String issueTitle = issueData.getmIssueTitle();
        String issueBody = issueData.getmIssueBody();
        holder.title.setText(issueTitle);
        holder.body.setText(issueBody);
    }

    @Override
    public int getItemCount() {
        return mIssues.size();
    }

    // I know you don't want to name it as static but RecyclerView.ViewHolder is STATIC~~~ lol
    public static class IssueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            if (mIssues.get(getAdapterPosition()).getmCommentsList().size() > 0) {
                for (int i = 0; i < mIssues.get(getAdapterPosition()).getmCommentsList().size(); i++) {
                    message += mIssues.get(getAdapterPosition()).getmCommentsList().get(i).getmBody() + "\n";
                }
            } else {
                message = "No comments available for issue";
            }
            commentBuilder.setMessage(message).setCancelable(true).create().show();
        }
    }
}
