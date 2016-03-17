package ahhhlvin.c4q.nyc.vinecodingproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class IssuesActivity extends AppCompatActivity {

    private static final String REQUEST_METHOD_GET = "GET";
    private static final String ENDPOINT = "https://api.github.com/repos/rails/rails/issues";
    private List<GitIssue> mListIssues;
    private RecyclerView mRecyclerViewIssues;
    private IssuesViewAdapter mAdapter;
    private String mResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListIssues = new ArrayList<>();
        mRecyclerViewIssues = (RecyclerView) findViewById(R.id.issues_view);

        new GetDataTask().execute();

        if (mListIssues != null) {
            mAdapter = new IssuesViewAdapter(mListIssues, getApplicationContext());
            mRecyclerViewIssues.setAdapter(mAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), 1, false);
            mRecyclerViewIssues.setLayoutManager(linearLayoutManager);
        }
    }

    // Retrieves issues JSON on separate thread
    private class GetDataTask extends AsyncTask<Void, Void, List<GitIssue>> {

        String run(String url) throws IOException {
            InputStream is = null;
            HttpURLConnection conn = null;

            try {
                URL endpointUrl = new URL(url);
                conn = (HttpURLConnection) endpointUrl.openConnection();
                conn.setRequestMethod(REQUEST_METHOD_GET);
                conn.setDoInput(true);

                conn.connect();
                int response = conn.getResponseCode();
                Log.d("DEBUG_TAG", "The response is: " + response);
                is = conn.getInputStream();
                mResult = InputStreamToString(is);
                is.close();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }

                if (is != null) {
                    is.close();
                }
            }
            return mResult;
        }

        private String InputStreamToString(InputStream is) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        }

        @Override
        protected List<GitIssue> doInBackground(Void... arg0) {

            try {
                JSONArray issueArray = new JSONArray(run(ENDPOINT));

                    for (int i = 0; i < issueArray.length(); i++) {
                        JSONObject issueObj = issueArray.getJSONObject(i);
                        GitIssue issue = new GitIssue();
                        issue.setIssueTitle(issueObj.get("title").toString());
                        issue.setIssueBody(issueObj.get("body").toString() + "\n\n");

                        JSONArray commentsArray = new JSONArray(run(issueObj.get("comments_url").toString()));
                        if (commentsArray.length() > 0) {
                            for (int j = 0; j < commentsArray.length(); j++) {
                                JSONObject commentObj = commentsArray.getJSONObject(j);
                                IssueComment comment = new IssueComment();
                                comment.setCommentBody("Posted by: " + commentObj.getJSONObject("user").getString("login").toUpperCase() + "\n\n" + commentObj.getString("body") + "\n\n\n");
                                issue.mCommentsList.add(comment);
                            }
                        }
                        mListIssues.add(issue);
                    }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("ServiceHandler", "Error retrieving data from URL");
            }
            return mListIssues;
        }


        @Override
        protected void onPostExecute(List<GitIssue> list) {
            super.onPostExecute(list);
            mAdapter.addIssues(list);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
