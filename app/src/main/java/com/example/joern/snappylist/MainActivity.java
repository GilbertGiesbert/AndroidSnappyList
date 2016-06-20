package com.example.joern.snappylist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<ListItem> items = new ItemFactory().buildItems(50);
        ListAdapter adapter = new ListAdapter(this, R.layout.list_item, items);


        ListView lv = (ListView) findViewById(R.id.lv_list);

        if(lv != null){
            lv.setFastScrollEnabled(true);
            lv.setAdapter(adapter);
        }


    }
}
