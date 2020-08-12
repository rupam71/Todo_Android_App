package com.example.todo_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button save;
    EditText name;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatabaseHelper helper = new DatabaseHelper(this);
        final ArrayList array_list = helper.getAllContacts();
        name = findViewById(R.id.plain_text_input);
        listView = findViewById(R.id.list_view);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1, array_list);
        listView.setAdapter(arrayAdapter);

        findViewById(R.id.btnAddItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().isEmpty()) {
                    helper.insert(name.getText().toString());
                    Toast.makeText(MainActivity.this, "Inserted : "+name.getText().toString(), Toast.LENGTH_LONG).show();
                    name.setText("");
                } else {
                    name.setError("Enter NAME");
                }

                array_list.clear();
                array_list.addAll(helper.getAllContacts());
                arrayAdapter.notifyDataSetChanged();

                System.out.println(array_list);
                listView.invalidateViews();
                listView.refreshDrawableState();
            }
        });

        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        array_list.remove(pos);
                        // Refresh the adapter
                        arrayAdapter.notifyDataSetChanged();
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }
                });
    }}
