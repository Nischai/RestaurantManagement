package com.nischaipyda.restaurant.staff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.nischaipyda.restaurant.R;

public class StaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        Button ok = (Button) findViewById(R.id.btn_staff_ok);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgrp_staff_options);
        final Intent intent = new Intent(this,StaffAuthActivity.class);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioGroup.getCheckedRadioButtonId() == R.id.rbtn_staff_cleaner){
                    intent.putExtra("option","cleaner");
                    startActivity(intent);
                }else if(radioGroup.getCheckedRadioButtonId() == R.id.rbtn_staff_server){
                    intent.putExtra("option","server");
                    startActivity(intent);
                }else if(radioGroup.getCheckedRadioButtonId() == R.id.rbtn_staff_cook){
                    intent.putExtra("option","cook");
                    startActivity(intent);
                }
            }
        });
    }
}
