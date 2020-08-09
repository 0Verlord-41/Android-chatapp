package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Chatactivity extends AppCompatActivity {


    private ListView listView;
SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatactivity);

getSupportActionBar().setTitle("Messages");

        listView=findViewById(R.id.listview);
        final ArrayList<String> names=new ArrayList<String>();
        final ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,names);

        swipeRefreshLayout=findViewById(R.id.swipe);
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(Chatactivity.this,Mess.class);
        intent.putExtra("selecteduser",names.get(position));
        startActivity(intent);
    }
});
        try {

            ParseQuery<ParseUser> query=ParseUser.getQuery();

            query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(objects.size()>0){

                        for(ParseUser user:objects){
                            names.add(user.getUsername());

                        }
                        listView.setAdapter(adapter);
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();

        }



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {

                    ParseQuery<ParseUser> query=ParseUser.getQuery();

                    query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
query.whereNotContainedIn("username",names);
                    query.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                            if(objects.size()>0){

                                for(ParseUser user:objects){
                                    names.add(user.getUsername());

                                }
adapter.notifyDataSetChanged();
                                if(swipeRefreshLayout.isRefreshing()){
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                            else{
                                if(swipeRefreshLayout.isRefreshing()){
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        return super.onOptionsItemSelected(item);

    }
}
