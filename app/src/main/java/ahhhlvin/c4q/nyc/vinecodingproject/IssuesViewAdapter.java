package ahhhlvin.c4q.nyc.vinecodingproject;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by alvin2 on 2/25/16.
 */
public class IssuesViewAdapter extends RecyclerView.Adapter<IssuesViewAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<GitIssue> mIssues;
    private Context mContext;
    AlertDialog.Builder commentsBuilder;

    public IssuesViewAdapter(Context mContext, ArrayList<GitIssue> list) {
        this.mIssues = list;
        this.mContext = mContext;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView issueTitleTV;
        public TextView issueBodyTV;

        public ViewHolder(View v) {
            super(v);
            issueTitleTV = (TextView) v.findViewById(R.id.issue_title_tv);
            issueBodyTV = (TextView) v.findViewById(R.id.issue_body_tv);

        }
    }


    @Override
    public IssuesViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_layout, parent, false);
        return new ViewHolder(v);    }

    @Override
    public void onBindViewHolder(IssuesViewAdapter.ViewHolder holder, int position) {
        final GitIssue issueData = mIssues.get(position);

        String message = "";
        for (int i = 0; i < issueData.getCommentsList().size(); i++) {
            message += issueData.getCommentsList().get(i) + "\n";
        }


        commentsBuilder = new AlertDialog.Builder(mContext);
        commentsBuilder.setMessage(message);

        String issueTitle = issueData.getIssueTitle();
        String issueBody = issueData.getIssueBody();
        holder.issueTitleTV.setText(issueTitle);
        holder.issueBodyTV.setText(issueBody);

    }

    @Override
    public void onClick(View v) {
        commentsBuilder.show();
    }

    @Override
    public int getItemCount() {
        return mIssues.size();
    }


}
