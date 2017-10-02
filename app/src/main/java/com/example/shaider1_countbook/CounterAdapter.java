/*
 * CounterAdapter
 *
 * Version 1.0
 *
 * September 27, 2017
 *
 * Copyright © 2017 Sajjad - All Rights Reserved.
 */


package com.example.shaider1_countbook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Provides layout based on data source
 */
public class CounterAdapter extends ArrayAdapter<Counter> {

    private static final String LOG_TAG = CounterAdapter.class.getSimpleName();

    /**
     * A custom constructor
     * @param context        The current context. Used to inflate the layout file.
     * @param counters A List of counter objects to display in a list
     */
    public CounterAdapter(Activity context, ArrayList<Counter> counters) {
        super(context, 0, counters);
    }

    /**
     * Provides a view for an AdapterView
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        final Counter currentCounter = getItem(position);

        // decrement button listener
        Button decreaseButton = (Button) listItemView.findViewById(R.id.minusButton);
        decreaseButton.setTag(position);
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCounter.decCount();
                notifyDataSetChanged();

            }
        });

        // increment button listener
        Button increaseButton = (Button) listItemView.findViewById(R.id.plusButton);
        increaseButton.setTag(position);
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCounter.incCount();
                notifyDataSetChanged();

            }
        });

        // reset button listener
        Button resetButton = (Button) listItemView.findViewById(R.id.resetButton);
        resetButton.setTag(position);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCounter.resetCount();
                notifyDataSetChanged();

            }
        });


        TextView nameTextView = (TextView) listItemView.findViewById(R.id.counter_name);
        nameTextView.setText(currentCounter.getCounterName());

        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.counter_description);
        descriptionTextView.setText(currentCounter.getCounterDescription());

        TextView countTextView = (TextView) listItemView.findViewById(R.id.counter_count);
        countTextView.setText(String.valueOf(currentCounter.getCounterCount()));

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.counter_date);
        dateTextView.setText(currentCounter.getCounterDate());

        return listItemView;
    }

}