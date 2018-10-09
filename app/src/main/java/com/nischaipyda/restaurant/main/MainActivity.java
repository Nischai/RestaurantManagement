package com.nischaipyda.restaurant.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nischaipyda.restaurant.R;
import com.nischaipyda.restaurant.customer.CustomerActivity;
import com.nischaipyda.restaurant.staff.StaffActivity;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private MainContract.Presenter presenter;
    private Button ok ;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        ok = (Button) findViewById(R.id.btn_main_ok);
        radioGroup = (RadioGroup)findViewById(R.id.rgrp_main_options);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioGroup.getCheckedRadioButtonId() == R.id.rbtn_main_customer)
                    presenter.customerOptionChecked();
                else if(radioGroup.getCheckedRadioButtonId() == R.id.rbtn_main_Staff)
                    presenter.staffOptionChecked();
                else
                    presenter.noOptionsChecked();
            }
        });
    }

    @Override
    public void setPresenter(@NonNull MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void startCustomerActivity() {
        startActivity(new Intent(this,CustomerActivity.class));
    }

    @Override
    public void startStaffActivity() {
        startActivity(new Intent(this,StaffActivity.class));
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
