package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Loginactivity extends AppCompatActivity {


    private EditText editText;

    private EditText editText1;

    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        getSupportActionBar().setTitle("Login");

        editText=findViewById(R.id.editText2);

        editText1=findViewById(R.id.editText3);

        button=findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.animate().translationY(2000).setDuration(2000);
                logintheuser();
            }
        });


    }

    private void logintheuser() {

        try{

            ParseUser.logInInBackground(editText.getText().toString(), editText1.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user!=null){
                        alertbuilder("LoggedIn successfully",true);


                    }
                    else{

                        alertbuilder("Entered wrong details",false);

                    }
                }
            });


        }catch(Exception e){
            e.printStackTrace();
        }


    }
    private void alertbuilder(String account_created_successfully, final boolean value) {

        AlertDialog.Builder builder=new AlertDialog.Builder(Loginactivity.this);
builder.setTitle(account_created_successfully);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if(value) {

                    Intent intent = new Intent(Loginactivity.this, Chatactivity.class);
                    startActivity(intent);
                }
                else{
                    dialog.dismiss();
                    button.animate().translationY(0f).setDuration(2000);

                }
            }
        });

        AlertDialog ok=builder.create();
        ok.show();

    }
}
