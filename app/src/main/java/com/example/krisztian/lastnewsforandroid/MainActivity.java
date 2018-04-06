package com.example.krisztian.lastnewsforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private ListView countryListView;

    private String[] countryNames = {"Austria", "Croatia", "Finland",
            "France", "Germany", "Hungary", "Romania", "Slovakia", "Sweden"};

    Integer[] imageId = {
            R.drawable.at,
            R.drawable.hr,
            R.drawable.fi,
            R.drawable.fr,
            R.drawable.de,
            R.drawable.hu,
            R.drawable.ro,
            R.drawable.sk,
            R.drawable.se

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryListView = findViewById(R.id.countryList);

        // image and text in table row: https://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html
        final CountryList adapter = new
                CountryList(MainActivity.this, countryNames, imageId);

        /*final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, countryNames);*/

        countryListView.setAdapter(adapter);

        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // String selectedCountry = adapter.getItem(position);
                String selectedCountry = countryNames[position];
                Intent intent = new Intent(view.getContext(), ShowNewsActivity.class);
                intent.putExtra("COUNTRY_NAME", selectedCountry);
                startActivity(intent);
            }
        });
    }

}