package com.imibragimov.loftmoney;

import android.os.Bundle;

//import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {
    ArrayList<Item> items = new ArrayList<Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        setInitialData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        ItemsAdapter adapter = new ItemsAdapter(this, items);
        recyclerView.setAdapter(adapter);
     //   recyclerView.setLayoutManager(new LinearLayoutManager(this));
     //   adapter.setItems(items);
    }
    private void setInitialData(){
        items.add(new Item("Зубная щетка", "90 р"));
        items.add(new Item("Жидкое мыыло", "120 р"));
        items.add(new Item("Зубная паста", "50 р"));
        items.add(new Item("Шампунь для волос", "150 р"));

    }
}

