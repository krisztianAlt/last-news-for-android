package com.example.krisztian.lastnewsforandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ShowNewsActivity extends AppCompatActivity {

    private final String API_K = "700eedc3304b44fe8b6e079001e04091";
    private String countryName;

    private String TAG = MainActivity.class.getSimpleName();
    private ListView newsListView;

    ArrayList<HashMap<String, String>> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        Intent intent = getIntent();
        countryName = intent.getExtras().getString("COUNTRY_NAME");

        newsList = new ArrayList<>();
        newsListView = findViewById(R.id.newsList);

        new GetNews().execute();
    }

    private class GetNews extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(ShowNewsActivity.this,"Json Data is " +
                    "downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // request to url and getting response
            String url = "https://newsapi.org/v2/everything?q=" +
                    countryName +
                    "&sources=reuters&sortBy=relevancy&apiKey=" +
                    API_K;
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray news = jsonObj.getJSONArray("articles");

                    for (int i = 0; i < news.length(); i++) {
                        JSONObject n = news.getJSONObject(i);
                        String title = n.getString("title");
                        String author = n.getString("author");
                        String newsUrl = n.getString("url");
                        String date = n.getString("publishedAt").substring(0, 10);

                        HashMap<String, String> newsMap = new HashMap<>();

                        newsMap.put("title", title);
                        newsMap.put("author", author);
                        newsMap.put("newsUrl", newsUrl);
                        newsMap.put("date", date);

                        newsList.add(newsMap);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(final Void result) {
            super.onPostExecute(result);

            // sort news by date
            Collections.sort(newsList, new MapComparator("date"));

            final ListAdapter adapter = new SimpleAdapter(ShowNewsActivity.this, newsList,
                    R.layout.news_list_item, new String[]{ "title","author", "date", "newsUrl"},
                    new int[]{R.id.newsTitle, R.id.author, R.id.date, R.id.newsUrl});
            newsListView.setAdapter(adapter);

            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String, String> selectedCountry = (HashMap<String, String>) adapter.getItem(position);
                    String url = selectedCountry.get("newsUrl");
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(i);
                }
            });
        }

    }
}
