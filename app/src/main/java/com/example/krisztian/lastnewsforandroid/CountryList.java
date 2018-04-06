package com.example.krisztian.lastnewsforandroid;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryList extends ArrayAdapter {

    private final Activity context;
    private final String[] countries;
    private final Integer[] imageId;

    public CountryList(Activity context,
                      String[] countries, Integer[] imageId) {
        super(context, R.layout.country_list_item, countries);
        this.context = context;
        this.countries = countries;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.country_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.countryName);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(countries[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }

}
