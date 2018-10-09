package com.nischaipyda.restaurant.customer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nischaipyda.restaurant.R;
import com.nischaipyda.restaurant.main.MainActivity;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;


public class FeedbackFragment extends Fragment implements BlockingStep, SeekBar.OnSeekBarChangeListener {

    SeekBar taste,service,overall;
    View view;
    TableDataModel tableDataModel;

    public FeedbackFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        tableDataModel = (TableDataModel) context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taste = (SeekBar) view.findViewById(R.id.sb_taste);
        service = (SeekBar) view.findViewById(R.id.sb_service);
        overall = (SeekBar) view.findViewById(R.id.sb_overall);
        this.view = view;
        taste.setOnSeekBarChangeListener(this);
        service.setOnSeekBarChangeListener(this);
        overall.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
        dref.child("Review").child("Taste").setValue(taste.getProgress());
        dref.child("Review").child("Service").setValue((service.getProgress()));
        dref.child("Review").child("Overall").setValue((overall.getProgress()));

        dref.child("Table").child("" +(tableDataModel.getTableNumber()+1)).child("Reserved").setValue(false);
        dref.child("Table").child("" +(tableDataModel.getTableNumber()+1)).child("Cleaned").setValue(false);
        dref.child("Table").child("" +(tableDataModel.getTableNumber()+1)).child("Served").setValue(false);
        dref.child("Table").child("" +(tableDataModel.getTableNumber()+1)).child("Ordered").setValue(false);
        dref.child("Table").child("" +(tableDataModel.getTableNumber()+1)).child("Cooked").setValue(false);

        callback.complete();
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        updateText(seekBar,progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void updateText(SeekBar seekBar,int progress){
        String star = (progress>1)?" stars":" star";
        if(seekBar.getId() == R.id.sb_overall){
            TextView overall = (TextView) view.findViewById(R.id.tv_overall);
            overall.setText("Overall   " + progress + star);
        }else if(seekBar.getId() == R.id.sb_taste){
            TextView overall = (TextView) view.findViewById(R.id.tv_taste);
            overall.setText("Taste     " + progress + star);
        }else if(seekBar.getId() == R.id.sb_service){
            TextView overall = (TextView) view.findViewById(R.id.tv_service);
            overall.setText("Service   " + progress + star);
        }
    }
}
