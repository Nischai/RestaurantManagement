package com.nischaipyda.restaurant.customer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nischaipyda.restaurant.R;
import com.stepstone.stepper.StepperLayout;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity implements TableDataModel{

    int selectedTable;
    StepperLayout stepperLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        stepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        stepperLayout.setAdapter(new StepAdapter(getSupportFragmentManager(),this));

    }

    @Override
    public void saveTableNumber(int selectedTable) {
        this.selectedTable = selectedTable;
    }

    @Override
    public int getTableNumber() {
        return selectedTable;
    }
}
