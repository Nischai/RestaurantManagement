package com.nischaipyda.restaurant.customer;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nischaipyda.restaurant.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.HashMap;

public class BillFragment extends Fragment implements BlockingStep{

    View view;
    DatabaseReference dref;
    TableDataModel tableDataModel;
    public BillFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        tableDataModel = (TableDataModel) context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        dref = FirebaseDatabase.getInstance().getReference().child("Table").child("" +(tableDataModel.getTableNumber()+1));
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean served = dataSnapshot.child("Served").getValue(Boolean.class);
                if (served)
                    loadListView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void loadListView() {

        dref = FirebaseDatabase.getInstance().getReference().child("Orders").child(Integer.toString(tableDataModel.getTableNumber()+1));
        TextView wait = (TextView) view.findViewById(R.id.tv_wait);
        wait.setVisibility(View.GONE);
        ListView listView  = (ListView) view.findViewById(R.id.lv_bill);
        ListView listView2  = (ListView) view.findViewById(R.id.lv_total);
        FirebaseListAdapter<FoodItem> firebaseListAdapter = new FirebaseListAdapter<FoodItem>(
                getActivity(),
                FoodItem.class,
                R.layout.layout_bill,
                dref
        ) {
            @Override
            protected void populateView(View v, FoodItem model, int position) {
                TextView name = (TextView)v.findViewById(R.id.text1);
                TextView price = (TextView)v.findViewById(R.id.text2);
                String strprice = "" + model.getPrice();
                name.setText(model.getName());
                price.setText(strprice);
            }
        };
        listView.setAdapter(firebaseListAdapter);
        dref = FirebaseDatabase.getInstance().getReference().child("Total");
        FirebaseListAdapter<Integer> firebaseListAdapter2 = new FirebaseListAdapter<Integer>(
                getActivity(),
                Integer.class,
                R.layout.layout_bill,
                dref
        ) {
            @Override
            protected void populateView(View v, Integer model, int position) {
                TextView name = (TextView)v.findViewById(R.id.text1);
                TextView price = (TextView)v.findViewById(R.id.text2);
                String na = (position == 0)?"GST":"Total",pr = Integer.toString(model);
                if (position == 0) {
                    name.setText(na);
                    price.setText(pr);
                }else{
                    name.setText(na);
                    price.setText(pr);
                }
            }
        };

        listView2.setAdapter(firebaseListAdapter2);
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        Toast.makeText(getContext(),"Please pay the bill",Toast.LENGTH_SHORT).show();
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
}
