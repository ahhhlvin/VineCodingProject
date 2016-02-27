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

public class IssuesActivity extends AppCompatActivity {

    private static final String REQUEST_METHOD_GET = "GET";
    private static final String ENDPOINT = "https://api.github.com/repos/rails/rails/issues";
    ArrayList<GitIssue> issuesList;
    RecyclerView issuesView;
    IssuesViewAdapter mAdapter;

    String result = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        issuesList = new ArrayList<>();

        issuesView = (RecyclerView) findViewById(R.id.issues_view);


        new getIssues().execute();


        if (issuesList != null) {
            mAdapter = new IssuesViewAdapter(issuesList);
            issuesView.setAdapter(mAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), 1, false);
            issuesView.setLayoutManager(linearLayoutManager);
        }


////         FOR TESTING
//        for (int j = 0; j < 15; j++) {
//            issuesList.add(new GitIssue("Ahhhlvin", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas et felis porttitor magna vestibulum placerat. Fusce vel iaculis quam, non suscipit nulla. Nunc commodo diam nec felis dignissim, vitae accumsan dolor pulvinar. Cras orci augue, ornare vel mauris sed, tempus cursus elit. Nullam id nisl in arcu fringilla cursus. Nullam ornare ipsum id enim gravida tincidunt. Integer eu dolor nec massa ultricies consequat. In dignissim odio quis gravida dictum. Donec accumsan fermentum diam, et molestie ligula varius eget. Nam dictum elementum tellus, et iaculis nulla scelerisque quis. Vestibulum et venenatis libero, eu commodo ante. Aenean et varius est. Suspendisse tincidunt, sem eu posuere suscipit, elit velit porttitor diam, ac porttitor magna magna et erat. Donec eleifend mauris et elit posuere scelerisque. Vivamus in commodo arcu. Proin accumsan risus nunc, quis ornare urna accumsan eget. Curabitur venenatis est vel augue dignissim, nec pellentesque mi posuere. Vestibulum consectetur nisi vel placerat scelerisque. Cras blandit luctus nibh, eget tristique ligula fermentum nec."));
//        }

    }

    // Retrieves issues JSON on separate thread
    private class getIssues extends AsyncTask<Void, Void, ArrayList<GitIssue>> {

        @Override
        protected ArrayList<GitIssue> doInBackground(Void... arg0) {


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
                            comment.setBody(commentObj.getJSONObject("user").getString("login") + "\n\n" + commentObj.getString("body") + "\n\n\n");
                            issue.commentsList.add(comment);
                        }

                    }

                    issuesList.add(issue);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("ServiceHandler", "Error retrieving data from URL");
            }

            return issuesList;
        }


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
                result = InputStreamToString(is);
                is.close();

            } finally {
                if (conn != null) {
                    conn.disconnect();
                }

                if (is != null) {
                    is.close();
                }
            }

            return result;
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
        protected void onPostExecute(ArrayList<GitIssue> list) {
            super.onPostExecute(list);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
