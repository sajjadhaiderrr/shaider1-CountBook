/*
 * MainActivity
 *
 * Version 1.0
 *
 * September 27, 2017
 *
 * Copyright © 2017 Sajjad - All Rights Reserved.
 */

package com.example.shaider1_countbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    // Filename
    private static final String FILENAME = "file.sav";

    // ListView to be populated
    private ListView listView;

    // ArrayList for Counters
    private ArrayList<Counter> androidCounters = new ArrayList<Counter>();

    // Custom Counter Adapter
    private CounterAdapter counterAdapter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        // Find ListView layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview_counter);

        // Add Counter Button
        FloatingActionButton newActivity = (FloatingActionButton) findViewById(R.id.eventAdder);
        newActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_event, null);

                final EditText editName = (EditText) view.findViewById(R.id.edit_counter_name);
                final EditText editDesc = (EditText) view.findViewById(R.id.edit_counter_desc);
                final EditText editCount = (EditText) view.findViewById(R.id.edit_counter_count);


                // Create dialog to add counter
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Add Counter");
                builder.setView(view);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Check if counter name or count are valid entries
                        if ( !(editName.getText().toString().equals("")) && !(editCount.getText().toString().equals("")) ) {
                            String Counter = editName.getText().toString();
                            String Desc = editDesc.getText().toString();
                            String Count = editCount.getText().toString();
                            int intCount = Integer.parseInt(Count);


                            Counter myObject = new Counter(Counter, Desc, intCount);
                            androidCounters.add(myObject);
                            counterAdapter.notifyDataSetChanged();
                            final TextView totalCounters = (TextView) findViewById(R.id.totalCounters);
                            totalCounters.setText("You have " + counterAdapter.getCount() + " counter(s)");
                            saveInFile();
                            dialog.dismiss();
                        }

                        // Show error toast on invalid entry
                        else {
                            Toast.makeText(getApplicationContext(), "Make sure Name and Count are not blank", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.setCancelable(false);

                AlertDialog alert = builder.create();

                alert.show();
            }
        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        counterAdapter = new CounterAdapter(this, androidCounters);
        listView.setAdapter(counterAdapter);

        final TextView totalCounters = (TextView) findViewById(R.id.totalCounters);
        totalCounters.setText("You have " + counterAdapter.getCount() + " counter(s)");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current counter that was clicked on

                final Counter currentCounter = counterAdapter.getItem(position);

                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_edit, null);

                final EditText editName = (EditText) v.findViewById(R.id.edit_counter_name);
                final EditText editDesc = (EditText) v.findViewById(R.id.edit_counter_desc);
                final EditText editCount = (EditText) v.findViewById(R.id.edit_counter_count);
                final int pos = position;

                // Set Delete Button
                final Button deleteButton = (Button) v.findViewById(R.id.edit_delete);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        androidCounters.remove(pos);
                        saveInFile();
                        Intent eventIntent = new Intent(MainActivity.this, MainActivity.class);
                        counterAdapter.notifyDataSetChanged();
                        final TextView totalCounters = (TextView) findViewById(R.id.totalCounters);
                        totalCounters.setText("You have " + counterAdapter.getCount() + " counter(s)");
                        startActivity(eventIntent);

                    }
                });

                editName.setText(currentCounter.getCounterName().toString(), TextView.BufferType.EDITABLE);
                editDesc.setText(currentCounter.getCounterDescription().toString(), TextView.BufferType.EDITABLE);
                editCount.setText((String.valueOf(currentCounter.getCounterCount())), TextView.BufferType.EDITABLE);


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Edit Counter");
                builder.setView(v);


                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (Integer.parseInt(editCount.getText().toString()) < 0 ) {

                            Toast.makeText(getApplicationContext(), "Make sure count isn't negative", Toast.LENGTH_SHORT).show();

                        }

                        else if ((editName.getText().toString().equals("")) & (editCount.getText().toString().equals(""))){
                            Toast.makeText(getApplicationContext(), "Make sure Name and Count are not blank", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String Counter = editName.getText().toString();
                            String Desc = editDesc.getText().toString();
                            String Count = editCount.getText().toString();
                            int intCount = Integer.parseInt(Count);

                            currentCounter.setCounterName(Counter);
                            currentCounter.setCounterDesc(Desc);

                            if (intCount != currentCounter.getCounterCount()) {
                                currentCounter.setCounterDate();
                            }

                            currentCounter.setCounterCount(intCount);
                            saveInFile();
                            counterAdapter.notifyDataSetChanged();

                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.setCancelable(false);

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }


    // Save data when activity is paused
    @Override
    public void onPause() {
        super.onPause();
        saveInFile();
    }

    /**
     * Load data from file
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {}.getType();
            androidCounters = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            androidCounters = new ArrayList<Counter>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    /**
     * Save the data in file
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(androidCounters, writer);
            writer.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }
}