package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private TextView textView1;

    private TextView textView2;

private Button button;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Signup");

        
        textView=findViewById(R.id.email);

        textView1=findViewById(R.id.username);
        textView2=findViewById(R.id.password);
        button=findViewById(R.id.button);
        button1=findViewById(R.id.button2);

        ParseInstallation.getCurrentInstallation().saveInBackground();

 button.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         button.animate().rotation(2000).setDuration(2000);

         signupupuser();
         
     }
 });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
button1.animate().alpha(0.8f).setDuration(1000);
                Intent intent=new Intent(MainActivity.this,Loginactivity.class);
                startActivity(intent);

            }
        });
        
        
    }

    private void signupupuser() {

        try{
            ParseUser user=new ParseUser();
            user.setUsername(this.textView1.getText().toString());
            user.setPassword(this.textView2.getText().toString());
            user.setEmail(this.textView.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        ParseUser.logOut();
                        alertbuilder("Account created successfully",true);
                    }else{
                        ParseUser.logOut();

                        alertbuilder("Enter correct details",false);
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void alertbuilder(String account_created_successfully, final boolean value) {

        final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
builder.setTitle(account_created_successfully);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
dialog.cancel();
if(value) {
    Intent intent = new Intent(MainActivity.this, Loginactivity.class);
    startActivity(intent);
}
else {
   dialog.dismiss();
}

            }
        });

AlertDialog ok=builder.create();
ok.show();

    }
}
