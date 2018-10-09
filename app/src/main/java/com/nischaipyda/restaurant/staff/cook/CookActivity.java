package com.nischaipyda.restaurant.staff.cook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nischaipyda.restaurant.R;

import com.stepstone.stepper.StepperLayout;

public class CookActivity extends AppCompatActivity implements CookTableDataModel{

    int selectedTable;
    StepperLayout stepperLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);

        stepperLayout = (StepperLayout) findViewById(R.id.stepperLayout_cook);
        stepperLayout.setAdapter(new CookStepAdapter(getSupportFragmentManager(),this));

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
