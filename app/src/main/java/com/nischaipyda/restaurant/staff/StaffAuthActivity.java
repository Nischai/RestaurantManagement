package com.nischaipyda.restaurant.staff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nischaipyda.restaurant.R;
import com.nischaipyda.restaurant.staff.cook.CookActivity;

public class StaffAuthActivity extends AppCompatActivity {

    public static final String USERNAME = "admin", PASSWORD = "pass" ;
    String op;
    String n,p;
    EditText uname,pass;
    Button login;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_auth);

        login = (Button)findViewById(R.id.btn_staff_login);
        uname = (EditText)findViewById(R.id.etxt_auth_uname);
        pass = (EditText)findViewById(R.id.etxt_auth_pass);

        b = getIntent().getExtras();
        op = b.getString("option");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = uname.getText().toString();
                p = pass.getText().toString();
                if (!verifyForm())
                    Toast.makeText(getApplicationContext(),"Enter Username and Password",Toast.LENGTH_SHORT).show();
                else if (n.equals(USERNAME) && p.equals(PASSWORD) && op.equals("cleaner"))
                    startActivity(new Intent(StaffAuthActivity.this,CleanerActivity.class));
                else if (n.equals(USERNAME) && p.equals(PASSWORD) && op.equals("cook"))
                    startActivity(new Intent(StaffAuthActivity.this,CookActivity.class));
                else if (n.equals(USERNAME) && p.equals(PASSWORD) && op.equals("server"))
                    startActivity(new Intent(StaffAuthActivity.this,ServerActivity.class));
            }
        });
    }

    public boolean verifyForm(){


        if(n.equals("") || p.equals(""))
            return false;

        return true;
    }
}
