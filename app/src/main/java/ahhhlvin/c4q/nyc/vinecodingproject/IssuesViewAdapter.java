package ahhhlvin.c4q.nyc.vinecodingproject;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alvin2 on 2/25/16.
 */
public class IssuesViewAdapter extends RecyclerView.Adapter<IssuesViewAdapter.ViewHolder> {

    private ArrayList<GitIssue> mIssues;
    AlertDialog.Builder commentsBuilder;

    public IssuesViewAdapter(ArrayList<GitIssue> list) {
        this.mIssues = list;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView issueTitleTV;
        public TextView issueBodyTV;

        public ViewHolder(View v) {
            super(v);
            issueTitleTV = (TextView) v.findViewById(R.id.issue_title_tv);
            issueBodyTV = (TextView) v.findViewById(R.id.issue_body_tv);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // For tesing purposes
//            Log.d("DEBUG_TAG", "ITEM " + getAdapterPosition() + " HAS BEEN CLICKED");

            String message = "";
            for (int i = 0; i < mIssues.get(getAdapterPosition()).getCommentsList().size(); i++) {
                message += mIssues.get(getAdapterPosition()).getCommentsList().get(i).getBody() + "\n";
            }

            commentsBuilder = new AlertDialog.Builder(v.getContext(), R.style.dialog);
            commentsBuilder.setMessage(message);
            commentsBuilder.setCancelable(true);
            commentsBuilder.create().show();
        }
    }


    @Override
    public IssuesViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(IssuesViewAdapter.ViewHolder holder, int position) {
        final GitIssue issueData = mIssues.get(position);

        String issueTitle = issueData.getIssueTitle();
        String issueBody = issueData.getIssueBody();
        holder.issueTitleTV.setText(issueTitle);
        holder.issueBodyTV.setText(issueBody);

    }


    @Override
    public int getItemCount() {
        return mIssues.size();
    }


}
