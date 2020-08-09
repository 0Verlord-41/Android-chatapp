package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Mess extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> messages;
    private ArrayAdapter arrayAdapter;
    private String selecteduser;
    private EditText editText;
    private FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess);

        selecteduser=getIntent().getStringExtra("selecteduser");

        getSupportActionBar().setTitle(selecteduser);
        listView=findViewById(R.id.listview);
//SwipeRefreshLayout swipeRefreshLayout=findViewById(R.id.referesh);


editText=findViewById(R.id.editText);

button=findViewById(R.id.floatingActionButton);
messages=new ArrayList<String>();
arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,messages);
listView.setAdapter(arrayAdapter);

        ParseQuery<ParseObject> sendermessages=ParseQuery.getQuery("Chat");
        ParseQuery<ParseObject> receivermessages=ParseQuery.getQuery("Chat");

        sendermessages.whereEqualTo("sender",ParseUser.getCurrentUser().getUsername());
        sendermessages.whereEqualTo("receiver",selecteduser);

        receivermessages.whereEqualTo("sender",selecteduser);
        receivermessages.whereEqualTo("receiver",ParseUser.getCurrentUser().getUsername());

ArrayList<ParseQuery<ParseObject>> allquery=new ArrayList<ParseQuery<ParseObject>>();


allquery.add(sendermessages);
allquery.add(receivermessages);

ParseQuery<ParseObject> myquery=ParseQuery.or(allquery);   // Merged all the items in arraylists into one
myquery.orderByAscending("createdAt");

myquery.findInBackground(new FindCallback<ParseObject>() {
    @Override
    public void done(List<ParseObject> objects, ParseException e) {

        for(ParseObject newmessage:objects){
            String mess=newmessage.get("text")+"";

            if(newmessage.get("sender").equals(ParseUser.getCurrentUser().getUsername())){

                mess=ParseUser.getCurrentUser().getUsername()+" : "+mess;

            }else{
                mess=selecteduser+" : "+mess;

            }
            messages.add(mess);


            arrayAdapter.notifyDataSetChanged();
        }

    }
});

        button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Snackbar.make(v,"Message sent", Snackbar.LENGTH_LONG).setAction("Action",null).show();

        ParseObject message=new ParseObject("Chat");
        message.put("sender", ParseUser.getCurrentUser().getUsername());
        message.put("receiver",selecteduser);
        message.put("text",editText.getText().toString());

        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
messages.add(ParseUser.getCurrentUser().getUsername()+" : "+editText.getText().toString());
arrayAdapter.notifyDataSetChanged();
editText.setText("");
                }
            }
        });
    }
});
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                ParseQuery<ParseObject> sendermessages=ParseQuery.getQuery("Chat");
//                ParseQuery<ParseObject> receivermessages=ParseQuery.getQuery("Chat");
//
//                sendermessages.whereEqualTo("sender",ParseUser.getCurrentUser().getUsername());
//                sendermessages.whereEqualTo("receiver",selecteduser);
//
//                receivermessages.whereEqualTo("sender",selecteduser);
//                receivermessages.whereEqualTo("receiver",ParseUser.getCurrentUser().getUsername());
//
//                ArrayList<ParseQuery<ParseObject>> allquery=new ArrayList<ParseQuery<ParseObject>>();
//
//
//                allquery.add(sendermessages);
//                allquery.add(receivermessages);
//
//                ParseQuery<ParseObject> myquery=ParseQuery.or(allquery);   // Merged all the items in arraylists into one
//
//                myquery.orderByAscending("createdAt");
//
//                myquery.findInBackground(new FindCallback<ParseObject>() {
//                    @Override
//                    public void done(List<ParseObject> objects, ParseException e) {
//
//                        for(ParseObject newmessage:objects){
//                            String mess=newmessage.get("text")+"";
//
//
//
//
//                            if(newmessage.get("sender").equals(ParseUser.getCurrentUser().getUsername())){
//
//                                mess=ParseUser.getCurrentUser().getUsername()+" : "+mess;
//
//                            }else{
//                                mess=selecteduser+" : "+mess;
//
//                            }
//                            messages.add(mess);
//
//
//                            arrayAdapter.notifyDataSetChanged();
//                        }
//
//                    }
//                });
//            }
//        });
    }
}
