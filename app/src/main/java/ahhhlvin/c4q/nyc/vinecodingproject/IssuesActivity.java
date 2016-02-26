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
import android.view.View;
import android.widget.Toast;

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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IssuesActivity extends AppCompatActivity {

    private static final String endpoint = "https://api.github.com/repos/rails/rails/issues";
    ArrayList<GitIssue> issuesList;
    RecyclerView issuesView;
    IssuesViewAdapter mAdapter;

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
            mAdapter = new IssuesViewAdapter(getApplicationContext(), issuesList);
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

        OkHttpClient client = new OkHttpClient();


        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }




        @Override
        protected ArrayList<GitIssue> doInBackground(Void... arg0) {

            try {
//                JSONArray issuesArray = new JSONArray(run(endpoint));


//            InputStream is = null;
//
//            try {
//                URL endpointUrl = new URL(endpoint);
//                HttpURLConnection conn = (HttpURLConnection) endpointUrl.openConnection();
//                conn.setReadTimeout(10000 /* milliseconds */);
//                conn.setConnectTimeout(15000 /* milliseconds */);
//                conn.setRequestMethod("GET");
//                conn.setDoInput(true);
//                // Starts the query
//                conn.connect();
//                int response = conn.getResponseCode();
//                Log.d("DEBUG_TAG", "The response is: " + response);
//                is = conn.getInputStream();
//
//
//
//                BufferedReader r = new BufferedReader(new InputStreamReader(is));
//                StringBuilder total = new StringBuilder();
//                String contentAsString;
//                try {
//                    while ((contentAsString = r.readLine()) != null) {
//                        total.append(contentAsString);
//                    }
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                //TODO: FIX!!
//                if (is != null) {
//                    is.close();
//                }

                // Convert the InputStream into a string

//                return contentAsString;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.

                JSONArray issueArray = new JSONArray(run(endpoint));

                for (int i = 0; i < issueArray.length(); i++) {
                    JSONObject issueObj = issueArray.getJSONObject(i);

                    GitIssue issue = new GitIssue();
                    issue.setIssueTitle(issueObj.get("title").toString());
                    issue.setIssueBody(issueObj.get("body").toString());

//                    JSONArray commentsArray = new JSONArray(run(issueObj.get("comments_url").toString()));

//                   for (int j = 0; j < commentsArray.length(); j++) {

//                       JSONObject commentObj = commentsArray.getJSONObject(j);

                    for (int k = 0; k < 10; k++) {
                       IssueComment comment = new IssueComment();
//                       comment.setBody(commentObj.getJSONObject("user").getString("login") + "\n" + commentObj.getString("body") + "\n\n");
                        comment.setBody("ahhhlvin" + "\n" + "a;sdlkfd;fjdsl;jfl;sdjfldjf;sdjfl;djf;jd;fds" + "\n\n");
                       issue.commentsList.add(comment);
                   }

                    issuesList.add(issue);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("ServiceHandler", "Error retrieving data from endpoint");
            }

            return issuesList;
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
